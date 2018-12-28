package cn.edu.zut.dbweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

/**
 * @program: sparks
 * @description: 这是baseCon
 * @author: dingyun
 * @create: 2018-11-26 14:48
 */
@Controller
public class BaseController {
    @ResponseBody
    @RequestMapping("hello.action")
    public String sayHello(){

        return "hello.html";
    }


    @RequestMapping("bye.action")
    public String saybye(){

        return "a";
    }

    @ResponseBody
    @RequestMapping("ld")
    public ArrayList<String> ld(){

        ArrayList<String> strings = new ArrayList<>();

        strings.add("1212");
        strings.add("1212");
        strings.add("1212");
        strings.add("1212");
        strings.add("1212");
        strings.add("1212");

        System.out.println("66326");

        return strings;
    }

    public void add(Byte b)
    {
        b = b++;
    }
    public void test()
    {
        Byte a = 127;
        Byte b = 127;
        add(++a);
        System.out.print(a + " ");
        add(b);
        System.out.print(b + "");
    }

    public static void main(String[] args) {
        new BaseController().test();
    }

}