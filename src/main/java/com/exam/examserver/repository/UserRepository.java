package com.exam.examserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.examserver.models.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);

}
