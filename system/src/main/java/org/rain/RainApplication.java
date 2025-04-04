package org.rain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * created by yangtong on 2025/4/4 下午3:26
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableTransactionManagement    //开启事务
@EnableScheduling   //开启定时任务
@EnableAsync    //开启异步任务
public class RainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RainApplication.class, args);
    }
}
