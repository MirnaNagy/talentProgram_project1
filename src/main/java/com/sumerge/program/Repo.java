package com.sumerge.program;

import java.util.ArrayList;

public class Repo {

    static ArrayList<User> usersList = new ArrayList<User>();

    public Repo() {
        usersList.add(new User("Ahmed", 1, "ahmed@gmail.com"));
        usersList.add(new User("Mirna", 2, "mirna@gmail.com"));
        usersList.add(new User("Amr", 3,"amr@gmail.com"));
        usersList.add(new User("Salma", 4, "salma@gmail.com"));
    }


    public void printUser(User user) {
        System.out.println("Name: " + user.getName());
        System.out.println("ID: " + user.getId());
        System.out.println("Email: " + user.getEmail());
    }

    public ArrayList<User> getAllUsers() {
        return usersList;
    }

    public User addUser(User user){
        usersList.add(user);
        return user;
    }

    public ArrayList<User> deleteUserbyID(int id) {
        for(int i = 0; i<usersList.size(); i++)
        {
            if (usersList.get(i).getId() == id ) {
                usersList.remove(i);
                break;
            }
        }
        return usersList;
    }

    public User findUserbyID(int id) {
        for(int i = 0; i<usersList.size(); i++)
        {
            if (usersList.get(i).getId() == id ){
              return usersList.get(i);
            }
        }
        return null;
    }

    public User findUserbyName(String name) {
        for(int i = 0; i<usersList.size(); i++)
        {
            if (usersList.get(i).getName().equalsIgnoreCase(name)){
                return usersList.get(i);
            }
        }
        return null;
    }


    public User findUserbyEmail(String mail) {
        for(int i = 0; i<usersList.size(); i++)
        {
            if (usersList.get(i).getEmail().equalsIgnoreCase(mail)){
                return usersList.get(i);
            }
        }
        return null;
    }


    public ArrayList<User> update(User u) {
        for(int i = 0; i<usersList.size(); i++)
        {
            if (usersList.get(i).getId() == u.getId() ){
                usersList.get(i).setUser(u);
            }
        }
        return usersList;
    }

}
