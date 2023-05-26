package com.service.dida.domain.post.controller;

import com.service.dida.domain.post.service.GetPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetPostController {

    private final GetPostService getPostService;
}
