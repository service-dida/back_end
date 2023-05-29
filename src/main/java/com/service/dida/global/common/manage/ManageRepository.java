package com.service.dida.global.common.manage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ManageRepository extends JpaRepository<Manage, Long> {

    @Query(value = "select m from Manage m where m.manageId = :manageId")
    Manage findByManageId(Long manageId);
}
