package com.login.page.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "clients")
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
    private String card_holder;

    @Column(name = "card_number", unique = true)
    private String card_number;

    @Column(name = "card_type")
    private String card_type;

    @Column(name = "card_expiration_date")
    private String card_expiration_date;

    @Column(name = "card_pin")
    private String card_pin;

    @Column(name = "card_billing_address")
    private String card_billing_address;

    @Column(name = "card_billing_city")
    private String card_billing_city;

    @Column(name = "card_billing_zip")
    private String card_billing_zip;

    @Column(name = "card_billing_country")
    private String card_billing_country;

    @Column(name = "card_billing_phone")
    private String card_billing_phone;

    @Column(name = "card_billing_time_zone")
    private String card_billing_time_zone;

    @Column(name = "card_balance")
    private String card_balance;

    @Column(name = "card_currency")
    private String card_currency;

    @Column(name = "card_status")
    private Boolean card_status;

    @Column(name = "card_created_date")
    private String card_created_date;


}
