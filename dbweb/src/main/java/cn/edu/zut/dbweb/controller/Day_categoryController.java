package cn.edu.zut.dbweb.controller;

import cn.edu.zut.dbweb.dao.Day_categoryDao;
import cn.edu.zut.dbweb.pojo.Day_category;
import cn.edu.zut.dbweb.dao.Day_categoryDao;
import cn.edu.zut.dbweb.pojo.Day_category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class Day_categoryController {
    //注入dao
    @Autowired
    private Day_categoryDao day_categoryDao;

    //toUI
    @RequestMapping("/show.action")
    public ModelAndView toUI(){
        return new ModelAndView("/show.html");
    }

    //把数据返回
    @ResponseBody
    @RequestMapping("/getCategory_click.action")
    public List<Day_category> getCategory_click(String date){
        //list   rk .split("_")(1)   1->电影   2->电视剧
        return day_categoryDao.queryByDate(date);
    }
}
