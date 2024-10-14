package com.csse.healthSphere.util;

public class Validator {
    /**
     * Validates whether the given email address is in a valid format.
     * Example usage:
     * <pre>{@code
     * boolean isValid = validator.isValidEmail("test@example.com");
     * System.out.println(isValid);  // Output: true
     * }</pre>
     *
     * @param email The email address to be validated.
     * @return {@code true} if the email address is valid; {@code false} otherwise.
     */
    private boolean isValidEmail(String email) {
        // email validation logic here
        return email != null && email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    /**
     * Validates whether the given phone number contains only digits and is not null.
     * Example usage:
     * <pre>{@code
     * boolean isValid = validator.isValidPhoneNumber("1234567890");
     * System.out.println(isValid);  // Output: true
     * }</pre>
     *
     * @param phoneNumber The phone number to be validated.
     * @return {@code true} if the phone number is valid; {@code false} otherwise.
     */
    private boolean isValidPhoneNumber(String phoneNumber) {
        // Your phone number validation logic here
        return phoneNumber != null && phoneNumber.matches("^[0-9]+$");
    }
}
