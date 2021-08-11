package com.cooperweisbach.CommunityGarden.models;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name="image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="image_id")
    int imageId;
    @NonNull
    @NotBlank
    @Column(name="image_upload_name")
    String imageUploadName;
    @NonNull
    @Temporal(value = TemporalType.DATE)
    @Column(name="upload_date")
    Date uploadDate;

    ///////////////////MAPPINGS///////////////////////////

    //https://vladmihalcea.com/manytoone-jpa-hibernate/
    // Mapping for image to imageStatus. In this instance image is the owning side of the relationship.
    // This is because we want to see the status of the image in the Image table. Many to one is used because
    // many images will have the same imageStatus. ManyToOne annotation allows you to map a Foreign Key column
    //Uni-Directional(Owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="image_status")
    ImageStatus imageStatus;

}
