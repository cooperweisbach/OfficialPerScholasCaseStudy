package com.cooperweisbach.CommunityGarden.models;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name="userroles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE)
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="UserRoleId")
    int userRoleId;
    @NonNull
    @NotBlank
    @Column(name="UserRoleName")
    String userRoleName;

    ///////////////////MAPPINGS///////////////////////////


    @ManyToMany(mappedBy="userRoles")
    List<Member> member;

}
