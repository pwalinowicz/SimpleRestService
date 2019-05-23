package com.rest.blog.client.console;

import com.rest.blog.client.client.PostClientController;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class ConsolePostClient {

    PostClientController postClientController;
    String id, title, content;
    boolean hasExited = false;

    public ConsolePostClient() {
        this.postClientController =  new PostClientController();
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onStart() {
        handleMenu();
    }

    private void handleMenu(){
        System.out.println();
        System.out.println();
        System.out.println(".........BLOG POST CREATION.........");

        while(hasExited == false) {
            printMenuHeadlines();
            try {
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                scanner.nextLine();

                Response response = responseFromMenuChoice(choice);
                if(response != null){
                    analyzeResponse(response);
                }
            } catch (InputMismatchException e){
                System.out.println("Use only numbers while choosing");
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void printMenuHeadlines(){
        System.out.println();
        System.out.println("....................................");
        System.out.println("What would you like to do?");
        System.out.println("....................................");
        System.out.println("[1] - list all existing blog posts");
        System.out.println("[2] - create 1 new post");
        System.out.println("[3] - modify 1 existing post");
        System.out.println("[4] - delete 1 existing post");
        System.out.println("[5] - see 1 existing post");
        System.out.println("[other] - exit");
        System.out.println();
    }

    private Response responseFromMenuChoice(int choice){
        Response response;
        Scanner scanner = new Scanner(System.in);
        switch (choice) {
            case 1:
                System.out.println("All existing blog posts");
                response = postClientController.getAllPosts();
                break;
            case 2:
                System.out.println("New post creation");

                System.out.println("Set id: ");
                id = scanner.nextLine();

                System.out.println("Set title: ");
                title = scanner.nextLine();

                System.out.println("Set content: ");
                content = scanner.nextLine();

                response = postClientController.addPost(id, title, content);
                break;
            case 3:
                System.out.println("Post modification");
                System.out.println("id of the post to be modified: ");
                id = scanner.nextLine();

                response = postClientController.getPostById(id);

                if(response.getStatus() == Response.Status.OK.getStatusCode())
                {
                    System.out.println(response.readEntity(String.class));
                    System.out.println("Is this the post you would like to modify? Y/N");
                    String answer = scanner.nextLine().trim();
                    if(answer.equalsIgnoreCase("y")){
                        System.out.println("Set new title: ");
                        title = scanner.nextLine();

                        System.out.println("Set new content: ");
                        content = scanner.nextLine();

                        response = postClientController.modifyPost(id, title, content);
                        break;
                    } else {
                        System.out.println("No other Post with given ID exists");
                        response = null;
                        break;
                    }
                } else {
                    System.out.println("No Post with given ID exists");
                    response = null;
                    break;
                }

            case 4:
                System.out.println("Delete the post");
                System.out.println("id of the post to be deleted: ");
                id = scanner.nextLine();

                response = postClientController.getPostById(id);
                if(response.getStatus() == Response.Status.OK.getStatusCode())
                {
                    System.out.println(response.readEntity(String.class));
                    System.out.println("Is this the post you would like to delete? Y/N");
                    String answer = scanner.nextLine().trim();
                    if(answer.equalsIgnoreCase("y")){
                        response = postClientController.deletePostById(id);
                        break;
                    } else {
                        System.out.println("No other Post with given ID exists");
                        break;
                    }
                } else {
                    System.out.println("No Post with given ID exists");
                    break;
                }

            case 5:
                System.out.println("See the existing post");
                System.out.println("id of the post to be searched: ");
                id = scanner.nextLine();
                response = postClientController.getPostById(id);
                break;
            default:
                System.out.println("Exiting");
                hasExited = true;
                response = null;
        }
        return response;
    }

    private void analyzeResponse(Response response){
        int status = response.getStatus();

        switch(status){
            case 200:
                System.out.println(response.readEntity(String.class));
                System.out.println("Successful operation.");
                break;
            case 201:
                System.out.println(response.readEntity(String.class));
                System.out.println("Creation successful.");
                break;
            case 405:
                System.out.println("Invalid input.");
                break;
            case 404:
                System.out.println("Post not found.");
                break;
            case 204:
                System.out.println("No content.");
                break;
            case 400:
                System.out.println("Bad request. No changes applied.");
                break;
            default:
                break;
        }
    }
}
