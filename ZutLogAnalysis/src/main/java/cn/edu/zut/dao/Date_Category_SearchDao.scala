package cn.edu.zut.dao

import cn.edu.zut.pojo.Date_Category_Search
import cn.edu.zut.util.HbaseUtil
import org.apache.hadoop.hbase.client.Table

object Date_Category_SearchDao {


  def saveOne(dcs: Date_Category_Search): Unit = {
    val table: Table = HbaseUtil.getTable("category_search_clickCount")
    table.incrementColumnValue(dcs.rk.getBytes(), "info".getBytes(),
      "num".getBytes(), dcs.num)
  }

  //可以写一个测试类


}
