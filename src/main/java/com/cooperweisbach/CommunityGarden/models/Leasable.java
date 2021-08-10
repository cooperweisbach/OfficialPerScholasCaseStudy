package com.cooperweisbach.CommunityGarden.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="leasable")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Leasable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="LeasableId")
    int leaseableId;
    @NonNull
    @NotBlank
    @Column(name="LeasableCode")
    String leasableCode;
    @NonNull
    @NotNull
    @Column(name="LeasableYearlyRent")
    double leasableYearlyRent;
    @NonNull
    @NotNull
    @Column(name="LeasableSize")
    double leasableSize;
    @NonNull
    @Column(name="creationDate")
    @Temporal(value = TemporalType.DATE)
    Date leasableCreationDate;


    ///////////////////MAPPINGS///////////////////////////

    //Mapping for leases to leasables, where leasables are the leasable entities
    // ( i.e. garden plots, bee hives, etc.). In this instance, one lease will belong to one
    // leasable entity and vice versa.
    //Uni-Directional(Non-Owner)
//    @OneToMany(mappedBy = "leasable")
//    List<Lease> lease;


    //Mapping for leasables to their type, where leasable types are the types of entities
    // ( i.e. garden plots, bee hives, etc.). In this instance, every leasable should have
    // a type assigned to it and that type may be the same as other leasables' types.
    //Uni-Directional(Owner)
    @ManyToOne
    LeasableType leasableType;

    //Mapping for leasables to their status, where leasables are the leasable entities
    // ( i.e. garden plots, bee hives, etc.). In this instance, every leasable should have
    // a status assigned to it and that status may be the same as other leasables' statuses.
    // (i.e. active lease, closed, open)
    //Uni-Directional(Owner)
    @ManyToOne
    LeasableStatus leasableStatus;
}
