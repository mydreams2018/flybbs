package cn.kungreat.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class Admin {
    /*
    * 监控中心
    */
    public static void main(String[] args) {
        SpringApplication.run(Admin.class, args);
    }
}