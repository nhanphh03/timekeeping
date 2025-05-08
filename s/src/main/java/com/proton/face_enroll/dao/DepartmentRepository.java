package com.proton.face_enroll.dao;

import com.proton.face_enroll.model.Department;

import java.util.List;

public interface DepartmentRepository {
    List<Department> findAll();
}
