package com.sumerge.program;


import Entities.group;
import Entities.user;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@RequestScoped
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Path("users")

public class userResources {

    private static final Logger LOGGER = Logger.getLogger(userResources.class.getName());

    @EJB
    private static userRepo repo = new userRepo();
    @Context
    HttpServletRequest request;

    @GET
    public Response getAllUsers() {
        try {
            return Response.ok().
                    entity(repo.getAllUsers()).
                    build();
        } catch (Exception e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e.getClass() + ": " + e.getMessage()).
                    build();
        }
    }

    @Path("add")
    @POST
    public Response userAdd(user u) {
        try {
            if (u.getUserID() == -1)
                throw new IllegalArgumentException("Can't add user");
            repo.addUser(u);
            return Response.ok().
                    build();
        } catch (Exception e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e.getClass() + ": " + e.getMessage()).
                    build();
        }
    }


    @Path("delete")
    @DELETE
    public Response userDelete(user u) {
        try {
//            if (u.getUserID() == -1)
//                throw new IllegalArgumentException("Can't delete user");
            repo.deleteUser(u.getUsername(), u.getPassword());
            return Response.ok().
                    build();
        } catch (Exception e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e.getClass() + ": " + e.getMessage()).
                    build();
        }
    }

    @Path("undo")
    @PUT
    public Response userUndoDelete(user u) {
        try {
//            if (u.getUserID() == -1)
//                throw new IllegalArgumentException("Can't delete user");
            repo.undoDelete(u.getUsername(), u.getPassword());
            return Response.ok().
                    build();
        } catch (Exception e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e.getClass() + ": " + e.getMessage()).
                    build();
        }
    }

    @Path("addgroup")
    @PUT
    public Response userAddGroup(@QueryParam("username")String username, @QueryParam("groupname") String groupname) {
        try {
//            if (u.getUserID() == -1)
//                throw new IllegalArgumentException("Can't delete user");
            System.out.println("GRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOUUPP FROM RESOURCES" + "   " + groupname + "   " + username);

            repo.addUserToGroup(username, groupname);
            return Response.ok().
                    build();
        } catch (Exception e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e.getClass() + ": " + e.getMessage()).
                    build();
        }
    }


    @Path("removegroup")
    @PUT
    public Response userRemoveGroup(@QueryParam("username")String username, @QueryParam("groupname") String groupname) {
        try {
//            if (u.getUserID() == -1)
//                throw new IllegalArgumentException("Can't delete user");
            System.out.println("GRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOUUPP FROM RESOURCES" + "   " + groupname + "   " + username);

            repo.removeUserFromGroup(username, groupname);
            return Response.ok().
                    build();
        } catch (Exception e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e.getClass() + ": " + e.getMessage()).
                    build();
        }
    }


    @Path("movegroup")
    @PUT
    public Response userMoveGroup(@QueryParam("username")String username, @QueryParam("groupname") String groupname, @QueryParam("groupname2") String groupname2) {
        try {
//            if (u.getUserID() == -1)
//                throw new IllegalArgumentException("Can't delete user");
            System.out.println("GRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOUUPP FROM RESOURCES" + "   " + groupname + "   " + username);
            repo.removeUserFromGroup(username, groupname);
            repo.addUserToGroup(username,groupname2);
            return Response.ok().
                    build();
        } catch (Exception e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e.getClass() + ": " + e.getMessage()).
                    build();
        }
    }



}

