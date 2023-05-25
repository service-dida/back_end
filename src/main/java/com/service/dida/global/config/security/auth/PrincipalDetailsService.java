package com.service.dida.global.config.security.auth;

import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.UserErrorCode;
import com.service.dida.domain.user.entity.Member;
import com.service.dida.domain.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public PrincipalDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member memberEntity = memberRepository.findByMemberId(Long.parseLong(username))
            .orElseThrow(() -> new BaseException(UserErrorCode.EMPTY_MEMBER));
        return new PrincipalDetails(memberEntity);
    }
}
