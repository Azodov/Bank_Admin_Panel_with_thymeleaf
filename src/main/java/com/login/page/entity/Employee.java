package com.login.page.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_holder")
    private String cardHolder;

    @Column(name = "card_number", unique = true)
    private String cardNumber;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "card_expiration_date")
    private String cardExpirationDate;

    @Column(name = "card_pin")
    private String cardPin;

    @Column(name = "card_billing_address")
    private String cardBillingAddress;

    @Column(name = "card_billing_city")
    private String cardBillingCity;

    @Column(name = "card_billing_zip")
    private String cardBillingZip;

    @Column(name = "card_billing_country")
    private String cardBillingCountry;

    @Column(name = "card_billing_phone")
    private String cardBillingPhone;

    @Column(name = "card_billing_time_zone")
    private String cardBillingTimeZone;

    @Column(name = "card_balance")
    private String cardBalance;

    @Column(name = "card_currency")
    private String cardCurrency;

    @Column(name = "card_status")
    private Boolean cardStatus;

    @Column(name = "card_created_date")
    private String cardCreatedDate;


}
