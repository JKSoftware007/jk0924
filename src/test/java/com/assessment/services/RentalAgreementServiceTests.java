package com.assessment.services;

import com.assessment.config.Config;
import com.assessment.exceptions.InvalidRequestException;
import com.assessment.exceptions.MissingRequiredFieldException;
import com.assessment.exceptions.ToolNotFoundException;
import com.assessment.requests.Checkout;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RentalAgreementServiceTests {
    private final Config config = new Config();
    private final RentalAgreementsService service = new RentalAgreementsService(config.getToolData(), config.getMapper());

    @Test
    void testIsSundayTrue() throws Exception{
        LocalDate test = LocalDate.of(2024, 9, 29);

        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("isSunday", LocalDate.class);
        privateMethod.setAccessible(true);

        assertTrue((boolean) privateMethod.invoke(service, test));
    }

    @Test
    void testIsSundayFalse() throws Exception{
        LocalDate test = LocalDate.of(2024, 9, 30);
        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("isSunday", LocalDate.class);
        privateMethod.setAccessible(true);

        assertFalse((boolean) privateMethod.invoke(service, test));
    }

    @Test
    void testIsSaturdayTrue() throws Exception{
        LocalDate test = LocalDate.of(2024, 9, 28);
        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("isSaturday", LocalDate.class);
        privateMethod.setAccessible(true);

        assertTrue((boolean) privateMethod.invoke(service, test));
    }

    @Test
    void testIsSaturdayFalse() throws Exception{
        LocalDate test = LocalDate.of(2024, 9, 30);
        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("isSaturday", LocalDate.class);
        privateMethod.setAccessible(true);

        assertFalse((boolean) privateMethod.invoke(service, test));
    }

    @Test
    void testIsWeekendTrue() throws Exception{
        LocalDate test = LocalDate.of(2024, 9, 29);
        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("isWeekend", LocalDate.class);
        privateMethod.setAccessible(true);

        assertTrue((boolean) privateMethod.invoke(service, test));
    }

    @Test
    void testIsWeekendFalse() throws Exception{
        LocalDate test = LocalDate.of(2024, 9, 30);
        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("isWeekend", LocalDate.class);
        privateMethod.setAccessible(true);

        assertFalse((boolean) privateMethod.invoke(service, test));
    }

    @Test
    void testDateIncludedFalse() throws Exception {
        LocalDate check = LocalDate.of(2024, 9, 20);
        LocalDate begin = LocalDate.of(2024, 9, 21);
        LocalDate end = LocalDate.of(2024, 9, 23);

        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("includesDate", LocalDate.class, LocalDate.class, LocalDate.class);
        privateMethod.setAccessible(true);

        assertFalse((boolean) privateMethod.invoke(service, check, begin, end));
    }

    @Test
    void testDateIncludedTrue() throws Exception {
        LocalDate check = LocalDate.of(2024, 9, 22);
        LocalDate begin = LocalDate.of(2024, 9, 21);
        LocalDate end = LocalDate.of(2024, 9, 23);

        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("includesDate", LocalDate.class, LocalDate.class, LocalDate.class);
        privateMethod.setAccessible(true);

        assertTrue((boolean) privateMethod.invoke(service, check, begin, end));
    }

    @Test
    void testDateIncludedTrueBeginEdge() throws Exception {
        LocalDate check = LocalDate.of(2024, 9, 21);
        LocalDate begin = LocalDate.of(2024, 9, 21);
        LocalDate end = LocalDate.of(2024, 9, 23);

        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("includesDate", LocalDate.class, LocalDate.class, LocalDate.class);
        privateMethod.setAccessible(true);

        assertTrue((boolean) privateMethod.invoke(service, check, begin, end));
    }

    @Test
    void testDateIncludedTrueEndEdge() throws Exception {
        LocalDate check = LocalDate.of(2024, 9, 23);
        LocalDate begin = LocalDate.of(2024, 9, 21);
        LocalDate end = LocalDate.of(2024, 9, 23);

        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("includesDate", LocalDate.class, LocalDate.class, LocalDate.class);
        privateMethod.setAccessible(true);

        assertTrue((boolean) privateMethod.invoke(service, check, begin, end));
    }

    @Test
    void testGetLaborDay1() throws Exception {
        int year = 2004;
        LocalDate check = LocalDate.of(year, 9, 6);

        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("getLaborDay", int.class);
        privateMethod.setAccessible(true);

        assertEquals(check, privateMethod.invoke(service, year));
    }

    @Test
    void testGetLaborDay2() throws Exception {
        int year = 2024;
        LocalDate check = LocalDate.of(year, 9, 2);

        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("getLaborDay", int.class);
        privateMethod.setAccessible(true);

        assertEquals(check, privateMethod.invoke(service, year));
    }

    @Test
    void testGetIndDayObservedOnTime() throws Exception {
        int year = 2025;
        LocalDate check = LocalDate.of(year, 7, 4);

        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("getIndependenceDayObserved", int.class);
        privateMethod.setAccessible(true);

        assertEquals(check, privateMethod.invoke(service, year));
    }

    @Test
    void testGetIndDayObservedEarly() throws Exception {
        int year = 2026;
        LocalDate check = LocalDate.of(year, 7, 3);

        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("getIndependenceDayObserved", int.class);
        privateMethod.setAccessible(true);

        assertEquals(check, privateMethod.invoke(service, year));
    }

    @Test
    void testGetIndDayObservedLate() throws Exception {
        int year = 2027;
        LocalDate check = LocalDate.of(year, 7, 5);

        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("getIndependenceDayObserved", int.class);
        privateMethod.setAccessible(true);

        assertEquals(check, privateMethod.invoke(service, year));
    }

    @Test
    void testChargedDaysChainsaw() throws Exception {
        LocalDate checkoutDate = LocalDate.of(2025, 7, 2);
        Checkout checkout = new Checkout("CHNS", 6, 10, checkoutDate);

        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("calculateChargeDays", Checkout.class);
        privateMethod.setAccessible(true);

        assertEquals(4, privateMethod.invoke(service, checkout));
    }

    @Test
    void testChargedDaysLadder() throws Exception {
        LocalDate checkoutDate = LocalDate.of(2025, 7, 2);
        Checkout checkout = new Checkout("LADW", 6, 10, checkoutDate);

        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("calculateChargeDays", Checkout.class);
        privateMethod.setAccessible(true);

        assertEquals(5, privateMethod.invoke(service, checkout));
    }

    @Test
    void testChargedDaysJackhammer() throws Exception {
        LocalDate checkoutDate = LocalDate.of(2025, 7, 2);
        Checkout checkout = new Checkout("JAKD", 6, 10, checkoutDate);

        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("calculateChargeDays", Checkout.class);
        privateMethod.setAccessible(true);

        assertEquals(3, privateMethod.invoke(service, checkout));
    }

    @Test
    void testCalculateDiscountedCharge1() throws Exception {
        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("calculateDiscountedCharge", int.class, double.class);
        privateMethod.setAccessible(true);

        assertEquals(1.00, privateMethod.invoke(service, 10, 10));
    }

    @Test
    void testCalculateDiscountedCharge2() throws Exception {
        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("calculateDiscountedCharge", int.class, double.class);
        privateMethod.setAccessible(true);

        assertEquals(25.00, privateMethod.invoke(service, 25, 100));
    }

    @Test
    void testCalculateFinalCharge() throws Exception {
        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("calculateFinalCharge", double.class, double.class);
        privateMethod.setAccessible(true);

        assertEquals(38.07, privateMethod.invoke(service, 65.28, 27.21));
    }

    @Test
    void testValidationMissingToolCode() throws Exception {
        Checkout checkout = new Checkout();

        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("validateCheckout", Checkout.class);
        privateMethod.setAccessible(true);

        try{
            privateMethod.invoke(service, checkout);
        } catch (InvocationTargetException e) {
            assertEquals(MissingRequiredFieldException.class, e.getCause().getClass());
            assertTrue(e.getCause().getMessage().contains("toolCode"));
        }
    }

    @Test
    void testValidationMissingCheckoutDate() throws Exception {
        Checkout checkout = new Checkout();
        checkout.setToolCode("CHNS");

        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("validateCheckout", Checkout.class);
        privateMethod.setAccessible(true);

        try{
            privateMethod.invoke(service, checkout);
        } catch (InvocationTargetException e) {
            assertEquals(MissingRequiredFieldException.class, e.getCause().getClass());
            assertTrue(e.getCause().getMessage().contains("checkoutDate"));
        }
    }

    @Test
    void testValidationBadRentalDays() throws Exception {
        Checkout checkout = new Checkout();
        checkout.setToolCode("CHNS");
        checkout.setCheckoutDate(LocalDate.of(2024, 9, 6));

        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("validateCheckout", Checkout.class);
        privateMethod.setAccessible(true);

        try{
            privateMethod.invoke(service, checkout);
        } catch (InvocationTargetException e) {
            assertEquals(InvalidRequestException.class, e.getCause().getClass());
            assertTrue(e.getCause().getMessage().contains("must be greater"));
        }
    }

    @Test
    void testValidationBadDiscountPercent() throws Exception {
        Checkout checkout = new Checkout();
        checkout.setToolCode("CHNS");
        checkout.setCheckoutDate(LocalDate.of(2024, 9, 6));
        checkout.setRentalDays(5);
        checkout.setDiscountPercent(200);

        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("validateCheckout", Checkout.class);
        privateMethod.setAccessible(true);

        try{
            privateMethod.invoke(service, checkout);
        } catch (InvocationTargetException e) {
            assertEquals(InvalidRequestException.class, e.getCause().getClass());
            assertTrue(e.getCause().getMessage().contains("must be between"));
        }
    }

    @Test
    void testValidationToolNotFound() throws Exception {
        Checkout checkout = new Checkout();
        checkout.setToolCode("CH");
        checkout.setCheckoutDate(LocalDate.of(2024, 9, 6));
        checkout.setRentalDays(5);
        checkout.setDiscountPercent(10);

        Method privateMethod = RentalAgreementsService.class.getDeclaredMethod("validateCheckout", Checkout.class);
        privateMethod.setAccessible(true);

        try{
            privateMethod.invoke(service, checkout);
        } catch (InvocationTargetException e) {
            assertEquals(ToolNotFoundException.class, e.getCause().getClass());
            assertTrue(e.getCause().getMessage().contains("Tool code not found"));
        }
    }
}
