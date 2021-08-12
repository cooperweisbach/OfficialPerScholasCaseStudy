package com.cooperweisbach.CommunityGarden.models;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="image_type_id")
    int imageTypeId;
    @NonNull
    @NotBlank
    @Column(name="image_type")
    String imageType;

}
