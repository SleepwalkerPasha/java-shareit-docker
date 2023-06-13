package ru.practicum.shareit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.exception.NotValidBookingRequestException;
import ru.practicum.shareit.exception.UnsupportedStatusException;

import java.util.Map;

@RestControllerAdvice(basePackageClasses = BookingController.class)
@Slf4j
public class ErrorHandler {

    @ExceptionHandler({UnsupportedStatusException.class, NotValidBookingRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequestException(final RuntimeException e) {
        log.error(e.getMessage());
        return Map.of("error", e.getMessage());
    }
}
