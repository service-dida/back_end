package com.service.dida.domain.member.service;

import com.service.dida.domain.follow.repository.FollowRepository;
import com.service.dida.domain.follow.usecase.GetFollowUseCase;
import com.service.dida.domain.member.dto.MemberResponseDto.MemberDetailInfo;
import com.service.dida.domain.member.dto.MemberResponseDto.MemberInfo;
import com.service.dida.domain.member.dto.MemberResponseDto.OtherMemberDetailInfo;
import com.service.dida.domain.member.dto.MemberResponseDto.WalletExists;
import com.service.dida.domain.member.dto.SendAuthEmailDto;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.member.repository.MemberRepository;
import com.service.dida.domain.member.usecase.GetMemberUseCase;
import com.service.dida.domain.nft.repository.NftRepository;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.MemberErrorCode;
import com.service.dida.global.util.mail.MailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMemberService implements GetMemberUseCase {

    private final MailUseCase mailUseCase;
    private final GetFollowUseCase getFollowUseCase;
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final NftRepository nftRepository;

    @Override
    public SendAuthEmailDto sendAuthMail(Member member) {
        return new SendAuthEmailDto(mailUseCase.sendAuthMail(member.getEmail()));
    }

    @Override
    public WalletExists isExistWallet(Member member) {
        if (member.getWallet() == null) {
            return new WalletExists(false);
        } else {
            return new WalletExists(true);
        }
    }

    @Override
    public MemberDetailInfo getMemberDetailInfo(Member member) {
        return new MemberDetailInfo(
            new MemberInfo(member.getMemberId(), member.getNickname(), member.getProfileUrl()),
            member.getDescription(), nftRepository.countByMemberAndDeleted(member, false),
            followRepository.countByFollowerMemberAndStatus(member, true),
            followRepository.countByFollowingMemberAndStatus(member, true));
    }

    @Override
    public OtherMemberDetailInfo getOtherMemberDetailInfo(Member member, Long memberId) {
        Member other = memberRepository.findByMemberIdWithDeleted(memberId)
            .orElseThrow(() -> new BaseException(MemberErrorCode.EMPTY_MEMBER));
        return new OtherMemberDetailInfo(
            new MemberDetailInfo(
                new MemberInfo(other.getMemberId(), other.getNickname(), other.getProfileUrl()),
                other.getDescription(), nftRepository.countByMemberAndDeleted(other, false),
                followRepository.countByFollowerMemberAndStatus(other, true),
                followRepository.countByFollowingMemberAndStatus(other, true)
            ), getFollowUseCase.checkIsFollowed(member, other)
        );
    }
}
