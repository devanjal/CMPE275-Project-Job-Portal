package edu.sjsu.cmpe275.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    private Long id;
    private String username;
    private String password;
    private String passwordConfirm;
    private Set<Role> roles;
    private Profile profile;
    private String tokenId;
    private String emailid;
    private String isVerified;
    private String usertype;
    
	
    public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}


	public String getIsVerified() {
		return isVerified;
	}
	

	public void setIsVerified(String isVerified) {
		this.isVerified = isVerified;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinTable(name = "user_profile", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "profile_id"))
    public Profile getProfile(){
    	return this.profile;
    }
    
    public void setProfile(Profile profile){
    	this.profile = profile;
    }
}
