package com.sumerge.program;


import static java.util.logging.Level.SEVERE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.logging.Logger;


@RequestScoped
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Path("users")

public class resources {


    private static final Logger LOGGER = Logger.getLogger(resources.class.getName());

    @EJB
    private static Repo repo = new Repo();
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



    @PUT
    public Response editUser(User user) {
        try {
            if (user.getId()== -1)
                throw new IllegalArgumentException("Can't edit student since it is not found");
            repo.update(user);
            return Response.ok().
                    build();
        } catch (Exception e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e.getClass() + ": " + e.getMessage()).
                    build();
        }
    }


    @GET
    @Path("e/{email}")
    public Response getUserbyEmail(@PathParam("email") String email) {
        // @QueryParam("name") String name
        try {
            return Response.ok().
                    entity(repo.findUserbyEmail(email)).
                    build();
        } catch (Exception e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e.getClass() + ": " + e.getMessage()).
                    build();
        }
    }

    @GET
    @Path("n/{name}")
    public Response getUserByName(@PathParam("name") String name) {
        // @QueryParam("name") String name
        try {
            return Response.ok().
                    entity(repo.findUserbyName(name)).
                    build();
        } catch (Exception e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e.getClass() + ": " + e.getMessage()).
                    build();
        }
    }


    @GET
    @Path("{id}")
    public Response getUserById(@PathParam("id") int id) {
                               // @QueryParam("name") String name
        try {
            return Response.ok().
                    entity(repo.findUserbyID(id)).
                    build();
        } catch (Exception e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e.getClass() + ": " + e.getMessage()).
                    build();
        }
    }



    @DELETE
    @Path("del/{id}")
    public Response deleteStudent(@PathParam("id") int id) {
        try {
            repo.deleteUserbyID(id);
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
