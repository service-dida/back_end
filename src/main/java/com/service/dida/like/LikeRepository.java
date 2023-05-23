package com.service.dida.like;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import com.service.dida.nft.Nft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query(value = "SELECT l.nft FROM Like l WHERE l.status = true GROUP BY l.nft ORDER BY COUNT(l.nft) DESC")
    Optional<List<Nft>> getHotItems(Pageable limit);

    @Query(value = "SELECT COUNT(l) FROM Like l WHERE l.nft = :nft AND l.status = true")
    Optional<Long> getLikeCountsByNftId(Nft nft);
}
