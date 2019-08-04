package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name="user.findAll_admin", query="SELECT usr FROM User usr"),
        @NamedQuery(name="user.findAll", query="SELECT usr.userID, usr.username, usr.email FROM User usr WHERE usr.delete = false")
})

public class User implements Serializable {

    @Id
    @Column(name = "USERID")
    //@GeneratedValue (strategy = GenerationType.IDENTITY)
    private int userID;
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
            name = "group_users",
            joinColumns = @JoinColumn(name = "USERID"),
            inverseJoinColumns = @JoinColumn(name = "GROUP_ID"))
    List<Group> groups;


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
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

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
