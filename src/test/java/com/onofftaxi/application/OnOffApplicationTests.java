package com.onofftaxi.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OnOffApplicationTests {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void contextLoads() {
        System.out.println(passwordEncoder);
    }

}
