package cn.edu.zut.dbweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ComponentScan("cn.edu.zut.dbweb")
public class DbwebApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbwebApplication.class, args);
    }

}

