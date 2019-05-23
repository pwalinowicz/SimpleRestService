package com.rest.blog.client.client;

import javax.ws.rs.core.Response;

public interface IPostClient {

    Response delete(String uri, IMediaTypeObject mediaTypeObjectIn);
    Response get(String uri, IMediaTypeObject mediaTypeObjectIn);
    Response post(String uri, String stringEntity, IMediaTypeObject mediaTypeObjectIn, IMediaTypeObject mediaTypeObjectOut);
    Response put(String uri, String stringEntity, IMediaTypeObject mediaTypeObjectIn, IMediaTypeObject mediaTypeObjectOut);


}
