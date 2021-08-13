package com.cooperweisbach.CommunityGarden.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
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
    @Column(name="member_id")
    int memberId;
    @NonNull
    @NotBlank
    @Column(name = "first_name")
    String firstName;
    @NonNull
    @NotBlank
    @Column(name = "last_name")
    String lastName;
    @NonNull
    @NotBlank
    @Column(name="email")
    @Email
    String email;
    @NonNull
    @NotBlank
    @Column(name="phone_number")
    String phoneNumber;
    @NonNull
    @NotBlank
    @Column(name="password")
    String password;

    @Column(name="joined_date")
    @Temporal(TemporalType.DATE)
    Date joinedDate = new Timestamp(new Date().getTime());

    ///////////////////MAPPINGS///////////////////////////

    //Mapping for member to memberStatus, where status could include ( i.e. active, inactive, hold).
    // In this instance, one status will belong to various members at one time.
    // leases over time.
    //Uni-Directional(Owner)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_status")
    MemberStatus memberStatus;

    //Mapping for member to profile pics.
    // In this instance, one profile pic has the ability to belong to many users due to default images
    //However, by no means will other user's profile pics be accessible by other users. This is simply to accommodate defaults
    //Uni-Directional(Owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="profile_pic")
    Image profilePic;

    //This is the owning/parent element in this manytomany relationship between Members and UserRoles
    //This is the case because the element with the Join Table annotation is declaring the join table from its POV
    //Here, the join column is a member_id while the inverse join column is role_id.
    //Cascade All
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "member_role",
            joinColumns = { @JoinColumn(name = "member_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    List<UserRoles> userRoles;


    public String convertUserRolesListToString(){
        return userRoles.stream().map(userRole -> userRole.getUserRoleName()).collect(Collectors.joining(","));
    }

}
