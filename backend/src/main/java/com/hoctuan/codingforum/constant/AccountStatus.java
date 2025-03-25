package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountStatus {
    ACTIVE("active", "Active"),
    INACTIVE("Inactive", "Inactive"),
    BLOCK("block", "Block");

    private final String name;
    private final String displayName;
}
