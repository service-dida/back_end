package com.service.dida.domain.hide.repository;

import com.service.dida.domain.hide.Hide;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HideRepository extends JpaRepository<Hide, Long> {
    Optional<Hide> findByMemberAndNft(Member member, Nft nft);

    Page<Hide> findByMember(Member member, PageRequest pageRequest);
}
