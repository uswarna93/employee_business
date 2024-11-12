package com.demoemployeebusiness.service;

import com.demoemployeebusiness.model.EmployeeInfoRequestDTO;
import com.demoemployeebusiness.model.EmployeeInfoResponseDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;

@Service
public interface EmployeeService {
     URI getUri(String empId);
     URI getproduceMessageUri();
    void getNoOfEmployeeSkills(EmployeeInfoResponseDTO employeeInfoResponseDTO);
     void writeToCSV(EmployeeInfoRequestDTO employeeInfoDTOS) throws IOException;

    void getEmployeeSkills(EmployeeInfoRequestDTO employeeInfoDTO);
}

