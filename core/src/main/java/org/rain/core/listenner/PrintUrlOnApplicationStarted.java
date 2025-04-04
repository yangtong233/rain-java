package org.rain.core.listenner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * 在项目启动后打印接口文档地址
 */
@Component
@ConditionalOnClass(name = "com.github.xiaoymin.knife4j.spring.configuration.Knife4jAutoConfiguration")
@Slf4j
public class PrintUrlOnApplicationStarted implements ApplicationRunner, ApplicationContextAware {

    private ConfigurableApplicationContext application;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.application = (ConfigurableApplicationContext)applicationContext;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = getString(env.getProperty("server.servlet.context-path"));
        log.info("\n----------------------------------------------------------\n\t" +
                "项目启动成功! 访问路径:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "Swagger文档: http://" + ip + ":" + port + path + "/doc.html\n" +
                "----------------------------------------------------------");
    }

    private String getString(String s) {
        if (s == null) {
            return "";
        }
        return (s.trim());
    }

}
