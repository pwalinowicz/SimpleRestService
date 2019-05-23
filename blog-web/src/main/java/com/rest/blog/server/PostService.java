package com.rest.blog.server;

import java.net.URI;
import java.net.URISyntaxException;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.enterprise.context.ApplicationScoped;

@Path("posts")
@ApplicationScoped
public class PostService {
    
    @Inject
    private PostDAO dao;
     
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllPosts(){       
        return Response.ok(dao.getAllPosts()).build();
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addPost(@Valid Post post) {
        if (dao.createPost(post)) {
            URI location;
            try {
                location = new URI("posts/" + post.getId());
                return Response.created(location).entity(post).build();

            } catch (URISyntaxException e) {
                System.out.println(e.getMessage());
                return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Invalid input").build();
            }
        }
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Invalid input").build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updatePost(@Valid Post post) {
        try {
            String id = post.getId();
            if (dao.hasId(id)) {
                dao.updatePostById(id, post);
                return Response.status(Response.Status.CREATED).entity(post).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Post not found").build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Invalid input").build();
        }
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Response getPostById(@PathParam("id") String id){
        Post post = dao.getPostById(id);
        if(post != null){
            return Response.ok(post).build();
        }
        return Response.status(Response.Status.NO_CONTENT).entity("No content").build();
    }
        
    @DELETE
    @Path("{id}")
    public Response deletePost(@PathParam("id") String id){
        Post post = dao.deletePostById(id);
        if(post != null){
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Post not found").build();
    }  
}
    
