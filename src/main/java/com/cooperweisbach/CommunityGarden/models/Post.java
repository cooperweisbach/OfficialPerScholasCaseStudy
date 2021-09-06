package com.cooperweisbach.CommunityGarden.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "postId")
public class Post {

    //Resources
    //https://www.dummies.com/web-design-development/wordpress/installing-configuring/using-a-mysql-database-to-manage-your-wordpress-blog-data/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    int postId;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name="post_date")
    Date postDate;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name="creation_date")
    Date creationDate = new Timestamp(new Date().getTime());

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
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    PostStatus postStatus;

    //Mapping for member to profile pics.
    // In this instance, one profile pic has the ability to belong to many users due to default images
    //However, by no means will other user's profile pics be accessible by other users. This is simply to accommodate defaults
    //Uni-Directional(Owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Member member;

    //This is the owning/parent element in this manytomany relationship between Posts and PostTags
    //This is the case because the element with the Join Table annotation is declaring the join table from its POV
    //Here, the join column is a post_id while the inverse join column is post_tag_id.
    @ManyToMany()
            @JoinTable(
                    name="post_post_tag",
                    joinColumns= {@JoinColumn(name="post_id")},
                    inverseJoinColumns = {@JoinColumn(name="post_tag_id")}
            )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    List<PostTag> postTagList;

}
