package com.redis.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 用途描述
 *
 * @author 胡晓磊
 * @company xxx
 * @date 2020年11月18日 20:52 胡晓磊 Exp $
 */
@Configuration      //指明当前类是配置类
@ComponentScan(basePackages={"com.redis.demo"})
public class SpringConfig {

}
