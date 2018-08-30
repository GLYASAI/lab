package org.abewang.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author Abe
 * @Date 2018/8/29.
 */
@SpringBootApplication
public class JpaApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class);
    }
}
