package com.lottefn.collateral.app.controllers;

import com.lottefn.collateral.app.requests.DTO;
import com.lottefn.collateral.app.requests.FilterDTO;
import com.lottefn.collateral.app.responses.PageResponse;
import com.lottefn.collateral.domain.services.BaseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
public abstract  class BaseController<O, ID, P1, FD extends FilterDTO<O>>{
    protected final Class<P1> responseClass;
    protected final Class<FD> filterDto;

    @Autowired
    protected ModelMapper modelMapper;

    protected final BaseService<O, ID> service;

    protected BaseController(Class<P1> responseClass, Class<FD> fpClass, BaseService<O, ID> service) {
        this.responseClass = responseClass;
        this.filterDto = fpClass;
        this.service = service;
    }

    PageResponse<P1> findAll(FilterDTO<O> filterDTO, Pageable pageable) {
        Page<O> page = service.findAll(filterDTO, pageable);
        PageResponse<P1> pageResponse = new PageResponse<>();
        pageResponse.setData(page.getContent().stream().map(ele -> modelMapper.map(ele, responseClass))
                .collect(Collectors.toList()));
        pageResponse.setMetadata(new PageResponse.Metadata(page));
        return pageResponse;
    }

    P1 getById(HttpServletRequest request, @PathVariable ID id) {
        O ob = service.findById(request, id);
        return modelMapper.map(ob, responseClass);
    }

    P1 create(HttpServletRequest request, @Valid @RequestBody DTO dto) {
        O ob = service.create(request, dto);
        return modelMapper.map(ob, responseClass);
    }

    P1 update(HttpServletRequest request, @PathVariable ID id, @Valid @RequestBody DTO dto) {
        O ob = service.update(request, id, dto);
        return modelMapper.map(ob, responseClass);
    }

    boolean delete(HttpServletRequest request, @PathVariable ID id) {
        return service.delete(request, id);
    }
}
