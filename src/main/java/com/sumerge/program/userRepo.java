package com.sumerge.program;

import Entities.group;
import Entities.user;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static jdk.vm.ci.sparc.SPARC.g1;

public class userRepo {

    EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
   // static ArrayList<user> usersList = new ArrayList<user>();

//
//    public userRepo() {
//        usersList.add(new user(1, "Ahmed", "ahmed@gmail.com", "ahmed", "admin", false));
//    }


    public List<user> getAllUsers() {
        entityManager.getTransaction().begin();
        TypedQuery<user> query = entityManager.createNamedQuery("user.findAll", user.class);
        List<user> results = query.getResultList();
        System.out.println("RESSSULTTTTTTTTTTTTSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS" + results.get(0).getGroups().get(0).getGroupname());
        entityManager.getTransaction().commit();
        return results;
    }



    public void addUser(user u) {
        entityManager.getTransaction().begin();
        entityManager.persist(u);
        entityManager.getTransaction().commit();
        //return u;
    }

    public void deleteUser(String username, String password) {
        entityManager.getTransaction().begin();
//        entityManager.remove(u);
        Query query = entityManager.createQuery("SELECT u FROM user u WHERE u.username=:username AND u.password=:password ").setParameter("username", username).setParameter("password", password);
        user u = (user) query.getSingleResult();
        u.setDelete(true);
        entityManager.getTransaction().commit();
    }



    public void undoDelete(String username, String password) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT u FROM user u WHERE u.username=:username AND u.password=:password ").setParameter("username", username).setParameter("password", password);
        user u = (user) query.getSingleResult();
        u.setDelete(false);
        entityManager.getTransaction().commit();
    }

    public void addUserToGroup(String username, String groupname)
    {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT u FROM user u WHERE u.username=:username").setParameter("username", username);
        user u = (user) query.getSingleResult();
        System.out.println("GRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOUUPP" + "   " + groupname);
        Query query2 = entityManager.createQuery("SELECT g FROM group g WHERE g.groupname=:groupname").setParameter("groupname", groupname);
        group grp = (group) query2.getSingleResult();
        //group grp = entityManager.find(group.class, groupname);
//        Gusers.add(u);
        List<group> Ugroups = u.getGroups();
        Ugroups.add(grp);
        u.setGroups(Ugroups);
        System.out.println("GRRRRRRRRRRRRRRRRRROOOUUPP" + "   " + grp.getGroupname());

        System.out.println("GRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOUUPP" + "   " + Ugroups);

        entityManager.getTransaction().commit();
    }

    public void removeUserFromGroup(String username, String groupname) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT u FROM user u WHERE u.username=:username").setParameter("username", username);
        user u = (user) query.getSingleResult();
        System.out.println("GRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOUUPP" + "   " + groupname);
        Query query2 = entityManager.createQuery("SELECT g FROM group g WHERE g.groupname=:groupname").setParameter("groupname", groupname);
        group grp = (group) query2.getSingleResult();
       // group grp = entityManager.find(group.class, groupname);
//        Gusers.add(u);
        List<group> Ugroups = u.getGroups();
        if(Ugroups.contains(grp)) {
            Ugroups.remove(grp);
        }
        u.setGroups(Ugroups);
        System.out.println("GRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOUUPP" + "   " + Ugroups);

        entityManager.getTransaction().commit();
    }


//    public user addUser(user user){
//        usersList.add(user);
//        return user;
//    }

//    public ArrayList<user> deleteUserbyID(int id) {
//        for(int i = 0; i<usersList.size(); i++)
//        {
//            if (usersList.get(i).getUserID() == id ) {
//                usersList.remove(i);
//                break;
//            }
//        }
//        return usersList;
//    }

//    public user findUserbyID(int id) {
//        for(int i = 0; i<usersList.size(); i++)
//        {
//            if (usersList.get(i).getId() == id ){
//                return usersList.get(i);
//            }
//        }
//        return null;
//    }


}
