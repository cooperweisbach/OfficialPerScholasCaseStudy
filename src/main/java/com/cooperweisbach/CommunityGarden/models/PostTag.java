package com.cooperweisbach.CommunityGarden.models;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="posttag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE)
public class PostTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PostTagId")
    int postTagId;

    @NonNull
    @Column(name="postTagTitle")
    String postTagTitle;


    ///////////////////MAPPINGS///////////////////////////



    @ManyToMany(mappedBy = "post")
    List<Post> postList;

}
