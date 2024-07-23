package tech.buildrun.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tech.buildrun.entity.UserEntity;
import tech.buildrun.service.UserService;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.UUID;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GET
    public Response findAll( @QueryParam("page") @DefaultValue("0") Integer page,
                                           @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        var users = userService.findAll(page, pageSize);

        return Response.ok(users).build();
    }

    @Transactional
    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") UUID userId, UserEntity userEntity){
        return Response.ok(userService.updateUser(userId, userEntity)).build();
    }

    @Transactional
    @POST
    public Response createUser(UserEntity userEntity){
        return Response.ok(userService.createUser(userEntity)).build();
    }

    @Path("/{id}")
    @GET
    public Response findById(@PathParam("id") UUID userId){
        return Response.ok(userService.findById(userId)).build();
    }

    @Path("/{id}")
    @DELETE
    @Transactional
    public Response deleteById(@PathParam("id") UUID userId){
        userService.deleteById(userId);
        return Response.noContent().build();
    }
}
