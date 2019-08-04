package Repos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.program.JPAUtil;
import entities.AuditLog;

import javax.persistence.EntityManager;
import java.sql.Timestamp;

public class AuditLogRepo {

    static EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    static ObjectMapper om = new ObjectMapper();

    public static void createLog(Object o, String action, String author) {
        AuditLog auditlog = new AuditLog();

        entityManager.getTransaction().begin();
        try {
            auditlog.setEntity(om.writeValueAsString(o));
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }
        auditlog.setAction(action);
        auditlog.setUsername(author);
        auditlog.setTime(new Timestamp(System.currentTimeMillis()));
        entityManager.persist(auditlog);
        entityManager.getTransaction().commit();

    }
}
