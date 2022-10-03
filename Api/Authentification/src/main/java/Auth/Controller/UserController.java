package Auth.Controller;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import Auth.Filter.JwtUtils;
import Auth.Repository.UserRepository;
import Auth.Service.AccountService;
import Auth.entities.Role;
import Auth.entities.RoleToUserForm;
import Auth.entities.User;
import Authentification.Request.AuthRequest;
import Authentification.Request.JwtResponse;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserController {

	@Autowired
	private AccountService accountService ;
	
	@GetMapping("/user")
	public List<User> getUsers(){
		return accountService.ListUsers();
	}
	
	 @RequestMapping(value = "/users/save", method = RequestMethod.POST)
	public User saveUser(@RequestBody User user){
		return accountService.addNewUser(user);
	}
	
	@PostMapping("/roles/save")
	public ResponseEntity<Role> saveRoles(@RequestBody Role role){
		URI uri =URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/Roles/save").toUriString());
		return ResponseEntity.created(uri).body(accountService.addNewRolle(role));
	}
	
	@PostMapping("/roles/addRolesToUsers")
	public ResponseEntity<?> addRoleTOUser(@RequestBody RoleToUserForm form){
		accountService.addRolleToUser(form.getUsername(), form.getName());
		return ResponseEntity.ok().build();
	}
	

	
	
}