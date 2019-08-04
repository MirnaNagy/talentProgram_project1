package entities;


import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.wildfly.swarm.container.runtime.cli.CommandLineArgs;
import sun.util.calendar.BaseCalendar;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

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
