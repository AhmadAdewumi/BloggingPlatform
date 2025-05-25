package com.ahmad.BloggingPlatform.service;

import com.ahmad.BloggingPlatform.model.Post;
import com.ahmad.BloggingPlatform.request.CreatePostRequest;
import com.ahmad.BloggingPlatform.request.UpdatePostRequest;
import com.ahmad.BloggingPlatform.response.PostDto;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IPostService {
    Post createPost(CreatePostRequest request);
    Post updatePostById(UpdatePostRequest request, long postId);
    Post getPostById(long postId);
    List<Post> getAllPosts();
    Collection<Post> getPostByTags(Set<String> tags);
    void deletePostsById(long postId);
    List<PostDto> convertAllToDto(List<Post> posts);
    Collection<PostDto> getCollectedPosts(Collection<Post> post , Set<String> tags);
    PostDto convertToDto(Post post);

}
