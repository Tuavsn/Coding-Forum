package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountRole {
    SYS_ADMIN(0, "system_admin", "System Admin"),
    SYS_MANAGER(1, "system_manager", "System Manager"),
    GROUP_ADMIN(2, "group_admin", "Group Admin"),
    GROUP_MANAGER(3, "group_manager", "Group Manager"),
    GROUP_MEMBER(4, "group_member", "Group Member"),
    USER(5, "user", "User");

    private final int code;
    private final String name;
    private final String displayName;
}
