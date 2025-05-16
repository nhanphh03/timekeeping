package com.lottefn.collateral.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lottefn.collateral.domain.annotations.enum_convert.MenuConverter;
import com.lottefn.collateral.domain.annotations.enum_convert.PermissionMethodConverter;
import com.lottefn.collateral.domain.enums.Menu;
import com.lottefn.collateral.domain.enums.PermissionMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "collateral_permission")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERMISSION_SEQ")
    @SequenceGenerator(name = "PERMISSION_SEQ", sequenceName = "PERMISSION_SEQ", allocationSize = 1)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "menu")
    @Convert(converter = MenuConverter.class)
    private Menu menu;


    @Convert(converter = PermissionMethodConverter.class)
    @Column(name = "method")
    private PermissionMethod method;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Role> roles = new ArrayList<>();


}
