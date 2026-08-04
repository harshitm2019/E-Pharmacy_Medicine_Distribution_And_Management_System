package com.harshit.pharmacy.returns.controller;

import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.returns.dto.CreateReturnRequest;
import com.harshit.pharmacy.returns.dto.ReturnResponse;
import com.harshit.pharmacy.returns.service.ReturnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/returns")
@RequiredArgsConstructor
public class ReturnController {

    private final ReturnService returnService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReturnResponse>> createReturn(
            @Valid @RequestBody CreateReturnRequest request) {

        ReturnResponse response = returnService.createReturn(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(SuccessMessages.RETURN_SUBMITTED, response)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ReturnResponse>>> getMyReturns(
            @PageableDefault(sort = "returnDate") Pageable pageable) {

        Page<ReturnResponse> response = returnService.getMyReturns(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.RETURNS_FETCHED, response)
        );
    }
}