package com.cooperweisbach.CommunityGarden.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
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
    @Column(name="leasable_type_id")
    int leasableTypeId;
    @NonNull
    @NotBlank
    @Column(name="leasable_type_name")
    String leasableTypeName;

}
