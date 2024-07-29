package com.greentechpay.paymenthistoryservice.service;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("http://46.101.99.4:9000")
                .credentials("Kq1YoWfvEPLxOAoA72dA","Z4QbG9CxXDDIIJH1v6U1xXmk8dV5RB7n9TcGczBc")
                .build();
    }
}
