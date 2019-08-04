package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "_group")
@NamedQuery(name = "group.findAll", query="SELECT grp FROM Group grp")

public class Group implements Serializable {

    @Id
    @Column(name = "GROUP_ID")
    //@GeneratedValue (strategy = GenerationType.IDENTITY)
    private int groupID;
    @Column (name = "GROUPNAME")
    private String groupname;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "group_users",
            joinColumns = @JoinColumn(name = "USERID"),
            inverseJoinColumns = @JoinColumn(name = "GROUP_ID"))
    List<User> users;

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
