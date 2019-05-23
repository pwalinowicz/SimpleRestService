package com.rest.blog;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BlogTestIntegr {
        private static final String POSTS_URI = "http://localhost:8080/blog-web/posts/";

    public static final JsonObject POST_1_JSON = Json.createObjectBuilder()
            .add("id", "1")
            .add("title", "First title")
            .add("content", "First content")
            .build();

    public static final JsonObject POST_1_JSON_MODIFIED = Json.createObjectBuilder()
            .add("id", "1")
            .add("title", "Modified title")
            .add("content", "Modified content")
            .build();

    public static JsonObject POST_2_JSON = Json.createObjectBuilder()
            .add("id", "2")
            .add("title", "Second title")
            .add("content", "Second content")
            .build();

    public static JsonObject WRONG_POST = Json.createObjectBuilder()
            .add("wrong id", "2")
            .add("wrong title", "Second title")
            .add("wrong content", "Second content")
            .add("wrong property", "wrong property")
            .build();

    private Client client;

    @Before
    public void setup() {
        client = ClientBuilder.newClient();
    }

    @Test
    public void test_1_BlogWithoutPosts() {
        Response response = GET(POSTS_URI);
        assertEquals("[]", response.readEntity(String.class));
        assertEquals(200, response.getStatus());
    }

    @Test
    public void test_2_AddPosts() {
        Response response = POST(POSTS_URI, POST_1_JSON);
        assertEquals(201, response.getStatus());
        assertEquals(POST_1_JSON.toString(), response.readEntity(String.class));

        response = POST(POSTS_URI, POST_2_JSON);
        assertEquals(201, response.getStatus());
        assertEquals(POST_2_JSON.toString(), response.readEntity(String.class));
    }

    @Test
    public void test_3_GetPost() {
        Response response = GET(POSTS_URI + POST_1_JSON.getString("id"));
        assertEquals(POST_1_JSON.toString(), response.readEntity(String.class));
        assertEquals(200, response.getStatus());

        response = GET(POSTS_URI + POST_2_JSON.getString("id"));
        assertEquals(POST_2_JSON.toString(), response.readEntity(String.class));
        assertEquals(200, response.getStatus());
    }

    @Test
    public void test_4_GetAllPosts() {
        Response response = GET(POSTS_URI);
        assertEquals("[" + POST_1_JSON.toString() + "," + POST_2_JSON.toString() + "]", response.readEntity(String.class));
    }

    @Test
    public void test_5_DeletePosts() {
        String uriToPost1 = POSTS_URI + POST_1_JSON.getString("id");
        String uriToPost2 = POSTS_URI + POST_2_JSON.getString("id");

        Response response = DELETE(uriToPost1);
        assertEquals(200, response.getStatus());

        // Should now be gone
        response =  GET(uriToPost1);
        assertEquals(204, response.getStatus());

        response = DELETE(uriToPost2);
        assertEquals(200, response.getStatus());

        // Should now be gone
        response =  GET(uriToPost2);
        assertEquals(204, response.getStatus());

        response =  DELETE(uriToPost2);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void test_6_GetAllPostsShouldNowBeEmpty() {
        Response response = GET(POSTS_URI);
        assertEquals("[]", response.readEntity(String.class));
        assertEquals(200, response.getStatus());
    }

    @Test
    public void test_7_AddingPostForAlreadyDefinedId() {
        //add Post 1
        Response response = POST(POSTS_URI, POST_1_JSON);
        assertEquals(201, response.getStatus());

        //Try to add different post, but with the same id as post 1
        response = POST(POSTS_URI, POST_1_JSON_MODIFIED);
        assertEquals(405, response.getStatus());

        //post with wrong input data
        response = POST(POSTS_URI, WRONG_POST);
        assertEquals(400, response.getStatus());

        //check if post under id 1 is still the original post
        response = GET(POSTS_URI + "1");
        assertEquals(200, response.getStatus());
        assertEquals(POST_1_JSON.toString(), response.readEntity(String.class));
    }

    @Test
    public void test_8_ModifyPosts() {
        //only post 1 is present
        Response response = GET(POSTS_URI + "1");
        assertEquals(200, response.getStatus());
        assertEquals(POST_1_JSON.toString(), response.readEntity(String.class));

        //post found and modified
        response = PUT(POSTS_URI, POST_1_JSON_MODIFIED);
        assertEquals(201, response.getStatus());

        //post not found
        response = PUT(POSTS_URI, POST_2_JSON);
        assertEquals(404, response.getStatus());

        response = PUT(POSTS_URI, WRONG_POST);
        assertEquals(400, response.getStatus());

        //still only modified post 1 is present
        response = GET(POSTS_URI + "1");
        assertEquals(POST_1_JSON_MODIFIED.toString(), response.readEntity(String.class));
    }


    /* Helper methods */
    private Response GET(String uri) {
        Response response = null;
        try {
            response = client.target(uri)
                    .request(MediaType.APPLICATION_JSON)
                    .get();
        } catch (Exception e){
            System.out.println("GET unsuccessful");
            e.printStackTrace();
        }
        return response;
    }

    private Response POST(String uri, JsonObject json) {
        Response response = null;
        try {
            WebTarget webTarget = client.target(uri);
            response = webTarget.request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(json.toString(), MediaType.APPLICATION_JSON));
        } catch(Exception e) {
            System.out.println("POST unsuccessful");
            e.printStackTrace();
        }
        return response;
    }

    private Response DELETE(String uri) {
        Response response = null;
        try {
            response = client.target(uri)
                    .request(MediaType.APPLICATION_JSON)
                    .delete();
        } catch(Exception e) {
            System.out.println("DELETE unsuccessful");
            e.printStackTrace();
        }
        return response;
    }

    private Response PUT(String uri, JsonObject json) {
        Response response = null;
        try {
            response = client.target(uri)
                    .request(MediaType.APPLICATION_JSON)
                    .put(Entity.entity(json.toString(), MediaType.APPLICATION_JSON));
        } catch(Exception e) {
            System.out.println("PUT unsuccessful");
            e.printStackTrace();
        }
        return response;
    }
}
