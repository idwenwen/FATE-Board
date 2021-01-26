package com.webank.ai.fate.board.utils;

import bsp.encrypt.EncryptUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class PassWordEncryptPostProcessor implements EnvironmentPostProcessor {


    @SneakyThrows
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String isEncrypt = environment.getProperty("fateboard.datasource.isEncrypt");
        String privateKey = environment.getProperty("fateboard.datasource.privateKey");
        if ("true".equals(isEncrypt)&& StringUtils.isNotBlank(privateKey)) {
            String encryptPwd = environment.getProperty("fateboard.datasource.password");
            String realPwd = EncryptUtil.decrypt(privateKey, encryptPwd);

            Properties properties = new Properties();
            properties.put("fateboard.datasource.password", realPwd);
            environment.getPropertySources().addFirst(new PropertiesPropertySource("decryptProperties", properties));
        }


    }
}
