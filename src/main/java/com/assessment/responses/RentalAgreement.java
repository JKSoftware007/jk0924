package com.assessment.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Objects;


/***
 * Response object representing a rental agreement
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalAgreement {
    private String toolCode;
    private String toolType;
    private String toolBrand;
    private int rentalDays;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate checkoutDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate dueDate;
    private double dailyRentalCharge;
    private int chargeDays;
    private double preDiscountCharge;
    private int discountPercent;
    private double discountAmount;
    private double finalCharge;

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat( "0.00" );
        return "Tool code: " + toolCode + "\n" +
                "Tool type: " + toolType + "\n" +
                "Tool brand: " + toolBrand + "\n" +
                "Rental days: " + rentalDays + "\n" +
                "Checkout date: " + checkoutDate + "\n" +
                "Due date: " + dueDate + "\n" +
                "Daily rental charge: $" + decimalFormat.format(dailyRentalCharge) + "\n" +
                "Charge days: " + chargeDays + "\n" +
                "Pre discount charge: $" + decimalFormat.format(preDiscountCharge) + "\n" +
                "Discount percent : " + discountPercent + "%\n" +
                "Discount amount: $" + decimalFormat.format(discountAmount) + "\n" +
                "Final Charge: $" + decimalFormat.format(finalCharge);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalAgreement that = (RentalAgreement) o;
        return getRentalDays() == that.getRentalDays() && Double.compare(getDailyRentalCharge(), that.getDailyRentalCharge()) == 0 && getChargeDays() == that.getChargeDays() && Double.compare(getPreDiscountCharge(), that.getPreDiscountCharge()) == 0 && getDiscountPercent() == that.getDiscountPercent() && Double.compare(getDiscountAmount(), that.getDiscountAmount()) == 0 && Double.compare(getFinalCharge(), that.getFinalCharge()) == 0 && Objects.equals(getToolCode(), that.getToolCode()) && Objects.equals(getToolType(), that.getToolType()) && Objects.equals(getToolBrand(), that.getToolBrand()) && Objects.equals(getCheckoutDate(), that.getCheckoutDate()) && Objects.equals(getDueDate(), that.getDueDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToolCode(), getToolType(), getToolBrand(), getRentalDays(), getCheckoutDate(), getDueDate(), getDailyRentalCharge(), getChargeDays(), getPreDiscountCharge(), getDiscountPercent(), getDiscountAmount(), getFinalCharge());
    }
}
