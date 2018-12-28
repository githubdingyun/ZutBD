package cn.edu.zut.dbweb.util;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseUtil {
    private static Configuration conf;
    private static Connection conn;
    private static Table table;

    static {
        //1.Á¬½Ózookeeper
        conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "master1:2181,node1:2181,node2:2181");
        //2.½¨Á¢Á¬½Ó
        try {
            conn = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 使用方法:
     * 直接gettable  就可以对table进行操作
     *
     * @param tableName
     * @return
     */

    public static Table getTable(String tableName) {


        //3.»ñÈ¡±í
        try {
            table = conn.getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return table;

    }


    public static void main(String[] args) throws IOException {
        //1.zk的 配置    来获取到hbase的元数据信息
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "master1:2181,node1:2181,node2:2181");
        //2.创建一个默认连接
        Connection conn = ConnectionFactory.createConnection(conf);

        //3.要获取的表明
        Table table = conn.getTable(TableName.valueOf("sum_money_shenfeng_date"));

        //4.
        /*sum_money_20181112   ifno:money=300
         *
         * 4.incrementColumnValue  hbase»á×Ô¼º×öÀÛ¼Ó²Ù×÷
         *
         */
        //rk   ÁÐ´Ø     ÁÐ     Öµ
        //table.incrementColumnValue("sum_money_20181112".getBytes(),
        //	"info".getBytes(), "money".getBytes(), 200);

        //scan操作
        Scan scan = new Scan();
        ResultScanner resultScanner = table.getScanner(scan);
        for (Result result : resultScanner) {
            System.out.println(new String(result.getRow()));
            byte[] b = result.getValue("info".getBytes(), "money".getBytes());
            System.out.println(Bytes.toLong(b));
        }

    }
}
