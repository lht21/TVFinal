/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class User implements Serializable {

    private String password;
    private String message;

    public User() {
    }

    public User(String password, String message) {
        this.password = password;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public String getPassword() {
        return password;
    }

}
