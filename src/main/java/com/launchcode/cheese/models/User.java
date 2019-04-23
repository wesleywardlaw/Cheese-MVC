package com.launchcode.cheese.models;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="users")
public class User {

    @NotNull(message="Username is required")
    @Size(min=5, max=15, message = "Username must be between 5 and 15 characters")
    private String username;

    @Email
    private String email;

    @NotNull
    @Size(min=6, message="The password must be at least 6 characters")
    private String password;

    @NotNull(message = "Passwords do not match")
    @Transient
    private String verifyPassword;



    @Id
    @GeneratedValue
    private long userID;


    public User(){
    }

    public User(String username, String email, String password, String verifyPassword) {
        this();
        this.username = username;
        this.email = email;
        this.password = password;
        this.verifyPassword = verifyPassword;
    }

    private void checkPassword(){
        if(password!=null&&verifyPassword!=null&&!password.equals(verifyPassword)){
            verifyPassword=null;
        }
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        checkPassword();
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
        checkPassword();
    }
}
