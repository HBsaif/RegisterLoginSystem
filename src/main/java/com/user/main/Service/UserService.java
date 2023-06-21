package com.user.main.Service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.user.main.Dto.LoginDTO;
import com.user.main.Dto.UserDTO;
import com.user.main.Entity.User;
import com.user.main.payload.response.LoginMessage;
import com.user.main.payload.response.RegistrationMessage;

@Service
public interface UserService {

	RegistrationMessage addUser(UserDTO userDTO);

	LoginMessage loginUser(LoginDTO loginDTO);

	int getCount();

	List<User> getAllUsers();
	
	
	
	
	
//	public static List<UserDTO> userList = new ArrayList();
//	
//	static {
//		userList.add(new UserDTO(1, "a", "b"));
//	}
//
//	public UserDTO addUser(UserDTO userDTO) {
//		userList.add(userDTO);
//		return userDTO;
//	}
//	
//	public List<UserDTO> getUsers(){
//		return userList;
//	}

}
