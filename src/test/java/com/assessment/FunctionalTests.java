package com.assessment;

import com.assessment.requests.Checkout;
import com.assessment.responses.ErrorInfo;
import com.assessment.responses.RentalAgreement;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = "server.port=8080")
class FunctionalTests {

    private final int port =  8080;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private final ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    private final String checkoutUrl = "http://127.0.0.1:" + port + "/v1/rental-application/checkout";

    private static HttpHeaders headers;

    @BeforeAll
    public static void runBeforeAllTestMethods() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void getRentalAgreementForChainsaw() throws Exception {
        Checkout checkout = new Checkout("CHNS", 4, 25, LocalDate.of(2024, 9, 28));
        RentalAgreement ra = new RentalAgreement("CHNS", "Chainsaw", "Stihl", 4, LocalDate.of(2024, 9, 28),
                LocalDate.of(2024, 10, 2), 1.49, 3, 4.47, 25, 1.12, 3.35);

        HttpEntity<String> request =
                new HttpEntity<>(objectMapper.writeValueAsString(checkout), headers);

        ResponseEntity<RentalAgreement> response = restTemplate.
                postForEntity(checkoutUrl, request, RentalAgreement.class);
        assertEquals(response.getBody(), ra);
    }

    @Test
    void getRentalAgreementForJackhammer() throws Exception {
        Checkout checkout = new Checkout("JAKD", 90, 10, LocalDate.of(2024, 7, 3));
        RentalAgreement ra = new RentalAgreement("JAKD", "Jackhammer", "DeWalt", 90, LocalDate.of(2024, 7, 3),
                LocalDate.of(2024, 10, 1), 2.99, 62, 185.38, 10, 18.54, 166.84);

        HttpEntity<String> request =
                new HttpEntity<>(objectMapper.writeValueAsString(checkout), headers);

        ResponseEntity<RentalAgreement> response = restTemplate.
                postForEntity(checkoutUrl, request, RentalAgreement.class);
        assertEquals(response.getBody(), ra);
    }

    @Test
    void checkoutErrorBadDateFormat() throws Exception {
        Map<String, Object> checkout = new HashMap<>();
        checkout.put("toolCode", "JAKD");
        checkout.put("rentalDays", 90);
        checkout.put("discountPercent", 10);
        checkout.put("checkoutDate", "7/3/24");
        ErrorInfo errorInfo = new ErrorInfo("Invalid date format, must be MM/DD/YYYY", 400);

        HttpEntity<String> request =
                new HttpEntity<>(objectMapper.writeValueAsString(checkout), headers);

        ResponseEntity<ErrorInfo> response = restTemplate.
                postForEntity(checkoutUrl, request, ErrorInfo.class);
        assertEquals(response.getBody(), errorInfo);
    }

    @Test
    void checkoutErrorInvalidDiscount() throws Exception {
        Checkout checkout = new Checkout("JAKD", 90, 101, LocalDate.of(2024, 7, 3));
        ErrorInfo errorInfo = new ErrorInfo("Discount must be between 0 and 100 inclusive", 400);

        HttpEntity<String> request =
                new HttpEntity<>(objectMapper.writeValueAsString(checkout), headers);

        ResponseEntity<ErrorInfo> response = restTemplate.
                postForEntity(checkoutUrl, request, ErrorInfo.class);
        assertEquals(response.getBody(), errorInfo);
    }

    @Test
    void checkoutErrorInvaliRentalLength() throws Exception {
        Checkout checkout = new Checkout("JAKD", 0, 4, LocalDate.of(2024, 7, 3));
        ErrorInfo errorInfo = new ErrorInfo("Rental days must be greater than 0", 400);

        HttpEntity<String> request =
                new HttpEntity<>(objectMapper.writeValueAsString(checkout), headers);

        ResponseEntity<ErrorInfo> response = restTemplate.
                postForEntity(checkoutUrl, request, ErrorInfo.class);
        assertEquals(response.getBody(), errorInfo);
    }

    @Test
    void checkoutMissingRequiredField() throws Exception {
        Checkout checkout = new Checkout("", 0, 4, LocalDate.of(2024, 7, 3));
        ErrorInfo errorInfo = new ErrorInfo("Missing required field - toolCode", 400);

        HttpEntity<String> request =
                new HttpEntity<>(objectMapper.writeValueAsString(checkout), headers);

        ResponseEntity<ErrorInfo> response = restTemplate.
                postForEntity(checkoutUrl, request, ErrorInfo.class);
        assertEquals(response.getBody(), errorInfo);
    }

    @Test
    void missingRequestBody() throws Exception {
        ErrorInfo errorInfo = new ErrorInfo("No request body", 400);

        HttpEntity<String> request =
                new HttpEntity<>(null, headers);

        ResponseEntity<ErrorInfo> response = restTemplate.
                postForEntity(checkoutUrl, request, ErrorInfo.class);
        assertEquals(response.getBody(), errorInfo);
    }

    @Test
    void noToolCodeFound() throws Exception {
        Checkout checkout = new Checkout("JA", 5, 4, LocalDate.of(2024, 7, 3));
        ErrorInfo errorInfo = new ErrorInfo("Tool code not found - JA", 404);

        HttpEntity<String> request =
                new HttpEntity<>(objectMapper.writeValueAsString(checkout), headers);

        ResponseEntity<ErrorInfo> response = restTemplate.
                postForEntity(checkoutUrl, request, ErrorInfo.class);
        assertEquals(response.getBody(), errorInfo);
    }
}
