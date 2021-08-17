package com.cooperweisbach.CommunityGarden.models;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
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
    @Column(name="user_role_id")
    int userRoleId;
    @NonNull
    @NotBlank
    @Column(name="user_role")
    String userRoleName;

    ///////////////////MAPPINGS///////////////////////////

    //This is the inverse/non-owning/child element in this manytomany relationship between Members and UserRoles
    //This is the case because the element with the mapped by parameter is mapped by a collection in another class
    //In this case, the other collection is the list, "userRoles" in the Member class
//    @ManyToMany(mappedBy="userRoles")
//    List<Member> member;

}
