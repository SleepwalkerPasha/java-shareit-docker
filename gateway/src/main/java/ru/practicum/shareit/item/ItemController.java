package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.dto.BasicInfo;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Validated
public class ItemController {

    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestBody @Validated(BasicInfo.class) ItemDto item,
                                          @RequestHeader(name = "X-Sharer-User-Id") long userId) {
        return itemClient.addItem(item, userId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestBody @Validated ItemDto item,
                                             @PathVariable long itemId,
                                             @RequestHeader(name = "X-Sharer-User-Id") long userId) {
        return itemClient.updateItem(item, itemId, userId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@PathVariable long itemId, @RequestHeader(name = "X-Sharer-User-Id") long userId) {
        return itemClient.getItem(itemId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllItems(@RequestHeader(name = "X-Sharer-User-Id") long userId,
                                              @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                              @RequestParam(name = "size", defaultValue = "20") @Positive Integer size) {
        return itemClient.getAllItems(userId, from, size);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getItemsByDescription(@RequestParam(name = "text") String text,
                                                        @RequestHeader(name = "X-Sharer-User-Id") long userId,
                                                        @RequestParam(name = "from", defaultValue = "0")
                                                        @PositiveOrZero Integer from,
                                                        @RequestParam(name = "size", defaultValue = "20")
                                                        @Positive Integer size) {
        return itemClient.getItemsByDescription(text, userId, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addCommentToItem(@PathVariable long itemId,
                                                   @RequestHeader(name = "X-Sharer-User-Id") long userId,
                                                   @RequestBody @Validated CommentDto comment) {
        return itemClient.addCommentToItem(itemId, userId, comment);
    }
}