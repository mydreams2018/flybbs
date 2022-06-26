package cn.kungreat.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "cn.kungreat.base.mapper")
public class FlybbsApplication {
	/*
	spring 默认使用的日志工具类
	*/
	private final static Logger LOGGER = LoggerFactory.getLogger(FlybbsApplication.class);
	public static final ObjectMapper MAP_JSON = new ObjectMapper(); //create once, reuse
	public static void main(String[] args) {
		SpringApplication.run(FlybbsApplication.class, args);
		LOGGER.info("start finish");
	}

}