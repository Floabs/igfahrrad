package at.igfahrrad;

public class UserCredentials {
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