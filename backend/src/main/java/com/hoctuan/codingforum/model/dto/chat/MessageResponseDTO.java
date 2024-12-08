package com.hoctuan.codingforum.model.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

import com.hoctuan.codingforum.common.BaseResponseDTO;
import com.hoctuan.codingforum.constant.MessageStatus;
import com.hoctuan.codingforum.model.entity.account.User;
import com.hoctuan.codingforum.model.entity.chat.MessageImage;

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
