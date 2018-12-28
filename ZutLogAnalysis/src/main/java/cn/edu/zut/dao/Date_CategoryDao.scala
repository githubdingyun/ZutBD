package cn.edu.zut.dao

import cn.edu.zut.pojo.Date_Category
import cn.edu.zut.util.HbaseUtil
import org.apache.hadoop.hbase.client.Table

object Date_CategoryDao {

  def save(list: List[Date_Category]): Unit = {
    //获取table,把list数据一条一条传入进去
    val table: Table = HbaseUtil.getTable("category_clickCount")
    for (dc <- list) {
      //传入数据   分别是   rk   列簇名   内容名   值
      table.incrementColumnValue(dc.rk.getBytes(), "info".getBytes(), "num".getBytes(), dc.num)

    }


  }

}
