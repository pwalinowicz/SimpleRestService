package com.rest.blog.server;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

@XmlRootElement
@JsonbPropertyOrder({ "id", "title", "content"})
@XmlType(name = "Response", propOrder={"id","title","content"})
public class Post {

    @NotEmpty
    @JsonbProperty("id")
    private String id;

    @NotNull
    @JsonbProperty("title")
    @XmlElement(required=true)
    private String title;

    @NotNull
    @JsonbProperty("content")
    @XmlElement(required=true)
    private String content;

    public Post() {
    }

    public Post(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
