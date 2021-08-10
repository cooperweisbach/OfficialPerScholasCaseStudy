package com.cooperweisbach.CommunityGarden;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
@Transactional
public class AppRunner implements CommandLineRunner {
    // autowire all repo's/services

    @Override
    public void run(String... args) throws Exception {

        // add dummy data

    }


}
