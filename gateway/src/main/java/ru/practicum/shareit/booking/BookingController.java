package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.exception.NotValidBookingRequestException;
import ru.practicum.shareit.exception.UnsupportedStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {

    private final BookingClient bookingClient;

    @GetMapping
    public ResponseEntity<Object> getBookings(@RequestHeader("X-Sharer-User-Id") long userId,
                                              @RequestParam(name = "state", defaultValue = "all") String stateParam,
                                              @PositiveOrZero
                                              @RequestParam(name = "from", defaultValue = "0") Integer from,
                                              @Positive
                                              @RequestParam(name = "size", defaultValue = "10") Integer size) {
        BookingState state = BookingState.from(stateParam)
                .orElseThrow(() -> new UnsupportedStatusException(String.format("Unknown state: %s", stateParam)));
        return bookingClient.getBookings(userId, state, from, size);
    }

    @PostMapping
    public ResponseEntity<Object> bookItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                           @RequestBody @Valid BookItemRequestDto requestDto) {
        validateBookingRequest(requestDto);
        return bookingClient.bookItem(userId, requestDto);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable Long bookingId) {
        return bookingClient.getBooking(userId, bookingId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approveBooking(@RequestHeader(name = "X-Sharer-User-Id") long userId,
                                                 @PathVariable long bookingId,
                                                 @RequestParam(name = "approved") boolean approved) {
        return bookingClient.approveBooking(userId, bookingId, approved);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllBookingsOfUser(@RequestHeader(name = "X-Sharer-User-Id") long userId,
                                                       @RequestParam(name = "state", defaultValue = "all") String stateParam,
                                                       @RequestParam(name = "from", defaultValue = "0")
                                                       @PositiveOrZero Integer from,
                                                       @RequestParam(name = "size", defaultValue = "10")
                                                       @Positive Integer size) {
        BookingState state = BookingState.from(stateParam)
                .orElseThrow(() -> new UnsupportedStatusException(String.format("Unknown state: %s", stateParam)));
        return bookingClient.getBookingsByOwnerId(userId, state, from, size);
    }

    private void validateBookingRequest(BookItemRequestDto bookingRequest) {
        if (bookingRequest.getEnd().isBefore(bookingRequest.getStart()))
            throw new NotValidBookingRequestException("конец раньше старта бронирования");
        else if (bookingRequest.getStart().equals(bookingRequest.getEnd()))
            throw new NotValidBookingRequestException("конец равен началу бронирования");
        else if (bookingRequest.getStart().isBefore(LocalDateTime.now()))
            throw new NotValidBookingRequestException("старт бронирования в прошлом");
    }

    @ExceptionHandler({UnsupportedStatusException.class, NotValidBookingRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequestException(final RuntimeException e) {
        log.error(e.getMessage());
        return Map.of("error", e.getMessage());
    }
}
