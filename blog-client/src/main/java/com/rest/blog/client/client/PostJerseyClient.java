package com.rest.blog.client.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

public class PostJerseyClient implements IPostClient {

    private Client client;

    public PostJerseyClient() {
        this.client = ClientBuilder.newClient();
    }

    @Override
    public Response delete(String uri, IMediaTypeObject mediaTypeObjectIn){
        Response response = client.target(uri)
                .request(mediaTypeObjectIn.getMediaType())
                .delete();

        return response;
    }

    @Override
    public Response get(String uri, IMediaTypeObject mediaTypeObjectIn) {
        Response response = client.target(uri)
                .request(mediaTypeObjectIn.getMediaType())
                .get();

       return response;
    }

    @Override
    public Response post(String uri, String stringEntity, IMediaTypeObject mediaTypeObjectIn, IMediaTypeObject mediaTypeObjectOut) {
        Response response =  client.target(uri)
                .request(mediaTypeObjectIn.getMediaType())
                .post(Entity.entity(stringEntity, mediaTypeObjectOut.getMediaType()));

        return response;
    }

    @Override
    public Response put(String uri, String stringEntity, IMediaTypeObject mediaTypeObjectIn, IMediaTypeObject mediaTypeObjectOut){
        Response response =  client.target(uri)
                .request(mediaTypeObjectIn.getMediaType())
                .put(Entity.entity(stringEntity, mediaTypeObjectOut.getMediaType()));

        return response;
    }
}
