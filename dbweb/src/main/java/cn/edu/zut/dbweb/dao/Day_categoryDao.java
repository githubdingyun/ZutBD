package cn.edu.zut.dbweb.dao;

import cn.edu.zut.dbweb.pojo.Day_category;

import java.util.List;


public interface Day_categoryDao {
    //查询  根据时间查
    public List<Day_category> queryByDate(String date);
}
