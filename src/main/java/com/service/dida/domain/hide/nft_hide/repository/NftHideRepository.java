package com.service.dida.domain.hide.nft_hide.repository;

import com.service.dida.domain.hide.nft_hide.NftHide;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.Nft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NftHideRepository extends JpaRepository<NftHide, Long> {
    Optional<NftHide> findByMemberAndNft(Member member, Nft nft);

    Page<NftHide> findByMember(Member member, PageRequest pageRequest);
}
