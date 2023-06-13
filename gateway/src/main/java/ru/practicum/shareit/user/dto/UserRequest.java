package ru.practicum.shareit.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotNull(groups = BasicInfo.class)
    String name;

    @Email(groups = {BasicInfo.class, AdvanceInfo.class})
    @NotNull(groups = BasicInfo.class)
    String email;

}
