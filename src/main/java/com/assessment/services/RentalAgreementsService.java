package com.assessment.services;

import com.assessment.PrototypeData.ToolData;
import com.assessment.exceptions.InvalidRequestException;
import com.assessment.exceptions.MissingRequiredFieldException;
import com.assessment.exceptions.ToolNotFoundException;
import com.assessment.requests.Checkout;
import com.assessment.responses.RentalAgreement;
import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static java.time.DayOfWeek.*;

@Service
@Slf4j
public class RentalAgreementsService {

    private final ToolData toolData;

    private final Mapper mapper;

    @Autowired
    public RentalAgreementsService(ToolData toolData, Mapper mapper) {
        this.toolData = toolData;
        this.mapper = mapper;
    }

    /***
     * Handles the checkout request
     * @param checkout The submitted checkout information.
     * @return a new rental agreement
     */
    public RentalAgreement createAgreement(Checkout checkout) throws Exception {
        log.info("Received request for checkout tool code: {}", checkout.getToolCode());
        log.debug("Checkout request: {}", checkout);

        validateCheckout(checkout);

        return createRentalAgreement(checkout);
    }

    /***
     * Validates the incoming request object and throws the appropriate exception if necessary
     * @param checkout The submitted checkout information
     */
    private void validateCheckout(Checkout checkout) throws Exception {
        if (checkout.getToolCode() == null || checkout.getToolCode().isBlank()) {
            throw new MissingRequiredFieldException("Missing required field - toolCode");
        } else if (checkout.getCheckoutDate() == null) {
            throw new MissingRequiredFieldException("Missing required field - checkoutDate");
        } else if (checkout.getRentalDays() < 1) {
            throw new InvalidRequestException("Rental days must be greater than 0");
        } else if(checkout.getDiscountPercent() < 0 || checkout.getDiscountPercent() > 100){
            throw new InvalidRequestException("Discount must be between 0 and 100 inclusive");
        } else if(!toolData.hasTool(checkout.getToolCode())){
            throw new ToolNotFoundException("Tool code not found - " + checkout.getToolCode());
        }
    }

    /***
     * Generates the new rental agreement.
     * @param checkout The submitted checkout information.
     * @return a new rental agreement
     */
    private RentalAgreement createRentalAgreement(Checkout checkout) {
        RentalAgreement ra = new RentalAgreement();
        mapper.map(checkout, ra);

        ra.setToolType(toolData.getToolType(checkout.getToolCode()));
        ra.setToolBrand(toolData.getBrand(checkout.getToolCode()));
        ra.setDueDate(ra.getCheckoutDate().plusDays(ra.getRentalDays()));
        ra.setDailyRentalCharge(toolData.getDailyCharge(checkout.getToolCode()));
        ra.setChargeDays(calculateChargeDays(checkout));
        ra.setPreDiscountCharge(roundToCents(ra.getChargeDays() * toolData.getDailyCharge(checkout.getToolCode())));
        ra.setDiscountAmount(calculateDiscountedCharge(checkout.getDiscountPercent(), ra.getPreDiscountCharge()));
        ra.setFinalCharge(calculateFinalCharge(ra.getPreDiscountCharge(), ra.getDiscountAmount()));

        log.debug(ra.toString());

        return ra;
    }

    /***
     * Determines the final charge for the rental agreement
     * @param preDiscountCharge Charge before discount
     * @param discount Amount of discount to apply
     * @return final rental charge amount
     */
    private double calculateFinalCharge(double preDiscountCharge, double discount) {
        return roundToCents(preDiscountCharge - discount);
    }

    /***
     * Determines how much to discount the rental
     * @param discountPercent percentage discount for the rental (0-100)
     * @param charge amount of the charge without discount
     * @return discounted rental cost
     */
    private double calculateDiscountedCharge(int discountPercent, double charge) {
        double discount = charge * (1 - ((double) discountPercent / 100));
        return roundToCents(charge - discount);
    }

    /***
     * Rounds double values to cents
     * @param value value to rount
     * @return rounded value
     */
    private double roundToCents(double value) {
        return (double) Math.round(value * 100d) / 100;
    }

    /***
     * Take into account which days can be charged for and return that result.
     * @param checkout the provided checkout information
     * @return net chargeable days
     */
    private int calculateChargeDays(Checkout checkout) {
        int chargeDays = 0;
        LocalDate rentalPeriodStart = checkout.getCheckoutDate().plusDays(1);
        LocalDate rentalPeriodEnd = rentalPeriodStart.plusDays(checkout.getRentalDays());

        for (int i = 0; i < checkout.getRentalDays(); i++) {
            LocalDate checkDate = rentalPeriodStart.plusDays(i);
            if (toolData.hasWeekdayCharge(checkout.getToolCode()) && !isWeekend(checkDate) ||
                    toolData.hasWeekendCharge(checkout.getToolCode()) && isWeekend(checkDate)) {
                chargeDays++;
            }
        }

        LocalDate indDayObs = getIndependenceDayObserved(rentalPeriodStart.getYear());
        LocalDate laborDay = getLaborDay(rentalPeriodStart.getYear());

        if (!toolData.hasHolidayCharge(checkout.getToolCode())) {
            if (includesDate(indDayObs, rentalPeriodStart, rentalPeriodEnd)) {
                chargeDays--;
            }
            if (includesDate(laborDay, rentalPeriodStart, rentalPeriodEnd)) {
                chargeDays--;
            }
        }

        return chargeDays;
    }

    /***
     * Determine what day the US Independence day is observed on.
     * @param year Year to determine date for
     * @return observed US Independence day date
     */
    private LocalDate getIndependenceDayObserved(int year) {
        LocalDate indDay = LocalDate.of(year, 7, 4);

        if (isSaturday(indDay)) {
            indDay = indDay.minusDays(1);
        } else if (isSunday(indDay)) {
            indDay = indDay.plusDays(1);
        }

        return indDay;
    }

    /***
     * Determine Labor day for given year
     * @param year year to determine Labor day for
     * @return labor day
     */
    private LocalDate getLaborDay(int year) {
        LocalDate firstOfSeptember = LocalDate.of(year, 9, 1);
        return firstOfSeptember.with(TemporalAdjusters.firstInMonth(MONDAY));
    }

    /***
     * Check to see if the date is included in the range provided
     * @param checkDate date to check if it falls in one or between the start and end date
     * @param startDate beginning of date range to check
     * @param endDate end of date range to check
     * @return
     */
    private boolean includesDate(LocalDate checkDate, LocalDate startDate, LocalDate endDate) {
        return !(checkDate.isBefore(startDate) || checkDate.isAfter(endDate));
    }

    /***
     * Determine if the day falls on a weekend
     * @param checkDate date to check
     * @return if the date is a weekend day or not
     */
    private boolean isWeekend(LocalDate checkDate) {
        return isSaturday(checkDate) || isSunday(checkDate);
    }

    /***
     * Determine if the day falls on Saturday
     * @param checkDate date to check
     * @return if the date is Saturday or not
     */
    private boolean isSaturday(LocalDate checkDate) {
        return checkDate.getDayOfWeek() == SATURDAY;
    }

    /***
     * Determine if the day falls on a Sunday
     * @param checkDate date to check
     * @return if the date is Sunday or not
     */
    private boolean isSunday(LocalDate checkDate) {
        return checkDate.getDayOfWeek() == SUNDAY;
    }

}
