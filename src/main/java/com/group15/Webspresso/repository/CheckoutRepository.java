package com.group15.Webspresso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group15.Webspresso.entity.Checkout;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

}
