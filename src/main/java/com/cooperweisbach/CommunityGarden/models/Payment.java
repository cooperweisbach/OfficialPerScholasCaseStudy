package com.cooperweisbach.CommunityGarden.models;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="payment_id")
    int paymentId;

    @NonNull
    @NotNull
    @Column(name="payment_amount")
    Double paymentAmount;

    @NonNull
    @NotNull
    @Column(name="paid_on")
    Date paidOn;


    ///////////////////MAPPINGS///////////////////////////

    //Mapping for payment to member.
    //In this instance, many payments have the ability to belong to a single member
    //We want this to live on the payments table because each payment is unique and will have the
    //ability to reference the same member in multiple rows without the data being redundant.
    //If on the member table, then the member rows would be repeated everytime they made a new payment
    //Uni-Directional(Owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member")
    Member member;

    //Mapping for payments to payment type.
    //In this instance, one type has the ability to belong to many payments
    //We want this type to live on the payment table so that the payment is easily identifiable by type
    //Types would include "ForLease" "MemberDues" "NewMemberFee" "Supplies etc.
    //Uni-Directional(Owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="payment_type")
    PaymentType paymentType;



}
