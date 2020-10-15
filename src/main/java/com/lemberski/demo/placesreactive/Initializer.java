package com.lemberski.demo.placesreactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Component
class Initializer implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(Initializer.class);

    private final DatabaseClient databaseClient;
    private final String sql;

    Initializer(DatabaseClient databaseClient, @Value("${database.initialization.sql}") String sql) {
        this.databaseClient = databaseClient;
        this.sql = sql;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        databaseClient
                .execute(sql)
                .fetch()
                .rowsUpdated()
                .subscribe(count -> LOG.info("Database with statement '{}' initialized.", sql));
    }

}
