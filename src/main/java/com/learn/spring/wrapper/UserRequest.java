package com.learn.spring.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotNull
    @NotBlank
    @JsonProperty("first_name")
    private String firstName;
    @NotNull
    @NotBlank
    @JsonProperty("last_name")
    private String lastName;
    @NotNull
    @NotBlank
    @JsonProperty("profile_name")
    private String profileName;
    @NotNull
    @NotBlank
    private String email;
    private String phone;
    @NotNull
    @NotBlank
    private String password;
}
