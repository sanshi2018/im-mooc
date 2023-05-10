package com.lld.im.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.lld.im.service",
        "com.lld.im.common"})
@MapperScan("com.lld.im.service.*.dao.mapper")
//导入用户资料，删除用户资料，修改用户资料，查询用户资料
public class Application {


    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(Application.class, args);
        Environment env = application.getEnvironment();

        String port = env.getProperty("server.port");

        String ip = InetAddress.getLocalHost().getHostAddress();
        log.info("\n----------------------------------------------------------\n\t" +

                "Application Demo is running! Access URLs:\n\t" +

                "本地访问地址: \thttp://localhost:" + port +"/\n\t" +

                "外部访问地址: \thttp://" + ip + ":" + port +"/\n\t" +

                "Swagger文档: \thttp://" + ip + ":" + port +"/swagger-ui/\n" +

                "----------------------------------------------------------");

    }


}




