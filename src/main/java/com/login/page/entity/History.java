package com.login.page.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "History")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_card_number")
    private String fromCardNumber;

    @Column(name = "to_card_number")
    private String toCardNumber;

    @Column(name = "amount")
    private String amount;

    @Column(name = "date")
    private String date;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "description")
    private String description;


}
