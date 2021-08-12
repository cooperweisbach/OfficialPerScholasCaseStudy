package com.cooperweisbach.CommunityGarden.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lease_id")
    int leaseId;

    @Temporal(value = TemporalType.DATE)
    @Column(name="start_date")
    Date startDate = new Timestamp(new Date().getTime());

    @Temporal(value = TemporalType.DATE)
    @Column(name="end_date")
    Date endDate;

    ///////////////////MAPPINGS///////////////////////////

    // Mapping for leases to members. In this instance, many leases can belong to one member.
    // For example, member 1 may have 2 garden plots and a beehive each with their own lease.
    //Uni-Directional(Owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member")
    Member member;

    // Mapping for leases to their status. In this instance, many leases can belong to one status.
    // For example, (status 1: active) may be the status more a number of leases at one time.
    //Uni-Directional(Owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="lease_status")
    LeaseStatus leaseStatus;

    //Mapping for leases to leasables, where leasables are the leasable entities
    // ( i.e. garden plots, bee hives, etc.). In this instance, one leasable will belong to many
    // leases over time.
    //Uni-Directional(Owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="leasable_id")
//    @JoinColumn(name="LeasableId", referencedColumnName = "LeaseId")
    Leasable leasable;
}
