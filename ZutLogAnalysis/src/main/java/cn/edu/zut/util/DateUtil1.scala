package cn.edu.zut.util

import java.util.Date

import org.apache.commons.lang3.time.FastDateFormat

object DateUtil1 {
  val format1: FastDateFormat = FastDateFormat.getInstance("yyyy-MM-dd hh:mm:ss")

  val format2: FastDateFormat = FastDateFormat.getInstance("yyyyMMdd")


  def main(args: Array[String]): Unit = {

    //    把字符串转换成时间
    val date: Date = format1.parse("2018-11-24 10:38:01")
    val str: String = format2.format(date)
    println(str)


  }

  def getdate(date: String): String = {
    val date1: Date = format1.parse(date)
    val str: String = format2.format(date1)
    str
  }


}
