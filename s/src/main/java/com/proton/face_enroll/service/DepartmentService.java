package com.proton.face_enroll.service;

import com.proton.face_enroll.model.Department;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();
}
