package com.service.dida.domain.member.usecase;

import com.service.dida.domain.member.dto.MemberRequestDto;
import com.service.dida.domain.member.dto.MemberResponseDto;
import com.service.dida.global.config.security.oauth.helper.SocialLoginType;
import java.io.IOException;

public interface RegisterMemberUseCase {

    MemberResponseDto.TokenInfo socialLogin(SocialLoginType socialLoginType,
        MemberRequestDto.SocialLoginToken socialLoginToken) throws IOException;

    MemberResponseDto.TokenInfo registerMember(MemberRequestDto.RegisterMember registerMember);

    MemberResponseDto.CheckNickname checkNickname(MemberRequestDto.CheckNickname checkNickname);
}
