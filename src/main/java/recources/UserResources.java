package recources;


import objects.Delete_Undo;
import objects.MovingGroups;
import objects.PasswordReset;
import repos.UserRepo;
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



    @Path("find")
    @GET
    public Response getUserByName(@QueryParam("username")String username) {
        try {
            return Response.ok().
                    entity(repo.getUserByUsername(username, securityContext.isUserInRole("admin"), securityContext.getUserPrincipal().toString())).
                    build();
        } catch (Exception e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e.getClass() + ": " + e.getMessage()).
                    build();
        }
    }


    @GET
    public Response getAllUsers() {
        System.out.println("NAAAAAMEEEEEEEEE: "+securityContext.getUserPrincipal().toString());
        System.out.println("Auth Scheme : "+securityContext.isUserInRole("admin"));
        try {
            return Response.ok().
                    entity(repo.getAllUsers(securityContext.isUserInRole("admin"), securityContext.getUserPrincipal().toString())).
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
            repo.addUser(u,securityContext.isUserInRole("admin"), securityContext.getUserPrincipal().toString());
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
    public Response userDelete(Delete_Undo u) {
        try {
//            if (u.getUserID() == -1)
//                throw new IllegalArgumentException("Can't delete User");
            repo.deleteUser(u, securityContext.getUserPrincipal().toString());
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
    public Response userUndoDelete(Delete_Undo u) {
        try {
//            if (u.getUserID() == -1)
//                throw new IllegalArgumentException("Can't delete User");
            repo.undoDelete(u, securityContext.getUserPrincipal().toString());
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
    public Response userAddGroup(MovingGroups mv) {
//        public Response userAddGroup(@QueryParam("username")String username, @QueryParam("groupname") String groupname) {
            try {
//            if (u.getUserID() == -1)
//                throw new IllegalArgumentException("Can't delete User");
//            System.out.println("GRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOUUPP FROM RESOURCES" + "   " + groupname + "   " + username);

            repo.addUserToGroup(mv.getUsername(), mv.getGroupname1(), securityContext.getUserPrincipal().toString());
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
    public Response userRemoveGroup(MovingGroups mv) {
        try {
//            if (u.getUserID() == -1)
//                throw new IllegalArgumentException("Can't delete User");
//            System.out.println("GRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOUUPP FROM RESOURCES" + "   " + groupname + "   " + username);

            repo.removeUserFromGroup(mv.getUsername(), mv.getGroupname1(), securityContext.getUserPrincipal().toString());
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
    public Response userMoveGroup(MovingGroups mv) {
        if(securityContext.isUserInRole("admin")) {
            try {
//            if (u.getUserID() == -1)
//                throw new IllegalArgumentException("Can't delete User");
//            System.out.println("GRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRROOOUUPP FROM RESOURCES" + "   " + groupname + "   " + username);
                repo.removeUserFromGroup(mv.getUsername(), mv.getGroupname1(), securityContext.getUserPrincipal().toString());
                repo.addUserToGroup(mv.getUsername(), mv.getGroupname2(), securityContext.getUserPrincipal().toString());
                return Response.ok().
                        build();
            } catch (Exception e) {
                LOGGER.log(SEVERE, e.getMessage(), e);
                return Response.serverError().
                        entity(e.getClass() + ": " + e.getMessage()).
                        build();
            }
        }
        else {
            return Response.serverError().
                    entity("Action not available").
                    build();
        }
    }


    @Path("reset")
    @PUT
    public Response userPasswordReset(PasswordReset passwordReset) {
        try {
//            if (u.getUserID() == -1)
//                throw new IllegalArgumentException("Can't delete User");
            String response = repo.resetPassword(passwordReset, securityContext.isUserInRole("admin"), securityContext.getUserPrincipal().toString());
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

