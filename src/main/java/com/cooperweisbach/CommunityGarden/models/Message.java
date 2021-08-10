package com.cooperweisbach.CommunityGarden.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name="message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MessageId")
    int messageId;

    @NonNull
    @NotBlank
    @Column(name="MessageContent", columnDefinition = "TEXT NOT NULL")
    String messageContent;

    @NonNull
    @Temporal(value=TemporalType.DATE)
    Date messageSent;

    ///////////////////MAPPINGS///////////////////////////

    @ManyToOne
    Member member;

}
