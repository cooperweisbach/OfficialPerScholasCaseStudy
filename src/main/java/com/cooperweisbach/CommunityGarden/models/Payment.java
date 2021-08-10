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
    @Column(name="PaymentId")
    int paymentId;

    @NonNull
    @NotNull
    @Column(name="PaymentAmount")
    Double paymentAmount;

    @NonNull
    @NotNull
    @Column(name="PaidOn")
    Date paidOn;


    ///////////////////MAPPINGS///////////////////////////

    @ManyToOne
    Member member;

    @ManyToOne
    PaymentType paymentType;



}
