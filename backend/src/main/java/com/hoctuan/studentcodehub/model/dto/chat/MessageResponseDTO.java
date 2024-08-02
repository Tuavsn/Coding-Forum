package com.hoctuan.studentcodehub.model.dto.chat;

import com.hoctuan.studentcodehub.common.BaseResponseDTO;
import com.hoctuan.studentcodehub.constant.MessageStatus;
import com.hoctuan.studentcodehub.model.entity.account.User;
import com.hoctuan.studentcodehub.model.entity.chat.MessageImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class MessageResponseDTO extends BaseResponseDTO {
    private User source;

    private User target;

    private String content;

    private MessageStatus status;

    private Set<MessageImage> images = new HashSet<>();
}
