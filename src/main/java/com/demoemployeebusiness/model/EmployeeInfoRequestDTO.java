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
public class EmployeeInfoRequestDTO {
    private EmployeeDTO empDetails;
    private List<EmployeeSkillDTO> skillSet;

}
