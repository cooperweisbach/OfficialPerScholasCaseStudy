package com.cooperweisbach.CommunityGarden.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="image_id")
    int imageId;

    @Column(name="image_upload_name")
    String imageUploadName;

    @Temporal(value = TemporalType.DATE)
    @Column(name="upload_date")
    Date uploadDate = new Timestamp(new Date().getTime());

    @Column(name="image_description")
    String imageDescription;

    @Column(name="member_id")
    int memberId;

    ///////////////////MAPPINGS///////////////////////////

    //https://vladmihalcea.com/manytoone-jpa-hibernate/
    // Mapping for image to imageType. In this instance image is the owning side of the relationship.
    // This is because we are creating the declaration in the Image entity. Here an image can have multiple imageType labels
    //This is because the imageType will be used by the admins to search for an image and display it in various places on the website.
    //Uni-Directional(Owner)
    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(
            name="image_imageType",
            joinColumns = {@JoinColumn(name="image_id")},
            inverseJoinColumns = {@JoinColumn(name="image_type")}
    )
    @JoinColumn(name="image_type")
    List<ImageType> imageType;

    public String getUploadPath(){
        if(imageType.get(0).getImageType().toLowerCase().equals("profilepicture"))
            return "/images/profilePicture/" + memberId + File.separator + imageUploadName;
        return "/images/" + imageType.get(0).getImageType() + "/" + imageUploadName;
    }

}
