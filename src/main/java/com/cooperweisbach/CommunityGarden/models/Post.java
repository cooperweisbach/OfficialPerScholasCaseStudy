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
    @Column(name="post_id")
    int postId;
    @NonNull
    @Temporal(value=TemporalType.DATE)
    @Column(name="post_date")
    Date postDate;
    @NonNull
    @Temporal(value=TemporalType.DATE)
    @Column(name="creation_date")
    Date creationDate;
    @NonNull
    @Column(name="post_title", columnDefinition = "VARCHAR(150) NOT NULL")
    String postTitle;
    @NonNull
    @Column(name="post_content", columnDefinition = "TEXT NOT NULL")
    String postContent;

    ///////////////////MAPPINGS///////////////////////////

    //Mapping for member to profile pics.
    // In this instance, one profile pic has the ability to belong to many users due to default images
    //However, by no means will other user's profile pics be accessible by other users. This is simply to accommodate defaults
    //Uni-Directional(Owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="post_status")
    PostStatus postStatus;

    //Mapping for member to profile pics.
    // In this instance, one profile pic has the ability to belong to many users due to default images
    //However, by no means will other user's profile pics be accessible by other users. This is simply to accommodate defaults
    //Uni-Directional(Owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member")
    Member member;

    //This is the owning/parent element in this manytomany relationship between Posts and PostTags
    //This is the case because the element with the Join Table annotation is declaring the join table from its POV
    //Here, the join column is a post_id while the inverse join column is post_tag_id.
    @ManyToMany( cascade = {CascadeType.ALL})
            @JoinTable(
                    name="post_tags",
                    joinColumns= {@JoinColumn(name="post_id")},
                    inverseJoinColumns = {@JoinColumn(name="post_tag_id")}
            )
    List<PostTag> postTagList;

}
