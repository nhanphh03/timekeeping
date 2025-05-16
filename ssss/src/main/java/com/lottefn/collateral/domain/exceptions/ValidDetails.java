package com.lottefn.collateral.domain.exceptions;


import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ValidDetails {
    private Integer status;
    private Map<String, String> message;
    private String error;
    private String path;
    private Date timestamp;
}
