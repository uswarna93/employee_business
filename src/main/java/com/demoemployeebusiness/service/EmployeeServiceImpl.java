package com.demoemployeebusiness.service;

import com.demoemployeebusiness.model.EmployeeInfoRequestDTO;
import com.demoemployeebusiness.model.EmployeeInfoResponseDTO;
import com.demoemployeebusiness.model.EmployeeResponseDTO;
import com.demoemployeebusiness.model.EmployeeSkillDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
   final Logger logger= LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Value("${other.api.base-url}")
    private String baseUrl;

    @Value("${get.api.base-url}")
    private String getByEmpIdUrl;

    @Value("${produce.message.api.base-url}")
    private String produceMessageUrl;

     public URI getUri(String empId) {
         return URI.create(baseUrl+getByEmpIdUrl+ empId);
    }

    @Override
    public URI getproduceMessageUri() {
        return URI.create(baseUrl+produceMessageUrl);
    }

    public void getNoOfEmployeeSkills(EmployeeInfoResponseDTO employeeInfoResponseDTO) {
        logger.info("Begin setNoOfEmployeeSkills()");
        int size=0;
        if(employeeInfoResponseDTO.getSkillSet()!=null) {
            size = employeeInfoResponseDTO.getSkillSet().size();
        }
        EmployeeResponseDTO employeeResponseDTO=employeeInfoResponseDTO.getEmpDetails();
        employeeResponseDTO.setNoOfSkills(String.valueOf(size));
    }
    public void writeToCSV(EmployeeInfoRequestDTO employeeInfoDTO) throws IOException {
        logger.info("Begin writeToCSV()");
        final String CSV_SEPARATOR = ",";
        ArrayList<EmployeeInfoRequestDTO> employeeInfoDTOList = new ArrayList<>();
        employeeInfoDTOList.add(employeeInfoDTO);
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter
                    (new FileOutputStream("EmployeeInfo.csv"),
                            StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            sb.append("EmpId, FirstName, LastName, Department \n");
            bw.flush();
            for (EmployeeInfoRequestDTO employeeInfo : employeeInfoDTOList)
            {
                writeEmployees(employeeInfo, sb);
                List<EmployeeSkillDTO> employeeSkillList=employeeInfo.getSkillSet();
                if (!employeeSkillList.isEmpty()){
                    writingEmployeeSkills(sb, employeeSkillList);
                }
                bw.write(sb.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new UnsupportedEncodingException();
        }catch (FileNotFoundException notFoundException){
            throw new FileNotFoundException();
        } catch (IOException ioException){
            throw new IOException();
        }
    }

    @Override
    public void getEmployeeSkills(EmployeeInfoRequestDTO employeeInfoDTO) {

    }

    private void writeEmployees(EmployeeInfoRequestDTO employeeInfo, StringBuilder sb) {
        logger.info("Begin writing employees to csv file");
        sb.append(Integer.parseInt(employeeInfo.getEmpDetails().getEmpId())<=0 ? ""
                : employeeInfo.getEmpDetails().getEmpId());
        sb.append(",");
        sb.append(employeeInfo.getEmpDetails().getFirstName().trim().isEmpty() ? ""
                : employeeInfo.getEmpDetails().getFirstName());
        sb.append(",");
        sb.append(employeeInfo.getEmpDetails().getLastName().trim().isEmpty()? ""
                : employeeInfo.getEmpDetails().getLastName());
        sb.append(",");
        sb.append(employeeInfo.getEmpDetails().getDepartment().trim().isEmpty()? ""
                : employeeInfo.getEmpDetails().getDepartment());
        sb.append("\n");
    }

    private void writingEmployeeSkills(StringBuilder sb, List<EmployeeSkillDTO> employeeSkillList) {
        sb.append("SkillId,SkillName,SkillLevel \n");
        logger.info("Begin writing employeesSkills to csv file");

        for (EmployeeSkillDTO employeeSkill: employeeSkillList){
            sb.append(Integer.parseInt(employeeSkill.getSkillId())<=0 ? ""
                    : employeeSkill.getSkillId());
            sb.append(",");
            sb.append(employeeSkill.getSkillName().trim().isEmpty() ? ""
                    : employeeSkill.getSkillName());
            sb.append(",");
            sb.append(employeeSkill.getSkillLevel().trim().isEmpty() ? ""
                    : employeeSkill.getSkillLevel());
            sb.append("\n");
        }
    }
}
