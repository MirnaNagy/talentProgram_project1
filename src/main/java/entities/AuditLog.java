package entities;



import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "auditlog")
public class AuditLog implements Serializable {


    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logID;
    @Column(name = "ENTITY")
    private String entity;
    @Column(name = "ACTION")
    private String action;
    @Column(name = "USERNAME")
    private String username;
//    @Column(name = "DATE")
//    private Date date;
    @Column(name = "TIME")
    private Timestamp time;


    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Timestamp getDate() {
        return time;
    }

    public void setDate(Timestamp time) {
        this.time = time;
    }
}
