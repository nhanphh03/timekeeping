package com.proton.face_enroll.service.impl;

import com.proton.face_enroll.dao.DepartmentRepository;
import com.proton.face_enroll.model.Department;
import com.proton.face_enroll.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentRepository getDepartmentRepository(DepartmentRepository departmentRepository) {
        return this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }
}
