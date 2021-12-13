package cn.kungreat.flybbs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "cn.kungreat.flybbs.mapper")
public class FlybbsApplication {
	/*
	spring 默认使用的日志工具类
	*/
	private final static Log LOGGER = LogFactory.getLog(FlybbsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FlybbsApplication.class, args);
		LOGGER.info("start finish");
	}

}
