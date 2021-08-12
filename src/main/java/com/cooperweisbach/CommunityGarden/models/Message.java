package com.cooperweisbach.CommunityGarden.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Date;

@Entity
//@Table(name="message")
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
    @Column(name="message_id")
    int messageId;

    @NonNull
    @NotBlank
    @Column(name="message_content", columnDefinition = "TEXT NOT NULL")
    String messageContent;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name="message_sent")
    Date messageSent = new Timestamp(new Date().getTime());

    ///////////////////MAPPINGS///////////////////////////

    //Mapping for member to messages.
    // In this instance, one member has the ability to belong to many messages
    //Because we want this to get mapped to message table and not to the member table, this was made the owning side
    //Additionally, because it is mandatory for every message to have a member associated with it, the fetch type is set to eager
    //Eager is the default, but it was made explicit to emphasize this.
    //Uni-Directional(Owner)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="member")
    Member member;

    //Mapping for messageThread to messages.
    // In this instance, one messageThread has the ability to belong to many messages
    //Because we want this to get mapped to message table and not to the messageThread table, this was made the owning side
    //Additionally, because it is mandatory for every message to belong to a thread, the fetch type is set to eager
    //Eager is the default, but it was made explicit to emphasize this.
    //Uni-Directional(Owner)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="message_thread")
    MessageThread messageThread;

}
