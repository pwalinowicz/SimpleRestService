package com.rest.blog.client.client;

import javax.ws.rs.core.MediaType;

public class JsonMediaTypeObject implements IMediaTypeObject{
    @Override
    public String getMediaType() {
        return MediaType.APPLICATION_JSON;
    }
}
