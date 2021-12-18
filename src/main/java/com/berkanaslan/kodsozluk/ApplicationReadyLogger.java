package com.berkanaslan.kodsozluk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationReadyLogger implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyLogger.class);

    @Value("${version}")
    private String version;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        LOGGER.info("-------------------------");
        LOGGER.info("Application is running.");
        LOGGER.info("Version: {}", version);
        LOGGER.info("-------------------------");
    }
}
