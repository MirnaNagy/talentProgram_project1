package Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "users")
//@NamedQuery(name = "employee.findAll", query="SELECT emp FROM employee emp")

public class user implements Serializable {

    @Id
    @Column(name = "USER_ID")
    //@GeneratedValue (strategy = GenerationType.IDENTITY)
    private String userID;
    @Column (name = "USERNAME")
    private String username;
    @Column (name = "PASSWORD")
    private String password;
    @Column (name = "EMAIL")
    private String email;
    @Column (name = "ROLE")
    private String role;
    @Column (name = "DEL")
    private boolean delete;


    @ManyToMany
    @JoinTable(
            name = "groupUsers",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "GROUP_ID"))
    List<group> groups;


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public List<group> getGroups() {
        return groups;
    }

    public void setGroups(List<group> groups) {
        this.groups = groups;
    }
}
