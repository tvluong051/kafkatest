package com.lightdevel.smg.services;

import com.lightdevel.smg.models.entities.Product;
import com.lightdevel.smg.models.in.ProductIn;
import com.lightdevel.smg.publisher.ProductPublisher;
import com.lightdevel.smg.repositories.ProductRepository;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductPublisher productPublisher;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductPublisher productPublisher) {
        this.productRepository = Objects.requireNonNull(productRepository);
        this.productPublisher = Objects.requireNonNull(productPublisher);
    }
    @Override
    public Product addProduct(ProductIn productIn) {
        Product product = Product.builder()
                .name(productIn.getName())
                .price(productIn.getPrice())
                .build();
        Product savedProduct = productRepository.save(product);
        productPublisher.fanOutProduct(savedProduct);
        return savedProduct;
    }

    @Override
    public Product getProduct(Long productId) {
        return productRepository.getOne(productId);
    }
}
