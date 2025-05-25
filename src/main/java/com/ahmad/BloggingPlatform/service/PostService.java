package com.ahmad.BloggingPlatform.service;

import com.ahmad.BloggingPlatform.exception.ResourceNotFoundException;
import com.ahmad.BloggingPlatform.model.Post;
import com.ahmad.BloggingPlatform.model.User;
import com.ahmad.BloggingPlatform.repository.PostRepository;
import com.ahmad.BloggingPlatform.request.CreatePostRequest;
import com.ahmad.BloggingPlatform.request.UpdatePostRequest;
import com.ahmad.BloggingPlatform.response.PostDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PostService implements IPostService{
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostService(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Post createPost(CreatePostRequest request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCategory(request.getCategory());
        post.setTags(request.getTags());
        post.setCreatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }


    @Override
    public Post updatePostById(UpdatePostRequest request , long postId) {
        return postRepository.findById(postId)
                .map(existingPost -> updatePost(request,existingPost))
                .map(postRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Post with ID " + postId + " NotFound"));
    }

    private Post updatePost(UpdatePostRequest request , Post existingPost){
        existingPost.setTitle(request.getTitle());
        existingPost.setCategory(request.getCategory());
        existingPost.setContent(request.getContent());
        existingPost.setTags(request.getTags());
        existingPost.setUpdatedAt(LocalDateTime.now());

        return existingPost;
    }

    @Override
    public Post getPostById(long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post with ID " + postId + " Not Found!"));
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Collection<Post> getPostByTags(Set<String> tags) {
        return postRepository.findPostsByTags(tags).orElseThrow(() -> new ResourceNotFoundException("Post with tags " + tags + "Not Found!"));
    }

    @Override
    public void deletePostsById(long postId) {
        postRepository.findById(postId).ifPresentOrElse(postRepository :: delete , () -> new ResourceNotFoundException("Post with ID " + postId + " NotFound!"));
    }

    @Override
    public List<PostDto> convertAllToDto(List<Post> posts) {
        return posts.stream().map(this::convertToDto).toList();
    }

    @Override
    public Collection<PostDto> getCollectedPosts(Collection<Post> posts , Set<String> tags) {
        return posts.stream().map(this::convertToDto).filter(post -> post.getTags() == tags).toList();
    }

    public PostDto convertToDto(Post post){
        return modelMapper.map(post, PostDto.class);
    }
}
