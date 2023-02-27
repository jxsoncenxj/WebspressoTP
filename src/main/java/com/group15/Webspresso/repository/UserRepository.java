package com.group15.Webspresso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group15.Webspresso.entity.User;

public interface UserRepository extends JpaRepository <User, Integer> {

}
