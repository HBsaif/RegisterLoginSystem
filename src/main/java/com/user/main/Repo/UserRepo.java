package com.user.main.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.user.main.Entity.User;
import com.user.main.payload.response.RegistrationMessage;

@EnableJpaRepositories
@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
	
	Optional<User> findOneByEmailAndPassword(String email, String password);
	User findByEmail(String email);
	
	@Procedure(name = "INS_DATA")
	RegistrationMessage INS_DATA(String name, String email, String password);
	
	@Procedure(name = "GET_USER_COUNT")
	int GET_USER_COUNT();
	
	@Procedure(name = "ALL_USER_DATA")
	List<User> ALL_USER_DATA();
}
