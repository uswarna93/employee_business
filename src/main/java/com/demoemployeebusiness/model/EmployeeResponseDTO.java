package com.demoemployeebusiness.model;
import lombok.*;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @EqualsAndHashCode
    @Data
    public class EmployeeResponseDTO {
        private String empId;
        private String firstName;
        private  String lastName;
        private  String department;
        private String NoOfSkills;

    }

