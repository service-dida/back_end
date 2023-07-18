package com.service.dida.domain.post.usecase;

import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.post.Post;
import com.service.dida.domain.post.dto.PostResponseDto;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GetPostUseCase {
    PostResponseDto.GetPostResponseDto makeGetPostResForm(Member member, Post post, boolean needComment);
    PageResponseDto<List<PostResponseDto.GetPostResponseDto>> makePostListForm(Member member, Page<Post> posts, boolean needComment);
    PageResponseDto<List<PostResponseDto.GetPostResponseDto>> getAllPosts(Member member, PageRequestDto pageRequestDto);
    PageResponseDto<List<PostResponseDto.GetPostResponseDto>> getPostsByNftId(Member member, Long nftId, PageRequestDto pageRequestDto);
    PostResponseDto.GetPostResponseDto getPost(Member member, Long postId);
    String checkIsMe(Member member, Member owner);
    PageResponseDto<List<PostResponseDto.GetHotPosts>> getHotPosts(Member member, PageRequestDto pageRequestDto);
}
