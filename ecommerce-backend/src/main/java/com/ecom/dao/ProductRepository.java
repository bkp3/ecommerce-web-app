package com.ecom.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{

}
