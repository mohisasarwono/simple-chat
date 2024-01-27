package com.learn.spring.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestedFriendResponse {
    Long id;
    Long userId;
    String profileName;
}
