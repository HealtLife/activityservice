package com.acme.nutrimove.activities;

import com.acme.nutrimove.activities.infrastructure.external.UserServiceClient;
import feign.Feign;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.sql.*;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.acme.nutrimove.activities.infrastructure.external")
@EnableJpaAuditing
public class ActivityServiceApplication {
    public static void main(String[] args) {
        createDatabaseIfNotExists("jdbc:postgresql://localhost:5432/", "postgres", "renato", "nutrimove_activityservice");

        SpringApplication.run(ActivityServiceApplication.class, args);
    }
    private static void createDatabaseIfNotExists(String url, String user, String password, String dbName) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'");
            if (!rs.next()) {
                stmt.executeUpdate("CREATE DATABASE " + dbName);
                System.out.println("Base de datos '" + dbName + "' creada correctamente.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error verificando/creando base de datos: " + dbName, e);
        }
    }
}