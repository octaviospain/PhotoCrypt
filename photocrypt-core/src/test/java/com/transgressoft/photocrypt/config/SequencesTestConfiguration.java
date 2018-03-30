package com.transgressoft.photocrypt.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

import java.util.concurrent.atomic.*;

/**
 * @author Octavio Calleya
 * @version 0.1
 */
@Configuration
public class SequencesTestConfiguration {

    @Bean
    @Qualifier ("albumSequence")
    AtomicInteger albumSequence() {
        return new AtomicInteger(0);
    }

    @Bean
    @Qualifier ("photoSequence")
    AtomicInteger photoSequence() {
        return new AtomicInteger(0);
    }

    @Bean
    @Qualifier ("personSequence")
    AtomicInteger personSequence() {
        return new AtomicInteger(0);
    }
}