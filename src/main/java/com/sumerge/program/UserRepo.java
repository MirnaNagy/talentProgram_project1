package com.sumerge.program;


import entities.Group;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserRepo {

    EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();



    @Transactional(rollbackOn = Exception.class)
    public static String sha256(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("SHA-256");
        byte[] digest = md5.digest(input.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; ++i) {
            sb.append(Integer.toHexString((digest[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }


    @Transactional(rollbackOn = Exception.class)
    public List<User> getAllUsers(boolean adm, String author) {
        entityManager.getTransaction().begin();
        List<User> results;
        if(adm) {
            TypedQuery<User> query = entityManager.createNamedQuery("user.findAll_admin", User.class);
            results = query.getResultList();
        }
        else {
            TypedQuery<User> query = entityManager.createNamedQuery("user.findAll", User.class);
            results = query.getResultList();
        }

        entityManager.getTransaction().commit();
        AuditLogRepo.createLog(results, "Get all users", author);

        return results;
    }


    @Transactional(rollbackOn = Exception.class)
    public void addUser(User u, boolean adm, String author) {
        if(adm) {
            entityManager.getTransaction().begin();
            try {
                u.setPassword(sha256(u.getPassword()));
            } catch (Exception e) {
                System.out.println(e);
            }
            entityManager.persist(u);
            entityManager.getTransaction().commit();
            AuditLogRepo.createLog(u, "Add user", author);

            //return u;
        }
        else {
            System.out.println("CAN'T ADD USER, YOU ARE NOT THE ADMIN");
        }
    }


    @Transactional(rollbackOn = Exception.class)
    public void deleteUser(String username, String password, String author) {
        entityManager.getTransaction().begin();
//        entityManager.remove(u);
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username=:username AND u.password=:password ").setParameter("username", username).setParameter("password", password);
        User u = (User) query.getSingleResult();
        u.setDelete(true);
        entityManager.getTransaction().commit();
        AuditLogRepo.createLog(u, "Soft delete", author);
    }



    @Transactional(rollbackOn = Exception.class)
    public void undoDelete(String username, String password, String author) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username=:username AND u.password=:password ").setParameter("username", username).setParameter("password", password);
        User u = (User) query.getSingleResult();
        u.setDelete(false);
        entityManager.getTransaction().commit();
        AuditLogRepo.createLog(u, "Undo delete", author);

    }


    @Transactional(rollbackOn = Exception.class)
    public void addUserToGroup(String username, String groupname, String author)
    {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username=:username").setParameter("username", username);
        User u = (User) query.getSingleResult();
        System.out.println("GRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOUUPP" + "   " + groupname);
        Query query2 = entityManager.createQuery("SELECT g FROM Group g WHERE g.groupname=:groupname").setParameter("groupname", groupname);
        Group grp = (Group) query2.getSingleResult();
        //Group grp = entityManager.find(Group.class, groupname);
//        Gusers.add(u);
        List<Group> Ugroups = u.getGroups();
        Ugroups.add(grp);
        u.setGroups(Ugroups);
        System.out.println("GRRRRRRRRRRRRRRRRRROOOUUPP" + "   " + grp.getGroupname());

        System.out.println("GRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOUUPP" + "   " + Ugroups);

        entityManager.getTransaction().commit();
        AuditLogRepo.createLog(u, "Adding " + u.getUsername() +  "to " + grp.getGroupname(), author);

    }


    @Transactional(rollbackOn = Exception.class)
    public void removeUserFromGroup(String username, String groupname, String author) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username=:username").setParameter("username", username);
        User u = (User) query.getSingleResult();
        System.out.println("GRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOUUPP" + "   " + groupname);
        Query query2 = entityManager.createQuery("SELECT g FROM Group g WHERE g.groupname=:groupname").setParameter("groupname", groupname);
        Group grp = (Group) query2.getSingleResult();
       // Group grp = entityManager.find(Group.class, groupname);
//        Gusers.add(u);
        List<Group> Ugroups = u.getGroups();
        if(Ugroups.contains(grp)) {
            Ugroups.remove(grp);
        }
        u.setGroups(Ugroups);
        System.out.println("GRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOUUPP" + "   " + Ugroups);

        entityManager.getTransaction().commit();
        AuditLogRepo.createLog(u, "Removing " + u.getUsername() +  "from " + grp.getGroupname(), author);

    }



    @Transactional(rollbackOn = Exception.class)
    public String resetPassword(String oldPassword, String newPassword, boolean adm, String author) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username=:username").setParameter("username", author);
        User u = (User) query.getSingleResult();
        System.out.println("U.GETPASSWORDDDD" + u.getPassword());
        System.out.println("U.USERNAAAMEE" + u.getUsername());
        System.out.println("HASSHEDD PASSWORDDD" + sha256(oldPassword));

        if(adm || u.getPassword().equals(sha256(oldPassword))) {
                Query query2 = entityManager.createQuery("SELECT u FROM User u WHERE u.password=:oldPassword").setParameter("oldPassword", sha256(oldPassword));
                User user = (User) query2.getSingleResult();
                user.setPassword(sha256(newPassword));
                entityManager.getTransaction().commit();
                AuditLogRepo.createLog(u, "Password reset to user: " + u.getUsername(), author);
                return "Password Reset Successful";

        } else {
            entityManager.getTransaction().commit();
            return "You cannot reset password";
        }


    }




}
