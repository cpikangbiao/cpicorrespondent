/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: LongTimeFeignConfiguration
 * Author:   admin
 * Date:     2018/6/12 16:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.correspondent.config;

import feign.Feign;
import feign.Request;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/6/12
 * @since 1.0.0
 */

@Configuration
public class LongTimeFeignConfiguration {

    public static final int CONNECT_TIMEOUT_MILLIS = 50000;
    public static final int READ_TIMEOUT_MILLIS = 50000;

//    @Autowired
//    private ObjectFactory<HttpMessageConverters> messageConverters;
//
//    @Bean
//    FormEncoder feignFormEncoder() {
//        return new FormEncoder(new SpringEncoder(this.messageConverters));
//    }
//
    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }

//    @Bean
//    public Logger.Level feignLogger() {
//        return Logger.Level.FULL;
//    }

    @Bean
    public Request.Options options() {
        return new Request.Options(CONNECT_TIMEOUT_MILLIS, READ_TIMEOUT_MILLIS);
    }
}
