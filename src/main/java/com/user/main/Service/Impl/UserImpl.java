package com.user.main.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.main.Dto.LoginDTO;
import com.user.main.Dto.UserDTO;
import com.user.main.Entity.User;
import com.user.main.Repo.UserRepo;
import com.user.main.payload.response.LoginMessage;
import com.user.main.payload.response.RegistrationMessage;
import com.user.main.Service.UserService;

@Service
public class UserImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public RegistrationMessage addUser(UserDTO userDTO) {
		
		try {
			
			if(userDTO.getName().isEmpty() || userDTO.getEmail().isEmpty() || userDTO.getPassword().isEmpty()) {
				return new RegistrationMessage("All fields required!", false);
			}
			else if(userDTO.getPassword().length() < 8) {
				return new RegistrationMessage("Password should be at least 8 characters!", false);
			}
			
			
			RegistrationMessage regMsg = userRepo.INS_DATA(
					userDTO.getName(),
					userDTO.getEmail(), 
					userDTO.getPassword()
//					this.passwordEncoder.encode(userDTO.getPassword())
				);
			
//			User user = new User(
//					
//					userDTO.getId(),
//					userDTO.getName(),
//					userDTO.getEmail(),
//					this.passwordEncoder.encode(userDTO.getPassword())
//			);
//			
//			userRepo.save(user);
			
			return new RegistrationMessage(regMsg.getMessage(), regMsg.getStatus());
			//return user;
		}
		catch(Exception e) {
			return new RegistrationMessage(e.getMessage(), false);
		}
		
	}

	@Override
	public LoginMessage loginUser(LoginDTO loginDTO) {
		String msg = "";
		User user1 = userRepo.findByEmail(loginDTO.getEmail());
		
		if(user1!=null) {
			String password = loginDTO.getPassword();
			String encodedPassword = user1.getPassword();
			Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
			if(isPwdRight) {
				Optional<User> user = userRepo.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
				if(user.isPresent()) {
					return new LoginMessage("Login Success", true);
				}
				else {
					return new LoginMessage("Login Failed", false);
				}
			}
			else {
				return new LoginMessage("Password did not match", false);
			}
		}
		else {
			return new LoginMessage("Email does not exist", false);
		}
	}

	@Override
	public int getCount() {
		return userRepo.GET_USER_COUNT();
	}

	@Override
	public List<User> getAllUsers() {
		List<User> userList = userRepo.ALL_USER_DATA();
		return userList;
	}

}
