package com.lightdevel.smg.configs;

import com.lightdevel.smg.models.entities.Product;
import com.lightdevel.smg.repositories.ProductRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = { ProductRepository.class })
@EntityScan(basePackageClasses = { Product.class })
public class JpaConfig {
}
