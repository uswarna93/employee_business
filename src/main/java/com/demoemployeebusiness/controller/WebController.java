package com.demoemployeebusiness.controller;

import com.demoemployeebusiness.model.EmployeeInfoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
public class WebController {
    private final RestTemplate restTemplate=new RestTemplate();
    final Logger logger= LoggerFactory.getLogger(WebController.class);
    private final  ObjectMapper objectMapper = new ObjectMapper();

    @Value("${other.api.base-url}")
    private String baseUrl;

    @GetMapping("/getEmployeeById/{empId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable String empId){
        logger.info("Begin getEmployeeById  EmpId  {}", empId);
        final URI uri;
        try{
             uri= URI.create(baseUrl+"/getEmployeeDetailsByEmpId/"+empId);
           return (restTemplate.getForEntity(uri, String.class));
        }catch (Exception e){
            logger.error("Exception: "+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exception: "+e.getMessage());
        }
    }
    @PostMapping("/produceMessageAndSave")
    public ResponseEntity<?> produceMessage(@RequestBody String msg){
        logger.info("Begin produceMessage in EmployeeBusiness App");
        final URI uri;
       try {
           uri= URI.create(baseUrl+"/produceMessage");
           return(restTemplate.postForEntity(uri, msg, String.class));
        }catch (Exception e){
           logger.error("Exception: "+e.getMessage());
           return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Exception: "+e.getMessage());
       }
    }
    @GetMapping("/getNoOfSkillsForEmployeeById/{empId}")
    public ResponseEntity<?> getSkillsByEmployeeById(@PathVariable String empId){
        logger.info("Begin getEmployeeById  EmpId  {}", empId);
        final URI uri;
        try{
            uri= URI.create(baseUrl+"/getEmployeeDetailsByEmpId/"+empId);
            String result = String.valueOf(restTemplate.getForEntity(uri, String.class));
            //objectMapper.writeValue(result);
            EmployeeInfoDTO  employeeInfoDTO= objectMapper.readValue(result, EmployeeInfoDTO.class);
            logger.info("response "+employeeInfoDTO);
            int size = employeeInfoDTO.getSkillSet().size();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(result+"No.of skills for employee "+empId +"is "+size);
        }catch (Exception e){
            logger.error("Exception: "+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exception: "+e.getMessage());
        }
    }
}
