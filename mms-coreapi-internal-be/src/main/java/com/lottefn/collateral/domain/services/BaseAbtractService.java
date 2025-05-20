package com.lottefn.collateral.domain.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseAbtractService {

    @Autowired
    protected  ModelMapper modelMapper;
}
