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
    @Column(name="post_tag_id")
    int postTagId;

    @NonNull
    @Column(name="post_tag_title")
    String postTagTitle;


    ///////////////////MAPPINGS///////////////////////////


    //This is the inverse/non-owning/child element in this manytomany relationship between Posts and PostTags
    //This is the case because the element with the mapped by parameter is mapped by a collection in another class
    //In this case, the other collection is the list, "postTagList" in the Post class
    @ManyToMany(mappedBy = "postTagList")
    List<Post> postList;

}
