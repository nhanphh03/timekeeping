package com.lottefn.collateral.app.requests.user;

import com.lottefn.collateral.app.requests.FilterDTO;
import com.lottefn.collateral.domain.entities.User;
import lombok.Data;

@Data
public class UserFilterDTO implements FilterDTO<User> {
    private String username;
}
