package com.lightdevel.smg.controllers;

import com.lightdevel.smg.models.entities.Product;
import com.lightdevel.smg.models.in.ProductIn;
import com.lightdevel.smg.services.ProductService;
import io.swagger.annotations.Api;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@Api("Product operations")
@RestController
@RequestMapping("/api/v0/products")
public class ProductController {

  private ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = Objects.requireNonNull(productService);
  }

  @GetMapping("/product/{productId}")
  public Product getProductById(@PathVariable Long productId) {
    log.info("Get - Get product id: {} ", productId);
    return productService.getProduct(productId);
  }

  @PostMapping("/product")
  public Product createProduct(@RequestBody @Valid ProductIn productIn) {
    log.info("POST - Create product request: {} ", productIn);
    return productService.addProduct(productIn);
  }
}
