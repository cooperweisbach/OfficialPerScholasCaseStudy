package com.cooperweisbach.CommunityGarden.models;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE)
public class MessageDTO {

    public enum MessageType{
        CHAT, LEAVE, JOIN
    }

    String messageContent;

    MessageType type;

    Date messageSent = new Timestamp(new Date().getTime());

    String member;

    String messageThread;
}
