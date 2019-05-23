package com.rest.blog.client.client;

import javax.json.Json;

public class JsonStringObjectBuilder implements IStringObjectBuilder {
    @Override
    public String createStringObject(String id, String title, String content) {

        return Json.createObjectBuilder()
                .add("id", id)
                .add("title", title)
                .add("content", content)
                .build()
                .toString();
    }
}
