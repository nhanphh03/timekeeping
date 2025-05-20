package com.lottefn.collateral.domain.services;

import com.lottefn.collateral.app.requests.DTO;
import com.lottefn.collateral.app.requests.FilterDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface BaseService<T, S> {

    Page<T> findAll(FilterDTO dto, Pageable pageable);

    T findById(HttpServletRequest request, S id);

    @Transactional(rollbackOn = Exception.class)
    T create(HttpServletRequest request, DTO dto);

    @Transactional(rollbackOn = Exception.class)
    T update(HttpServletRequest request, S id, DTO dto);

    T update(HttpServletRequest request, Long id, DTO dto);

    @Transactional(rollbackOn = Exception.class)
    boolean delete(HttpServletRequest request, S id);
}

