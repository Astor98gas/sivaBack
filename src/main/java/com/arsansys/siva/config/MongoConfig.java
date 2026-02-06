package com.arsansys.siva.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.arsansys.siva.repository.mongo")
public class MongoConfig {
}