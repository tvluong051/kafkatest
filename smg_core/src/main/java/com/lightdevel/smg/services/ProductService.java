package com.lightdevel.smg.services;

import com.lightdevel.smg.models.entities.Product;
import com.lightdevel.smg.models.in.ProductIn;

public interface ProductService {
    Product addProduct(ProductIn productIn);

    Product getProduct(Long productId);
}
