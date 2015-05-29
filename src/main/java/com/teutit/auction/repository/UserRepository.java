package com.teutit.auction.repository;

import com.teutit.auction.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>{
}
