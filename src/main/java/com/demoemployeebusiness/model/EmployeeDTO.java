package com.demoemployeebusiness.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Data
public class EmployeeDTO {

    private String empId;
    private String firstName;
    private  String lastName;

}
