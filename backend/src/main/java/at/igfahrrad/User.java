package at.igfahrrad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    public String getPassword(){
        return this.password;
    }    
    public String setPassword(String password){
        return this.password = password;
    }
    public String getUsername(){
        return this.username;
    }    
    public String setUsername(String username){
        return this.username = username;
    }
}

