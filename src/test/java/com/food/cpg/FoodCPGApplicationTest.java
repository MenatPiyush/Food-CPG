package com.food.cpg;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FoodCPGApplicationTest {

    private static final Logger LOG = LoggerFactory.getLogger(FoodCPGApplicationTest.class);

    @Test
    public void contextLoads() {
        LOG.info("Application context loaded successfully.");
    }
}