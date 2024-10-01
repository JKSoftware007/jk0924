package com.assessment.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/***
 * Request object to get a rental agreement with
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Checkout {
    private String toolCode;
    private int rentalDays;
    private int discountPercent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate checkoutDate;

    @Override
    public String toString() {
        return "Checkout{" +
                "toolCode='" + toolCode + '\'' +
                ", rentalDays=" + rentalDays +
                ", discountPercent=" + discountPercent +
                ", checkoutDate=" + checkoutDate +
                '}';
    }
}
