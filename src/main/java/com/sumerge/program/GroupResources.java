package com.sumerge.program;


import entities.Group;

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
@Path("Group")


public class GroupResources {
    private static final Logger LOGGER = Logger.getLogger(UserResources.class.getName());

    @EJB
    private static GroupRepo repo = new GroupRepo();
    @Context
    HttpServletRequest request;

    @GET
    public Response getAllGroups() {
        try {
            return Response.ok().
                    entity(repo.getAllGroups()).
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
    public Response groupAdd(Group g) {
        try {
            if (g.getGroupID() == -1)
                throw new IllegalArgumentException("Can't add Group");
            repo.addGroup(g);
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
    public Response groupDelete(Group g) {
        try {
            if (g.getGroupID() == -1)
                throw new IllegalArgumentException("Can't add Group");
            repo.deleteGroup(g);
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
