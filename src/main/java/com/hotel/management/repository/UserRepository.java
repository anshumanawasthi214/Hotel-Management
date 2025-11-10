package com.hotel.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.management.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
