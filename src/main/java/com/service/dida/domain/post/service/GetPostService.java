package com.service.dida.domain.post.service;

import com.service.dida.domain.comment.dto.CommentResponseDto;
import com.service.dida.domain.comment.repository.CommentRepository;
import com.service.dida.domain.comment.service.GetCommentService;
import com.service.dida.domain.member.dto.MemberResponseDto;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.dto.NftResponseDto;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.domain.post.Post;
import com.service.dida.domain.post.dto.PostResponseDto;
import com.service.dida.domain.post.dto.PostResponseDto.GetPostResponseDto;
import com.service.dida.domain.post.repository.PostRepository;
import com.service.dida.domain.post.usecase.GetPostUseCase;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.NftErrorCode;
import com.service.dida.global.config.exception.errorCode.PostErrorCode;
import com.service.dida.global.util.usecase.UtilUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GetPostService implements GetPostUseCase {

    private final PostRepository postRepository;
    private final NftRepository nftRepository;
    private final GetCommentService getCommentService;
    private final CommentRepository commentRepository;
    private final UtilUseCase utilUseCase;


    /**
     * 게시글 조회에서 공통으로 사용 될 PageRequest 를 정의하는 함수
     */
    public PageRequest pageReq(PageRequestDto pageRequestDto) {
        // pageRequest 는 원하는 page, 한 page 당 size, 최신 순서 정렬 이라는 요청을 담고 있다.
        return PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()
                , Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    /**
     * 나의 게시글인지를 나타내는 type 을 반환하는 함수
     */
    @Override
    public String checkIsMe(Member member, Member owner) {
        String type;
        if (member == null) {  // 로그인하지 않았다면
            type = "NEED LOGIN";
        } else if (Objects.equals(member.getMemberId(), owner.getMemberId())) {  // 내 게시물이라면
            type = "MINE";
        } else {  // 내 게시물이 아니라면
            type = "NOT MINE";
        }
        return type;
    }

    /**
     * 1개의 Post 를 GetPostResponseDto 형태로 만들어주는 함수
     * GetPostResponseDto 는 PostInfo, MemberInfo, NftInfo, comments, type 을 가진다.
     */
    @Override
    public GetPostResponseDto makeGetPostResForm(Member member, Post post, boolean needComment) {
        PostResponseDto.PostInfo postInfo = new PostResponseDto.PostInfo(
                post.getPostId(), post.getTitle(), post.getContent());

        MemberResponseDto.MemberInfo memberInfo = new MemberResponseDto.MemberInfo(
                post.getMember().getMemberId(), post.getMember().getNickname(), post.getMember().getProfileUrl());

        NftResponseDto.NftInfo nftInfo = new NftResponseDto.NftInfo(
                post.getNft().getNftId(), post.getNft().getTitle(), post.getNft().getImgUrl(),
                post.getNft().getPrice());

        List<CommentResponseDto.GetCommentResponseDto> comments = new ArrayList<>();
        if (needComment) {
            comments = getCommentService.getPreviewComments(member, post.getPostId());
        }

        return GetPostResponseDto.builder()
                .postInfo(postInfo)
                .memberInfo(memberInfo)
                .nftInfo(nftInfo)
                .type(checkIsMe(member, post.getMember()))
                .createdAt(utilUseCase.localDateTimeToDateFormatting(post.getCreatedAt()))
                .comments(comments)
                .build();
    }

    /**
     * makeGetPostResForm 을 호출하여 List<GetPostResponseDto> 을 만드는 함수
     * PageResponseDto 로 감싸서 반환
     */
    @Override
    public PageResponseDto<List<GetPostResponseDto>> makePostListForm(Member member, Page<Post> posts, boolean needComment) {
        List<GetPostResponseDto> res = new ArrayList<>();
        posts.forEach(p -> res.add(makeGetPostResForm(member, p, needComment)));
        return new PageResponseDto<>(
                posts.getNumber(), posts.getSize(), posts.hasNext(), res);
    }

    public PageResponseDto<List<PostResponseDto.GetHotPosts>> makeGetHotPostsListForm(Page<Post> posts) {
        List<PostResponseDto.GetHotPosts> res = new ArrayList<>();

        posts.forEach(p -> res.add(new PostResponseDto.GetHotPosts(
                p.getPostId(), p.getTitle(),
                commentRepository.countByPostAndDeletedFalse(p),
                p.getNft().getNftId(), p.getNft().getImgUrl())));

        return new PageResponseDto<>(posts.getNumber(), posts.getSize(), posts.hasNext(), res);
    }

    /**
     * 최신 게시글(삭제 제외)을 페이지 요청에 맞게 가져오는 함수
     * List<GetPostResponseDto> 를 PageResponseDto 로 감싸서 반환
     */
    @Override
    public PageResponseDto<List<GetPostResponseDto>> getAllPosts(Member member, PageRequestDto pageRequestDto) {
        Page<Post> posts;
        if (member != null) {
            posts = postRepository.findAllWithDeletedWithoutHide(member, pageReq(pageRequestDto));
        } else {
            posts = postRepository.findAllWithDeleted(pageReq(pageRequestDto));
        }
        return makePostListForm(member, posts, true);
    }

    /**
     * nftId를 받아 해당 NFT에 달린 게시글(삭제 제외)을 페이지 요청에 맞게 가져오는 함수
     * List<GetPostResponseDto> 를 PageResponseDto 로 감싸서 반환
     */
    @Override
    public PageResponseDto<List<GetPostResponseDto>> getPostsByNftId(Member member, Long nftId, PageRequestDto pageRequestDto) {
        if(nftRepository.findByNftIdWithDeleted(nftId).isEmpty()) {
            throw new BaseException(NftErrorCode.EMPTY_NFT);
        }
        Page<Post> posts;
        if (member != null) {
            posts = postRepository.findByNftIdWithDeletedWithoutHide(member, nftId, pageReq(pageRequestDto));
        } else {
            posts = postRepository.findByNftIdWithDeleted(nftId, pageReq(pageRequestDto));
        }
        return makePostListForm(member, posts, true);
    }

    /**
     * 게시글을 상세 조회하는 함수
     */
    @Override
    public GetPostResponseDto getPost(Member member, Long postId) {
        Post post = postRepository.findByPostIdWithDeleted(postId)
                .orElseThrow(() -> new BaseException(PostErrorCode.EMPTY_POST));
        return makeGetPostResForm(member, post, false);
    }

    @Override
    public PageResponseDto<List<PostResponseDto.GetHotPosts>> getHotPosts(Member member, PageRequestDto pageRequestDto) {
        Page<Post> posts;
        if (member != null) {
            posts = commentRepository.findPostsByCommentCount(member,
                    LocalDateTime.now().minusDays(7),
                    PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()));
        } else {
            posts = commentRepository.findPostsByCommentCountWithoutHide(
                    LocalDateTime.now().minusDays(7),
                    PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getPageSize()));
        }
        return makeGetHotPostsListForm(posts);
    }
}
