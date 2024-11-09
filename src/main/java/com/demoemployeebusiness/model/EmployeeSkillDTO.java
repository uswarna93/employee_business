package com.demoemployeebusiness.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Data
public class EmployeeSkillDTO {
    private String skillId;

    private String skillName;

    private String skillLevel;
}
