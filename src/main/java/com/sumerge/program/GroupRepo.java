package com.sumerge.program;

import entities.Group;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class GroupRepo {

    EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

    public List<Group> getAllGroups() {
        entityManager.getTransaction().begin();
        TypedQuery<Group> query = entityManager.createNamedQuery("group.findAll", Group.class);
        List<Group> results = query.getResultList();
        //System.out.println("RESSSULTTTTTTTTTTTTSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS" + results.get(0).getUsers().get(0).getUsername());
        entityManager.getTransaction().commit();
        return results;
    }

    public void addGroup(Group g) {
        entityManager.getTransaction().begin();
        entityManager.persist(g);
        entityManager.getTransaction().commit();
        //return u;
    }

//NOT WORKINGG
    public void deleteGroup(Group g) {
        entityManager.getTransaction().begin();
//        entityManager.remove(u);
//        Query query = entityManager.createQuery("SELECT g FROM Group g WHERE g.groupname=:groupname").setParameter("groupname", groupname);
//        Group g = (Group) query.getSingleResult();

        if(!entityManager.contains(g))
            g = entityManager.merge(g);

        entityManager.remove(g);
        entityManager.getTransaction().commit();
    }


}
