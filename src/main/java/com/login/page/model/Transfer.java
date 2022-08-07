package com.login.page.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transfer {
    private String fromCardNumber;
    private String toCardNumber;
    private String amount;

}
