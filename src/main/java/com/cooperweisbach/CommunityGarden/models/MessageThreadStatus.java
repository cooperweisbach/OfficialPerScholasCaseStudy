package com.cooperweisbach.CommunityGarden.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="messagethreadstatus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageThreadStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="message_thread_status_id")
    int MessageThreadStatusId;
    @NonNull
    @NotBlank
    @Column(name="message_thread_status")
    String messageThreadStatus;
}
