package com.nutritionix.authservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.Test;

class ErrorResponseDTOTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>
     * {@link ErrorResponseDTO#ErrorResponseDTO(Date, int, String, String, String)}
     *   <li>{@link ErrorResponseDTO#setError(String)}
     *   <li>{@link ErrorResponseDTO#setMessage(String)}
     *   <li>{@link ErrorResponseDTO#setPath(String)}
     *   <li>{@link ErrorResponseDTO#setStatus(int)}
     *   <li>{@link ErrorResponseDTO#setTimestamp(Date)}
     *   <li>{@link ErrorResponseDTO#getError()}
     *   <li>{@link ErrorResponseDTO#getMessage()}
     *   <li>{@link ErrorResponseDTO#getPath()}
     *   <li>{@link ErrorResponseDTO#getStatus()}
     *   <li>{@link ErrorResponseDTO#getTimestamp()}
     * </ul>
     */
    @Test
    void testConstructor() {
        ErrorResponseDTO actualErrorResponseDTO = new ErrorResponseDTO(
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), 1, "An error occurred",
                "Not all who wander are lost", "Path");
        actualErrorResponseDTO.setError("An error occurred");
        actualErrorResponseDTO.setMessage("Not all who wander are lost");
        actualErrorResponseDTO.setPath("Path");
        actualErrorResponseDTO.setStatus(1);
        Date timestamp = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        actualErrorResponseDTO.setTimestamp(timestamp);
        String actualError = actualErrorResponseDTO.getError();
        String actualMessage = actualErrorResponseDTO.getMessage();
        String actualPath = actualErrorResponseDTO.getPath();
        int actualStatus = actualErrorResponseDTO.getStatus();
        assertEquals("An error occurred", actualError);
        assertEquals("Not all who wander are lost", actualMessage);
        assertEquals("Path", actualPath);
        assertEquals(1, actualStatus);
        assertSame(timestamp, actualErrorResponseDTO.getTimestamp());
    }
}
