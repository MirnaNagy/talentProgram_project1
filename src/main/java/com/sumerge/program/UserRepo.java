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
   // static ArrayList<User> usersList = new ArrayList<User>();

//
//    public UserRepo() {
//        usersList.add(new User(1, "Ahmed", "ahmed@gmail.com", "ahmed", "admin", false));
//    }



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
    public List<User> getAllUsers(boolean adm) {
        entityManager.getTransaction().begin();
        List<User> results;
        if(adm) {
            TypedQuery<User> query = entityManager.createNamedQuery("user.findAll_admin", User.class);
            results = query.getResultList();
        }
        else {
            TypedQuery<User> query = entityManager.createNamedQuery("user.findAll", User.class);
            results = query.getResultList();
//            Query query = entityManager.createNativeQuery("SELECT usr.userID, usr.username, usr.email FROM User usr WHERE usr.delete= false");
//            results = query.getResultList();

        }



//        try {
//            for (int i = 0; i < results.size(); i++) {
//                String hashed = sha256(results.get(i).getPassword());
//                results.get(i).setPassword(hashed);
//            }
//        }catch (Exception e) {
//            System.out.println(e);
//        }

      //  System.out.println("RESSSULTTTTTTTTTTTTSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS" + results.get(0).getGroups().get(0).getGroupname());
        entityManager.getTransaction().commit();
        return results;
    }


    @Transactional(rollbackOn = Exception.class)
    public void addUser(User u, boolean adm) {
        if(adm) {
            entityManager.getTransaction().begin();
            try {
                u.setPassword(sha256(u.getPassword()));
            } catch (Exception e) {
                System.out.println(e);
            }
            entityManager.persist(u);
            entityManager.getTransaction().commit();
            //return u;
        }
        else {
            System.out.println("CAN'TTT ADD USER ENTA MSH ADMIN");
        }
    }


    @Transactional(rollbackOn = Exception.class)
    public void deleteUser(String username, String password) {
        entityManager.getTransaction().begin();
//        entityManager.remove(u);
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username=:username AND u.password=:password ").setParameter("username", username).setParameter("password", password);
        User u = (User) query.getSingleResult();
        u.setDelete(true);
        entityManager.getTransaction().commit();
    }



    @Transactional(rollbackOn = Exception.class)
    public void undoDelete(String username, String password) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username=:username AND u.password=:password ").setParameter("username", username).setParameter("password", password);
        User u = (User) query.getSingleResult();
        u.setDelete(false);
        entityManager.getTransaction().commit();
    }


    @Transactional(rollbackOn = Exception.class)
    public void addUserToGroup(String username, String groupname)
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
    }


    @Transactional(rollbackOn = Exception.class)
    public void removeUserFromGroup(String username, String groupname) {
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
    }



    @Transactional(rollbackOn = Exception.class)
    public String resetPassword(String oldPassword, String newPassword, boolean adm, String userName) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username=:username").setParameter("username", userName);
        User u = (User) query.getSingleResult();
        System.out.println("U.GETPASSWORDDDD" + u.getPassword());
        System.out.println("U.USERNAAAMEE" + u.getUsername());
        System.out.println("HASSHEDD PASSWORDDD" + sha256(oldPassword));

        if(adm || u.getPassword().equals(oldPassword)) {
                Query query2 = entityManager.createQuery("SELECT u FROM User u WHERE u.password=:oldPassword").setParameter("oldPassword", oldPassword);
                User user = (User) query2.getSingleResult();
                user.setPassword(sha256(newPassword));
                entityManager.getTransaction().commit();
                return "Password Reset Successful";


        } else {
            entityManager.getTransaction().commit();
            return "You cannot reset password";
        }


    }




}
