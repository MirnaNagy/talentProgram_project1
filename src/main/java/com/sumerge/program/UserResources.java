package com.sumerge.program;


import entities.User;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@RequestScoped
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Path("users")

public class UserResources {

    private static final Logger LOGGER = Logger.getLogger(UserResources.class.getName());

    @EJB
    private static UserRepo repo = new UserRepo();
    @Context
    HttpServletRequest request;

    @Context
    private SecurityContext securityContext;

    @GET
    public Response getAllUsers() {
        System.out.println("Name: "+securityContext.getUserPrincipal().toString());
        System.out.println("Auth Scheme : "+securityContext.isUserInRole("admin"));
        try {
            return Response.ok().
                    entity(repo.getAllUsers(securityContext.isUserInRole("admin"))).
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
    public Response userAdd(User u) {
        try {
            if (u.getUserID() == -1)
                throw new IllegalArgumentException("Can't add User");
            repo.addUser(u,securityContext.isUserInRole("admin"));
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
    public Response userDelete(User u) {
        try {
//            if (u.getUserID() == -1)
//                throw new IllegalArgumentException("Can't delete User");
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
    public Response userUndoDelete(User u) {
        try {
//            if (u.getUserID() == -1)
//                throw new IllegalArgumentException("Can't delete User");
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
//                throw new IllegalArgumentException("Can't delete User");
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
//                throw new IllegalArgumentException("Can't delete User");
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
//                throw new IllegalArgumentException("Can't delete User");
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


    @Path("reset")
    @PUT
    public Response userPasswordReset(@QueryParam("oldPassword") String oldPassword, @QueryParam("newPassword") String newPassword) {
        try {
//            if (u.getUserID() == -1)
//                throw new IllegalArgumentException("Can't delete User");
            String response = repo.resetPassword(oldPassword, newPassword, securityContext.isUserInRole("admin"), securityContext.getUserPrincipal().toString());
            return Response.ok().
                    entity(response).
                    build();
        } catch (Exception e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e.getClass() + ": " + e.getMessage()).
                    build();
        }
    }




}

