package com.harshit.pharmacy.user.dto;

public record UserProfileResponse(


        String username,
        String email,
        String phoneNumber,
        String address,
        String city,
        String state,
        String pin


) {
}
