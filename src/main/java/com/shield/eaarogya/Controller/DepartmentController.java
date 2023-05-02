package com.shield.eaarogya.Controller;

import com.shield.eaarogya.Entity.Department;
import com.shield.eaarogya.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // ------------------------------ Save a Department --------------------------------------
    @PostMapping("/saveDepartment")
    public Department saveDepartment(@RequestBody Department department) {
        return departmentService.saveDepartment(department);
    }

    // ---------------------------------- Get List of all Departments -------------------------------------
    @GetMapping("/getDepartment")
    public List<Department> fetchDepartmentList() {
        return departmentService.fetchDepartmentList();
    }

    // -------------------------------- Get Department By Id ---------------------------------------
    @GetMapping("getDepartment/{department_id}")
    public Department getDepartmentById(@PathVariable String department_id) {
        return departmentService.getDepartmentById(Integer.parseInt(department_id));
    }

    // --------------------------------- Get Department BY Name ----------------------------------------
    @GetMapping("getDepartmentByName/{department_Name}")
    public Department getDepartmentByName(@PathVariable String department_Name) {
        return departmentService.getDepartmentByName(department_Name);
    }
}
