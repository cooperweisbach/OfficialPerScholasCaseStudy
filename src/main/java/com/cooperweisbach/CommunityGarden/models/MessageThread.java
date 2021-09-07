package com.cooperweisbach.CommunityGarden.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE)
public class MessageThread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="message_thread_id")
    int messageThreadId;

    @NonNull
    @NotBlank
    @Column(name="message_thread_name", columnDefinition = "VARCHAR(100) NOT NULL")
    String messageThreadName;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name="message_thread_creation")
    Date messageThreadCreation = new Timestamp(new Date().getTime());



    ///////////////////MAPPINGS///////////////////////////

    //Mapping for message thread to thread status.
    //In this instance, one status has the ability to belong to many threads
    //We want this status to live on the thread table so that the thread is easily identifiable by status
    //Uni-Directional(Owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="thread_status")
    MessageThreadStatus messageThreadStatus;
}
