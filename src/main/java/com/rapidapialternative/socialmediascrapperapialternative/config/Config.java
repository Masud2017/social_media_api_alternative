package com.rapidapialternative.socialmediascrapperapialternative.config;

import com.rapidapialternative.socialmediascrapperapialternative.utils.ProxyListRotatorSingleton;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.FileNotFoundException;

@Configuration
@EnableScheduling
public class Config {
    @Bean
    public ProxyListRotatorSingleton getProxyListRotatorSingle() throws FileNotFoundException {
        return ProxyListRotatorSingleton.getInstance();
    }
}
