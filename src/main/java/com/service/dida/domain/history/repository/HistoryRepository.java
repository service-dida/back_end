package com.service.dida.domain.history.repository;

import com.service.dida.domain.history.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {


    Page<History> findAllByNftId(Long nftId, PageRequest pageRequest);
}
