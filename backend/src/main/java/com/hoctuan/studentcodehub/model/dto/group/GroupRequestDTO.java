package com.hoctuan.studentcodehub.model.dto.group;

import com.hoctuan.studentcodehub.common.BaseRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class GroupRequestDTO extends BaseRequestDTO {
    private String title;

    private String description;
}
