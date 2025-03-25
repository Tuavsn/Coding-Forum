package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountRole {
    SYS_ADMIN("system_admin", "System Admin"),
    SYS_MANAGER("system_manager", "System Manager"),
    GROUP_ADMIN("group_admin", "Group Admin"),
    GROUP_MANAGER("group_manager", "Group Manager"),
    GROUP_MEMBER("group_member", "Group Member"),
    USER("user", "User");

    private final String value;      // Lưu giá trị dưới dạng String trong DB
    private final String displayName;
}
