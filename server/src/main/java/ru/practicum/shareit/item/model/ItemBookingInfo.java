package ru.practicum.shareit.item.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemBookingInfo {
    Long id;

    String name;

    String description;

    Boolean available;

    BookingInfo lastBooking;

    BookingInfo nextBooking;

    List<Comment> comments = new ArrayList<>();
}
