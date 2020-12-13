package com.springboot.cs;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//http://localhost:8888/doc.html
@Slf4j
@RestController
@EnableSwagger2
@EnableAutoConfiguration(exclude=DruidDataSourceAutoConfigure.class)
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan("com.springboot.cs.mapper")
public class CsApplication {

	//https://www.cnblogs.com/zhangjianbing/p/8992897.html

	public static void main(String[] args) {
		SpringApplication.run(CsApplication.class, args);
	}

}
