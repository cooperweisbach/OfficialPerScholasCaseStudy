package com.cooperweisbach.CommunityGarden.models;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Post {

    //Resources
    //https://www.dummies.com/web-design-development/wordpress/installing-configuring/using-a-mysql-database-to-manage-your-wordpress-blog-data/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PostId")
    int postId;
    @NonNull
    @Temporal(value=TemporalType.DATE)
    Date postDate;
    @NonNull
    @Temporal(value=TemporalType.DATE)
    Date creationDate;
    @NonNull
    @Column(name="PostTitle", columnDefinition = "VARCHAR(150) NOT NULL")
    String postTitle;
    @NonNull
    @Column(name="PostContent", columnDefinition = "TEXT NOT NULL")
    String postContent;

    ///////////////////MAPPINGS///////////////////////////

    @ManyToOne
    PostStatus postStatus;
    @ManyToOne
    Member member;


    @ManyToMany
    List<PostTag> postTagList;

}
