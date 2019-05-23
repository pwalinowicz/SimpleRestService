package com.rest.blog.server;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PostDAO {
    
    private final Map<String,Post> posts = new HashMap<>();

    public Collection<Post> getAllPosts(){
        return posts.values();
    }
    
    public Post updatePostById(String id, Post post){
        return posts.put(id, post);
    }
    
    public Post getPostById(String id){
        return posts.get(id);
    }
    
    public Post deletePostById(String id){
        return posts.remove(id);
    }
        
    public boolean createPost(Post post){
        String id = post.getId();
        
        if(!hasId(id) && id != null){
            posts.put(id,post);            
            return true;
        }        
        return false;
    }

    public boolean hasId(String id){
        if(posts.containsKey(id) && id != null){
            return true;
        }
        return false;
    }
}
