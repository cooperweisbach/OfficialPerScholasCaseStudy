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
    @Column(name="ImageId")
    int imageId;
    @NonNull
    @NotBlank
    @Column(name="ImageUploadName")
    String imageUploadName;
    @NonNull
    @Temporal(value = TemporalType.DATE)
    Date uploadDate;

    @ManyToOne
    ImageStatus imageStatus;

}
