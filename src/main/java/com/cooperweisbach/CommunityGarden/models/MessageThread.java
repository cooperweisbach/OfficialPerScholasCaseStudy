package com.cooperweisbach.CommunityGarden.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name="messagethread")
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
    @Column(name="MessageThreadId")
    int messageThreadId;

    @NonNull
    @NotBlank
    @Column(name="MessageThreadName", columnDefinition = "VARCHAR(100) NOT NULL")
    String messageThreadName;

    @NonNull
    @Temporal(value=TemporalType.DATE)
    Date messageThreadCreation;

    @ManyToOne
    MessageThreadStatus messageThreadStatus;
}
