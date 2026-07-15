package com.harshit.pharmacy.common.response;

public record ApiResponse<T>(

        boolean success,

        String message,

        T data

) {

     public static <T> ApiResponse<T> success(String message,T data){

         return new ApiResponse<T>(true,message,data);

     }

     public static <T> ApiResponse<T> failure(String message,T data){

         return new ApiResponse<T>(false,message,data);

     }



}