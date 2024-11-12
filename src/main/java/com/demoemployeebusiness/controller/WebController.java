package com.demoemployeebusiness.controller;

import com.demoemployeebusiness.exception.EmployeeNotFoundException;
import com.demoemployeebusiness.model.EmployeeInfoRequestDTO;
import com.demoemployeebusiness.model.EmployeeInfoResponseDTO;
import com.demoemployeebusiness.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

@RestController
public class WebController {
   private final RestTemplate restTemplate;
    static final Logger logger= LoggerFactory.getLogger(WebController.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    final EmployeeService employeeService;

    public WebController(RestTemplate restTemplate, EmployeeService employeeService) {
        this.restTemplate = restTemplate;
        this.employeeService = employeeService;
    }

    @GetMapping("/getEmployeeById/{empId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable String empId){
        logger.info("Begin getEmployeeById  EmpId  {}", empId);
        URI uri;
        try{
             uri = employeeService.getUri(empId);
            return (restTemplate.getForEntity(uri, String.class));
        }catch (Exception e){
            logger.error("Exception: "+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exception: "+e.getMessage());
        }
    }


    @PostMapping("/produceMessageAndSave")
    public ResponseEntity<?> produceMessage(@RequestBody String msg){
        logger.info("Begin produceMessage in EmployeeBusiness App");
        URI uri;
       try {
           uri= employeeService.getproduceMessageUri();
           return(restTemplate.postForEntity(uri, msg, String.class));
        }catch (Exception e){
           logger.error("Exception: "+e.getMessage());
           return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Exception: "+e.getMessage());
       }
    }
    @GetMapping("/getNoOfSkillsForEmployeeById/{empId}")
    public ResponseEntity<?> getNoOfSkillsForEmployeeById(@PathVariable String empId) throws Exception {
        logger.info("Begin getNoOfSkillsForEmployeeByEmpId  {}", empId);
        URI uri;
        try{
            uri= employeeService.getUri(empId);
            EmployeeInfoResponseDTO employeeInfoResponseDTO = getEmployeeInfoRequestDTO(uri);
            if(employeeInfoResponseDTO.getEmpDetails()!=null) {
                employeeService.getNoOfEmployeeSkills(employeeInfoResponseDTO);
            }else {
                throw new EmployeeNotFoundException("No employeeFound with empId ");
            }
            return ResponseEntity.status(HttpStatus.OK).body(employeeInfoResponseDTO);
        }catch (EmployeeNotFoundException e){
            logger.error("Exception: "+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/exportEmployeeToCsv/{empId}")
    public ResponseEntity<?> exportEmployeeToCsvFile(@PathVariable String empId) throws Exception {
        logger.info("Begin exportEmployeeToCsvFile {}", empId);
        URI uri;
        try{
            uri= employeeService.getUri(empId);
            EmployeeInfoRequestDTO employeeInfoDTO= objectMapper.readValue(uri.toURL(),
                    EmployeeInfoRequestDTO.class);
            logger.info("employeeInfoDTO "+employeeInfoDTO);
            if(employeeInfoDTO.getEmpDetails()!=null) {
                employeeService.writeToCSV(employeeInfoDTO);
            }else {
                throw new EmployeeNotFoundException("No employeeFound with empId ");
            }
            return ResponseEntity.status(HttpStatus.OK).body(employeeInfoDTO);
        }catch (EmployeeNotFoundException e){
            logger.error("Exception: "+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    private static EmployeeInfoResponseDTO getEmployeeInfoRequestDTO(URI uri) throws IOException {
        EmployeeInfoResponseDTO employeeInfoResponseDTO= objectMapper.readValue(uri.toURL(),
                EmployeeInfoResponseDTO.class);
        logger.info("employeeInfoResponseDTO "+employeeInfoResponseDTO);
        return employeeInfoResponseDTO;
    }

}
