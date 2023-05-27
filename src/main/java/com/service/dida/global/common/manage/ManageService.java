package com.service.dida.global.common.manage;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ManageService implements ManageUseCase {

    private final ManageRepository manageRepository;

    @Override
    public Long getNftIdAndPlusOne() {
        Manage manage = manageRepository.findByManageId(2L);
        Long id = manage.getObj();
        manage.upCnt();
        return id;
    }
}
