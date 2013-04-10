package ru.db.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
    
    private Integer id;
    private String login;
    private String password;
    
    public User() {
        super();
    }

    public User(Integer id, String login, String password) {
        super();
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
