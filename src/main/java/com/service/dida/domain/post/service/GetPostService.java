package com.service.dida.domain.post.service;

import com.service.dida.domain.post.repository.PostRepository;
import com.service.dida.domain.post.usecase.GetPostUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPostService implements GetPostUseCase {

    private final PostRepository postRepository;
}
