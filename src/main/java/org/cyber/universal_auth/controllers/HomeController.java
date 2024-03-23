package org.cyber.universal_auth.controllers;

import lombok.RequiredArgsConstructor;
import org.cyber.universal_auth.dto.Post;
import org.cyber.universal_auth.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final PostService postService;
    @GetMapping("/")
    public List<Post>  getAllPost(){
        return postService.getAllPost();
    }

}
