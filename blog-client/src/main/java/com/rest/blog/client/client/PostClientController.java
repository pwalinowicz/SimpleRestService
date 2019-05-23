package com.rest.blog.client.client;

import javax.ws.rs.core.Response;

public class PostClientController {

    IPostClient postClient;
    private IStringObjectBuilder stringObjectBuilder;
    private IMediaTypeObject mediaTypeObjectIn;
    private IMediaTypeObject mediaTypeObjectOut;

    private static final String MAIN_URI
            = "http://localhost:8080/blog-web/posts";

    public PostClientController() {
        this.postClient = new PostJerseyClient();
        stringObjectBuilder = new JsonStringObjectBuilder();
        mediaTypeObjectIn = new JsonMediaTypeObject();
        mediaTypeObjectOut = new JsonMediaTypeObject();
    }

    public Response deletePostById(String id) {
        String uri = MAIN_URI + "/" + id;
        return postClient.delete(uri, mediaTypeObjectIn);
    }

    public Response getAllPosts() {
        return postClient.get(MAIN_URI, mediaTypeObjectIn);
    }

    public Response addPost(String id, String title, String content) {
        String uri = MAIN_URI;
        String stringEntity = stringObjectBuilder.createStringObject(id, title, content);
        return postClient.post(uri, stringEntity, mediaTypeObjectIn, mediaTypeObjectOut);
    }

    public Response getPostById(String id) {
        String uri = MAIN_URI + "/" + id;
        return postClient.get(uri, mediaTypeObjectIn);
    }

    public Response modifyPost(String id, String title, String content) {
        String uri = MAIN_URI;
        String stringEntity = stringObjectBuilder.createStringObject(id, title, content);
        return postClient.put(uri, stringEntity, mediaTypeObjectIn, mediaTypeObjectOut);
    }

    public void setPostClient(IPostClient postClient) {
        this.postClient = postClient;
    }


    public void setMediaTypeObjectOut(IMediaTypeObject mediaTypeObjectOut) {
        this.mediaTypeObjectOut = mediaTypeObjectOut;
    }

    public void setMediaTypeObjectIn(IMediaTypeObject mediaTypeObject) {
        this.mediaTypeObjectIn = mediaTypeObject;
    }

    public void setStringObjectBuilder(IStringObjectBuilder stringObjectBuilder) {
        this.stringObjectBuilder = stringObjectBuilder;
    }
}
