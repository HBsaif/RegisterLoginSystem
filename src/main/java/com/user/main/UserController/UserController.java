package com.user.main.UserController;

import java.util.List;
import java.util.Map;

import javax.tools.SimpleJavaFileObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.main.Dto.LoginDTO;
import com.user.main.Dto.UserDTO;
import com.user.main.Entity.User;
import com.user.main.Repo.UserRepo;
import com.user.main.Service.UserService;
import com.user.main.payload.response.LoginMessage;
import com.user.main.payload.response.RegistrationMessage;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("/count")
	public int getUserCount() {
		int cnt = userService.getCount();
		System.out.println("Total users " + cnt);
		return cnt;
	}
	
//	@GetMapping("/users")
//	public List<User> getAllUsers() {
//		List<User> userList = userService.getAllUsers(); 
//		return userList;
//	}
	
	@GetMapping("/users")
	public List<User> getAllUsers() {
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("ALL_USERS_DATA")
				.returningResultSet("p_info",
						(RowMapper<User>) (rs, rowNum) -> {
							User user = new User();
							user.setId(rs.getInt("ID"));
							user.setName(rs.getString("NAME"));
							user.setEmail(rs.getString("EMAIL"));
							return user;
						});

		
		Map<String, Object> result = jdbcCall.execute();
		return (List<User>) result.get("p_info");
	}
	

	@PostMapping("/register")
	public RegistrationMessage saveUser(@RequestBody UserDTO userDTO) {
		
		RegistrationMessage regMsg = new RegistrationMessage();
		
		regMsg.setMessage(userService.addUser(userDTO).getMessage());
		regMsg.setStatus(userService.addUser(userDTO).getStatus());
		
		return regMsg;
		
//		String id = userService.addUser(userDTO);
//		return id;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
		LoginMessage loginMsg = userService.loginUser(loginDTO);
		
		return ResponseEntity.ok(loginMsg);
	}
	
	
	
}
