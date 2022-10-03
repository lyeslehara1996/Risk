package Auth.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.relational.core.mapping.Embedded.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "User")
public class User  implements Serializable{

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Long id, String username, String password, Collection<Role> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = (List<Role>) roles;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	
	@Column ( name =  "nom" )
	private String nom;
	
	@Column ( name =  "Username" )
	@Nullable
	private String username;
	
	@Column ( name =  "email" )
	@Nullable
	private String email;
	
	@Column(name = "password")
	@Nullable
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	

	@ManyToMany(fetch = FetchType.EAGER)
	
	private List<Role> roles = new ArrayList<>();
	

	
}