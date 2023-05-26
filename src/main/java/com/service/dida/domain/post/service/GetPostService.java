package com.service.dida.domain.post.service;

import com.service.dida.domain.comment.service.GetCommentService;
import com.service.dida.domain.member.dto.MemberResponseDto;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.domain.nft.Nft;
import com.service.dida.domain.nft.dto.NftResponseDto;
import com.service.dida.domain.post.Post;
import com.service.dida.domain.post.dto.PostResponseDto;
import com.service.dida.domain.post.dto.PostResponseDto.GetPostResponseDto;
import com.service.dida.domain.post.dto.PostResponseDto.GetPostsResponseDto;
import com.service.dida.domain.post.repository.PostRepository;
import com.service.dida.domain.post.usecase.GetPostUseCase;
import com.service.dida.global.common.dto.PageRequestDto;
import com.service.dida.global.common.dto.PageResponseDto;
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
    private final MemberRepository memberRepository;
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
    public String checkIsMe(Long memberId, Long ownerId) {
        String type;
        if (memberId == 0L) {  // 로그인하지 않았다면
            type = "NEED LOGIN";
        } else if (memberId.equals(ownerId)) {  // 내 게시물이라면
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
     * PostInfo, MemberInfo, NftInfo 를 가지는 GetPostResponseDto 형태를 만들어주는 함수
     */
    @Override
    public GetPostResponseDto makeGetPostResForm(Long memberId, Post post) {
        PostResponseDto.PostInfo postInfo = new PostResponseDto.PostInfo(
                post.getPostId(), post.getTitle(), post.getTitle());

        MemberResponseDto.MemberInfo memberInfo = new MemberResponseDto.MemberInfo(
                memberId, post.getMember().getNickname(), post.getMember().getProfileUrl());

        NftResponseDto.NftInfo nftInfo = new NftResponseDto.NftInfo(
                post.getNft().getNftId(), post.getNft().getTitle(), post.getNft().getImgUrl(),
                getPrice(post.getNft()), post.getNft().getMember().getProfileUrl());

        return GetPostResponseDto.builder()
                .postInfo(postInfo)
                .memberInfo(memberInfo)
                .nftInfo(nftInfo)
                // 로그인 상태 및 나의 게시글인지를 나타내는 정보
                .type(checkIsMe(memberId, post.getMember().getMemberId()))
                .build();
    }

    /**
     *
     */
    public PageResponseDto<List<GetPostsResponseDto>> makeGetPostsResForm(Long memberId, Page<Post> posts) {
        List<GetPostsResponseDto> res = new ArrayList<>();

        // Page<Post>의 content 는 페이지 요청대로 가져온 List<Post>를 나타낸다.
        for (Post p : posts.getContent()) {

            res.add(new GetPostsResponseDto(
                    makeGetPostResForm(memberId, p), getCommentService.getPreviewComments(p.getPostId())));
        }

        return new PageResponseDto<>(
                posts.getNumber(), posts.getSize(), posts.hasNext(), res);
    }

    /**
     * GetPostResponseDto, commentsList 를 가지는 GetPostsResponseDto 의  List 를
     * PageResponseDto 로 감싸서 반환 하는 함수
     */
    @Override
    public PageResponseDto<List<GetPostsResponseDto>> getAllPosts(Long memberId, PageRequestDto pageRequestDto) {
        // pageRequest 대로 Page<post>를 받아온다.
        Page<Post> posts = postRepository.findAllWithDeleted(pageReq(pageRequestDto));
        return makeGetPostsResForm(memberId, posts);
    }

    @Override
    public PageResponseDto<List<GetPostsResponseDto>> getPostsByNftId(Long memberId, Long nftId, PageRequestDto pageRequestDto) {
        Page<Post> posts = postRepository.findByNftIdWithDeleted(nftId, pageReq(pageRequestDto));
        return makeGetPostsResForm(memberId, posts);
    }
}
