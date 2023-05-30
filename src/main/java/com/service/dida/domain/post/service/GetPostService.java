package com.service.dida.domain.post.service;

import com.service.dida.domain.comment.dto.CommentResponseDto;
import com.service.dida.domain.comment.service.GetCommentService;
import com.service.dida.domain.member.dto.MemberResponseDto;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.dto.NftResponseDto;
import com.service.dida.domain.post.Post;
import com.service.dida.domain.post.dto.PostResponseDto;
import com.service.dida.domain.post.dto.PostResponseDto.GetPostResponseDto;
import com.service.dida.domain.post.repository.PostRepository;
import com.service.dida.domain.post.usecase.GetPostUseCase;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.PostErrorCode;
import com.service.dida.global.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetPostService implements GetPostUseCase {

    private final PostRepository postRepository;
    private final UtilService utilService;
    private final GetCommentService getCommentService;

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
    public String checkIsMe(Member member, Member owner) {
        String type;
        if (member == null) {  // 로그인하지 않았다면
            type = "NEED LOGIN";
        } else if (member.equals(owner)) {  // 내 게시물이라면
            type = "MINE";
        } else {  // 내 게시물이 아니라면
            type = "NOT MINE";
        }
        return type;
    }

    /**
     * 마켓에 등록되었다면 price 를 반환하는 함수
     */
    public String getPrice(Nft nft) {
        String price = "NOT SALE";
        if (nft.isMarketed()) {
            price = utilService.doubleToString(nft.getMarkets().get(nft.getMarkets().size() - 1).getPrice());
        }
        return price;
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
                getPrice(post.getNft()));

        List<CommentResponseDto.GetCommentResponseDto> comments = new ArrayList<>();
        if (needComment) {
            comments = getCommentService.getPreviewComments(post.getPostId());
        }

        return GetPostResponseDto.builder()
                .postInfo(postInfo)
                .memberInfo(memberInfo)
                .nftInfo(nftInfo)
                // 로그인 상태 및 나의 게시글인지를 나타내는 정보
                .type(checkIsMe(member, post.getMember()))
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
        // Page<Post>의 content 는 페이지 요청대로 가져온 List<Post>를 나타낸다.
        for (Post p : posts.getContent()) {
            // 숨김 로직 추가 필요
            res.add(makeGetPostResForm(member, p, needComment));
        }
        return new PageResponseDto<>(
                posts.getNumber(), posts.getSize(), posts.hasNext(), res);
    }

    /**
     * 최신 게시글(삭제 제외)을 페이지 요청에 맞게 가져오는 함수
     * List<GetPostResponseDto> 를 PageResponseDto 로 감싸서 반환
     */
    @Override
    public PageResponseDto<List<GetPostResponseDto>> getAllPosts(Member member, PageRequestDto pageRequestDto) {
        Page<Post> posts = postRepository.findAllWithDeleted(pageReq(pageRequestDto));
        return makePostListForm(member, posts, true);
    }

    /**
     * nftId를 받아 해당 NFT에 달린 게시글(삭제 제외)을 페이지 요청에 맞게 가져오는 함수
     * List<GetPostResponseDto> 를 PageResponseDto 로 감싸서 반환
     */
    @Override
    public PageResponseDto<List<GetPostResponseDto>> getPostsByNftId(Member member, Long nftId, PageRequestDto pageRequestDto) {
        Page<Post> posts = postRepository.findByNftIdWithDeleted(nftId, pageReq(pageRequestDto));
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
}
