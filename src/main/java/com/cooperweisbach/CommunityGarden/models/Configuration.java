package com.cooperweisbach.CommunityGarden.models;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="configuration_id")
    int configurationId;

    @NonNull
    @NotBlank
    @Column(name = "configuaration_name")
    String configurationName;

    @Temporal(value = TemporalType.DATE)
    @Column(name="upload_date")
    Date uploadDate = new Timestamp(new Date().getTime());

    @NonNull
    @NotNull
    @Column(name="jsonification", columnDefinition = "TEXT")
    String jsonification;

    @NonNull
    @NotNull
    @Column(name="published")
    Boolean published;

}
