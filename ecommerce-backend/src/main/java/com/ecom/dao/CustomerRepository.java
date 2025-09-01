package com.ecom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
