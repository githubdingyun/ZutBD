package cn.edu.zut.dbweb.dao.impl;

import cn.edu.zut.dbweb.dao.Day_categoryDao;
import cn.edu.zut.dbweb.pojo.Day_category;
import cn.edu.zut.dbweb.util.HbaseUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class Day_categoryDaoImpl implements Day_categoryDao {
    @Override
    public List<Day_category> queryByDate(String date) {

        Table table=HbaseUtil.getTable("category_clickCount");
        Scan scan=new Scan();
        //rk 20181126_1  20181125_1
        Filter filter=new PrefixFilter(date.getBytes());
        scan.setFilter(filter);
        List<Day_category> list=new ArrayList<>();
        try{
            ResultScanner scanner = table.getScanner(scan);
            for(Result result:scanner) {
                String rk = Bytes.toString(result.getRow());
                long num = Bytes.toLong(result.getValue("info".getBytes(), "num".getBytes()));
                list.add(new Day_category(rk,num));
            }
            return list;
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
    public static void main(String[] args){
        List<Day_category> list=new Day_categoryDaoImpl().queryByDate("20181126");
        System.out.println(list);
    }
}
