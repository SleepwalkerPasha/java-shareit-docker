package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    @NotNull(groups = BasicInfo.class)
    @NotBlank(groups = BasicInfo.class)
    String name;

    @NotNull(groups = BasicInfo.class)
    @NotBlank(groups = BasicInfo.class)
    String description;

    @NotNull(groups = BasicInfo.class)
    Boolean available;

    Long requestId;
}
