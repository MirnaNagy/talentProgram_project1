package Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "_group")
public class group implements Serializable {

    @Id
    @Column(name = "GROUP_ID")
    //@GeneratedValue (strategy = GenerationType.IDENTITY)
    private String groupID;
    @Column (name = "GROUPNAME")
    private String groupname;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "group_users",
            joinColumns = @JoinColumn(name = "USERID"),
            inverseJoinColumns = @JoinColumn(name = "GROUP_ID"))
    List<user> users;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public List<user> getUsers() {
        return users;
    }

    public void setUsers(List<user> users) {
        this.users = users;
    }
}
