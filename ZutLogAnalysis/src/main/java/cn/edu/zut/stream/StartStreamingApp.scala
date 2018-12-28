package cn.edu.zut.stream

import cn.edu.zut.dao.{Date_CategoryDao, Date_Category_SearchDao}
import cn.edu.zut.pojo.{Date_Category, Date_Category_Search}
import cn.edu.zut.util.DateUtil1
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable.ListBuffer

object StartStreamingApp {

  case class ClickLog(ip: String, date: String, category: Int, source: String, status: String)

  def main(args: Array[String]): Unit = {

    val iqyConf: SparkConf = new SparkConf().setAppName("iqy").setMaster("local[2]")

    val streamingContext = new StreamingContext(iqyConf, Seconds(60))

    val kafkaParam = Map("metadata.broker.list" -> "master:9092,node1:9092,node2:9092")
    val topics = Set("iqy")


    val ds: InputDStream[(String, String)] = KafkaUtils.createDirectStream
      [String, String, StringDecoder, StringDecoder](streamingContext, kafkaParam, topics)



    //第yi个实际相当于 hadoop中的    longwriteable
    //    ds.map(_._1).print()

    val cleanResult: DStream[ClickLog] = ds.map(line => {
      val strings: Array[String] = line._2.split("\t")
      val ip: String = strings(0)
      val date: String = DateUtil1.getdate(strings(1))
      val url: String = strings(2)
      var category = 0
      if (url != null) {
        val value: Array[String] = url.split(" ")
        if (value(1).startsWith("www")) {
          category = value(1).split("/")(1).toInt
        }

      }
      val source = strings(3)
      val status = strings(4)
      //把这些数据封装到样例类
      val log: ClickLog = ClickLog(ip, date, category, source, status)

      log

    })
    cleanResult.print()


    //功能1:获取单个时间中的某个频道访问量
    val date_category: DStream[(String, Int)] = cleanResult.map(clicklog => {
      (clicklog.date + "_" + clicklog.category, 1)
    }).reduceByKey(_ + _)
    //保存到数据库
    date_category.foreachRDD(rdds => {
      rdds.foreachPartition(partitionRdds => {
        val listBuffer = new ListBuffer[Date_Category]
        partitionRdds.foreach(rdd => {
          //每一个rdd
          listBuffer.append(new Date_Category(rdd._1, rdd._2))
        })
        Date_CategoryDao.save(listBuffer.toList)

      })
    })

    //功能二:  莫一个频道莫一天的访问量
    val cleanResult2: DStream[(String, Int)] = cleanResult.map(clicklog => {
      val str1: String = clicklog.date + '_' + clicklog.category + '_'

      if (clicklog.source != "-") {
        val strings: Array[String] = clicklog.source.
          replace("//", "/").split("/")
        var str = str1 + strings(2)
        (str, 1)
      }
      else ("没有数据", 1)
    })
    cleanResult2.print()
    cleanResult2.foreachRDD(rdds => {
      rdds.foreachPartition(partitionRdds => {
        partitionRdds.foreach(rdd => {
          Date_Category_SearchDao.
            saveOne(new Date_Category_Search(rdd._1, rdd._2))
        })
      })
    })


    streamingContext.start()
    streamingContext.awaitTermination()
    streamingContext.stop()



  }

}
