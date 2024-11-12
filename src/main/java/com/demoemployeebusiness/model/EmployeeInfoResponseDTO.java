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
public class EmployeeInfoResponseDTO {
    private EmployeeResponseDTO empDetails;
    private List<EmployeeSkillDTO> skillSet;
}
