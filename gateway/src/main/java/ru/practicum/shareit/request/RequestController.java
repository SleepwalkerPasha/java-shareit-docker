package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Validated
public class RequestController {

    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> addRequest(@RequestHeader(name = "X-Sharer-User-Id") long userId,
                                             @Validated @RequestBody ItemRequestDto request) {
        return requestClient.addRequest(userId, request);
    }

    @GetMapping
    public ResponseEntity<Object> getAllRequestsByOwnerId(@RequestHeader(name = "X-Sharer-User-Id") long userId) {
        return requestClient.getAllRequestsByOwnerId(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(@RequestHeader(name = "X-Sharer-User-Id") long userId,
                                                 @RequestParam(name = "from", defaultValue = "0")
                                                 @PositiveOrZero Integer from,
                                                 @RequestParam(name = "size", defaultValue = "20")
                                                 @Positive Integer size) {
        return requestClient.getAllRequests(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestById(@RequestHeader(name = "X-Sharer-User-Id") long userId,
                                                 @PathVariable long requestId) {
        return requestClient.getRequestById(userId, requestId);
    }
}
