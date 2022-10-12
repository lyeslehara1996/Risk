package Auth;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import Auth.Repository.RoleRepository;
import Auth.Repository.UserRepository;
import Auth.Service.AccountService;
import Auth.entities.Role;
import Auth.entities.User;
@SpringBootApplication

public class AuthentificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthentificationApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	

	@Bean
	CommandLineRunner start(AccountService accountService,  RepositoryRestConfiguration restConfiguration) {
		restConfiguration.exposeIdsFor(AuthentificationApplication.class);
		return args->{
			accountService.addNewRolle(new Role(null, "Admin",new ArrayList<>()));
			accountService.addNewRolle(new Role(null, "Utilisateur",new ArrayList<>()));
			accountService.addNewRolle(new Role(null, "Manager Risque ",new ArrayList<>()));
			accountService.addNewRolle(new Role(null, "Analyste Risque ",new ArrayList<>()));
			accountService.addNewRolle(new Role(null, "Contrôleur  ",new ArrayList<>()));
			accountService.addNewRolle(new Role(null, "Viewer ",new ArrayList<>()));
			
			
			accountService.addNewUser(new User(null,"Admin","admin@Risque.com","Admin1234",new ArrayList<>()));
			accountService.addNewUser(new User(null,"Utilisateur1","ManagerRisque@Risque.com","User4",new ArrayList<>()));
			accountService.addNewUser(new User(null,"Utilisateur2","Utilisateur@Risque.com","User1",new ArrayList<>()));
			accountService.addNewUser(new User(null,"Utilisateur3","Anyliste@Risque.com","User2",new ArrayList<>()));
			accountService.addNewUser(new User(null,"Utilisateur4","Controller@Risque.com","User3",new ArrayList<>()));
			accountService.addNewUser(new User(null,"Utilisateur5","Viwer@Risque.com","User5",new ArrayList<>()));
			accountService.addNewUser(new User(null,"Utilisateur6","Utilisateur@Risque.com","User6",new ArrayList<>()));
			accountService.addNewUser(new User(null,"Utilisateur7","Utilisateur@Risque.com","User7",new ArrayList<>()));
//			

			accountService.addRolleToUser("Admin", "Admin");
			accountService.addRolleToUser("Admin",  "Manager Risque");
			accountService.addRolleToUser("Utilisateur1", "Manager Risque");
			accountService.addRolleToUser("Utilisateur2", "Utilisateur");
			accountService.addRolleToUser("Utilisateur3", "Analyste Risque");
			accountService.addRolleToUser("Utilisateur4", "Contrôleur");
			accountService.addRolleToUser("Utilisateur5", "Viewer");
			accountService.addRolleToUser("Utilisateur6", "Utilisateur");
			accountService.addRolleToUser("Utilisateur7", "Utilisateur");
		};
	}

}
