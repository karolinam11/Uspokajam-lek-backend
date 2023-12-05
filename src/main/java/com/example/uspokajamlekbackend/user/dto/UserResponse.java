package com.example.uspokajamlekbackend.user.dto;

import com.example.uspokajamlekbackend.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserResponse {
    private User user;
    private String token;
}
