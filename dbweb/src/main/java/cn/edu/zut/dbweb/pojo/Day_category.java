package cn.edu.zut.dbweb.pojo;


import java.io.Serializable;

public class Day_category implements Serializable {
    private String rk;
    private Long num;

    public Day_category() {

    }

    public Day_category(String rk, Long num) {
        this.rk = rk;
        this.num = num;
    }

    public void setRk(String rk) {
        this.rk = rk;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getRk() {
        return rk;
    }

    public Long getNum() {
        return num;
    }

    @Override
    public String toString() {
        return "Day_category{" +
                "rk='" + rk + '\'' +
                ", num=" + num +
                '}';
    }
}
