package com.imax.stock;

import com.imax.stock.bl.domain.model.StockModel;
import com.imax.stock.bl.service.StockRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ConfigurationPropertiesScan("com.imax.*")
@EnableTransactionManagement
@EntityScan(basePackages = {"com.imax.*"})
@EnableJpaRepositories(basePackages = {"com.imax.*"})
@EnableAsync
public class Api {

    public static void main(String[] args) {
        SpringApplication.run(Api.class, args);
    }

    @Bean
    public CommandLineRunner insertDummyData(StockRepository stockRepository) {
        return args -> {

            // create dummy stocks
            stockRepository.saveAll(Arrays.asList(
                    new StockModel("Coca-Cola", BigDecimal.valueOf(120000)),
                    new StockModel("Altria", BigDecimal.valueOf(320000)),
                    new StockModel("Amazon", BigDecimal.valueOf(480000)),
                    new StockModel("Celgene", BigDecimal.valueOf(590000)),
                    new StockModel("Apple", BigDecimal.valueOf(730000)),
                    new StockModel("Alphabet", BigDecimal.valueOf(840000)),
                    new StockModel("Gilead", BigDecimal.valueOf(6550000)),
                    new StockModel("Microsoft", BigDecimal.valueOf(12870000)),
                    new StockModel("Google", BigDecimal.valueOf(32560000))
            ));

        };
    }
}