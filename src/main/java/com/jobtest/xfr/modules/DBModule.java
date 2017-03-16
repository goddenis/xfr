package com.jobtest.xfr.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.jobtest.xfr.domain.EntryRepository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class DBModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(EntryRepository.class);
    }

    @Provides
    @Singleton
    public EntityManagerFactory provideEntityManagerFactory() {
        Map<String, String> properties = new HashMap<String, String>();
//        properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
//        properties.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/xfr");
//        properties.put("hibernate.connection.username", "xfr");
//        properties.put("hibernate.connection.password", "xfr");
//        properties.put("hibernate.connection.pool_size", "50");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
//        properties.put("hibernate.hbm2ddl.auto", "update");
        //-------------------  c3p0 pool  ---------------------
//                properties.put("hibernate.c3p0.min_size","20");
//        properties.put("hibernate.c3p0.max_size","50");
//        properties.put("hibernate.c3p0.timeout","300");
//        properties.put("hibernate.c3p0.max_statements","50");
//        properties.put("hibernate.c3p0.idle_test_period","3000");

//        -------------------- hikari pool ----------------------
        properties.put("hibernate.connection.provider_class","com.zaxxer.hikari.hibernate.HikariConnectionProvider");
        properties.put("hibernate.hikari.minimumIdle","5");
        properties.put("hibernate.hikari.maximumPoolSize","10");
        properties.put("hibernate.hikari.idleTimeout","30000");
        properties.put("hibernate.hikari.dataSourceClassName","org.postgresql.ds.PGPoolingDataSource");
        properties.put("hibernate.hikari.dataSource.url","jdbc:postgresql://localhost:5432/xfr");
        properties.put("hibernate.hikari.dataSource.user","xfr");
        properties.put("hibernate.hikari.dataSource.password","xfr");

        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("db-manager", properties);
            return entityManagerFactory;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

}
