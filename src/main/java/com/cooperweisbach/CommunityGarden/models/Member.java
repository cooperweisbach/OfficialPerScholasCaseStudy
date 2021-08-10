package com.cooperweisbach.CommunityGarden.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MemberId")
    int memberId;
    @NonNull
    @NotNull
    @Column(name = "FirstName", columnDefinition = "VARCHAR(50) NOT NULL")
    String firstName;
    @NonNull
    @NotNull
    @Column(name = "LastName", columnDefinition = "VARCHAR(50) NOT NULL")
    String lastName;
    @NonNull
    @NotNull
    @Column(name="Email", columnDefinition = "NOT NULL")
    @Email
    String email;
    @NonNull
    @NotNull
    @Column(name="PhoneNumber", columnDefinition = "VARCHAR(12) NOT NULL")
    String phoneNumber;
    @NonNull
    @NotNull
    @Column(name="Password")
    String password;
    @NonNull
    @NotNull
    @Column(name="JoinedDate")
    @Temporal(TemporalType.DATE)
    Date joinedDate;

    ///////////////////MAPPINGS///////////////////////////

    @OneToMany(mappedBy = "member")
    List<Lease> leases;
    @OneToMany(mappedBy = "member")
    List<Post> posts;
    @OneToMany(mappedBy = "member")
    List<Message> messages;
    @OneToMany(mappedBy = "member")
    List<Payment> payments;

    @ManyToOne
    MemberStatus memberStatus;
    @ManyToOne
    Image profilePic;

    @ManyToMany(mappedBy="member")
    List<UserRoles> userRoles;

}
