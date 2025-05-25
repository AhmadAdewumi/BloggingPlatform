package com.ahmad.BloggingPlatform.controller;

import com.ahmad.BloggingPlatform.exception.AlreadyExistException;
import com.ahmad.BloggingPlatform.exception.ResourceNotFoundException;
import com.ahmad.BloggingPlatform.model.Post;
import com.ahmad.BloggingPlatform.request.CreatePostRequest;
import com.ahmad.BloggingPlatform.request.UpdatePostRequest;
import com.ahmad.BloggingPlatform.response.ApiResponseBody;
import com.ahmad.BloggingPlatform.response.PostDto;
import com.ahmad.BloggingPlatform.service.IPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final IPostService postService;


    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Create a new post",
            description = "Creates a new blog post with the given details"
    )
    @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Post created successfully"),
            @ApiResponse(responseCode = "409", description = "Conflict - Unable to create post")
    })
    @PostMapping("/add")
    public ResponseEntity<ApiResponseBody> createPost(@Valid @RequestBody CreatePostRequest request){
        try {
            Post thePost = postService.createPost(request);
            PostDto convertedPost = postService.convertToDto(thePost);
            return ResponseEntity.ok(new ApiResponseBody("Post created Successfully", convertedPost));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseBody("Unable to create post " , null));
        }
    }

    @Operation(
            summary = "Update an existing post",
            description = "Updates the blog post identified by the postId"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post updated successfully"),
            @ApiResponse(responseCode = "409", description = "Conflict - Unable to update post")
    })
    @PutMapping("/update/{postId}")
    public ResponseEntity<ApiResponseBody> updatePosts(@Valid @RequestBody UpdatePostRequest request , @PathVariable long postId){
        try {
            Post thePost = postService.updatePostById(request,postId);
            PostDto convertedPost = postService.convertToDto(thePost);
            return ResponseEntity.ok(new ApiResponseBody("Post updated Successfully", convertedPost));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseBody("Unable to create post " , null));
        }
    }

    @Operation(
            summary = "Get a post by ID",
            description = "Retrieves the details of a post using its unique ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Not Found - Post with the given ID does not exist")
    })
    @GetMapping("/id/{postId}")
    public ResponseEntity<ApiResponseBody> getPostById(@PathVariable long postId){
        try {
            Post thePost = postService.getPostById(postId);
            PostDto convertedPost = postService.convertToDto(thePost);
            return ResponseEntity.ok(new ApiResponseBody("Post retrieved Successfully", convertedPost));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponseBody("Unable to retrieve post , post with ID " + postId + " Not Found!" , null));
        }
    }

    @Operation(
            summary = "Get all posts",
            description = "Retrieves a list of all blog posts"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully"),
            @ApiResponse(responseCode = "409", description = "Conflict - Unable to retrieve posts")
    })
    @GetMapping("/all")
    public ResponseEntity<ApiResponseBody> getAllPosts(){
        try {
            List<Post> posts = postService.getAllPosts();
            Collection<PostDto> convertedPost = postService.convertAllToDto(posts);
            return ResponseEntity.ok(new ApiResponseBody("All Posts retrieved Successfully", convertedPost));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseBody("Unable to retrieve posts " , null));
        }
    }

    @Operation(
            summary = "Get posts by tags",
            description = "Retrieves posts that contain the specified tags"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully"),
            @ApiResponse(responseCode = "409", description = "Conflict - Unable to retrieve posts by tags")
    })
    @GetMapping("/tags")
    public ResponseEntity<ApiResponseBody> getPostByTags(@RequestParam("tags")Set<String> tags){
        try {
            Collection<Post> thePost = postService.getPostByTags(tags);
            Collection<PostDto> convertedPost = postService.getCollectedPosts(thePost,tags);
            return ResponseEntity.ok(new ApiResponseBody("Tags retrieved Successfully", convertedPost));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseBody("Unable to retrieve tags " , null));
        }
    }

    @Operation(
            summary = "Delete a post by ID",
            description = "Deletes the post identified by the postId"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post deleted successfully"),
            @ApiResponse(responseCode = "409", description = "Conflict - Unable to delete post")
    })
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<ApiResponseBody> deletePostById(@PathVariable long postId){
        try {
            postService.deletePostsById(postId);
            return ResponseEntity.ok(new ApiResponseBody("Tags retrieved Successfully", null));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseBody("Unable to delete posts " , null));
        }
    }




}
