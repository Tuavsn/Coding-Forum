package com.hoctuan.codingforum.common;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilterRequest {
    private List<FilterDTO> rootFilter;
    private List<FilterDTO> joinFilter;
    private PageDTO pageable;
}
