package com.cooperweisbach.CommunityGarden.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="leasabletype")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LeasableType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="LeasableTypeId")
    int leaseableTypeId;
    @NonNull
    @NotBlank
    @Column(name="LeasableTypeName")
    String leasableTypeName;

}
