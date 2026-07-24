package com.harshit.pharmacy.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

@Configuration
public class RedisConfig {


    @Bean
    public StringRedisTemplate stringRedisTemplate(
            RedisConnectionFactory connectionFactory) {

        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    public DefaultRedisScript<Long> reserveStockScript() {

        DefaultRedisScript<Long> script = new DefaultRedisScript<>();

        script.setLocation(new ClassPathResource("lua/reserve_stock.lua"));

        script.setResultType(Long.class);

        return script;
    }

    @Bean
    public DefaultRedisScript<Long> releaseStockScript() {

        DefaultRedisScript<Long> script = new DefaultRedisScript<>();

        script.setLocation(new ClassPathResource("lua/release_stock.lua"));

        script.setResultType(Long.class);

        return script;
    }

    @Bean
    public DefaultRedisScript<String> confirmReservationScript() {

        DefaultRedisScript<String> script = new DefaultRedisScript<>();

        script.setLocation(new ClassPathResource("lua/confirm_reservation.lua"));

        script.setResultType(String.class);

        return script;
    }


}
