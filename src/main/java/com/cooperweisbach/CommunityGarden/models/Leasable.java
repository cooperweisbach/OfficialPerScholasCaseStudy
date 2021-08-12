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
public class Leasable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="leasable_id")
    int leasableId;
    @NonNull
    @NotBlank
    @Column(name="leasable_code")
    String leasableCode;
    @NonNull
    @NotNull
    @Column(name="leasable_yearly_rent")
    double leasableYearlyRent;
    @NonNull
    @NotNull
    @Column(name="leasable_size")
    double leasableSize;

    @Column(name="creation_date")
    @Temporal(value = TemporalType.DATE)
    Date leasableCreationDate = new Timestamp(new Date().getTime());


    ///////////////////MAPPINGS///////////////////////////


    //Mapping for leasables to their type, where leasable types are the types of entities
    // ( i.e. garden plots, bee hives, etc.). In this instance, every leasable should have
    // a type assigned to it and that type may be the same as other leasables' types.
    //Uni-Directional(Owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="leasable_type")
    LeasableType leasableType;

    //Mapping for leasables to their status, where leasables are the leasable entities
    // ( i.e. garden plots, bee hives, etc.). In this instance, every leasable should have
    // a status assigned to it and that status may be the same as other leasables' statuses.
    // (i.e. active lease, closed, open)
    //Uni-Directional(Owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leasable_status")
    LeasableStatus leasableStatus;
}
