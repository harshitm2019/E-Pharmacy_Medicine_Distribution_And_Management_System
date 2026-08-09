package com.harshit.pharmacy.returns.controller;

import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.returns.dto.ReturnResponse;
import com.harshit.pharmacy.returns.dto.UpdateReturnStatusRequest;
import com.harshit.pharmacy.returns.service.ReturnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/returns")
@RequiredArgsConstructor
public class AdminReturnController {

    private final ReturnService returnService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ReturnResponse>>> getReturnsByStatus(
            @RequestParam String status,
            @ParameterObject
            @PageableDefault(sort = "returnDate", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ReturnResponse> response = returnService.getReturnsByStatus(status, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.RETURNS_FETCHED_BY_STATUS, response)
        );
    }

    @PatchMapping("/{returnId}/status")
    public ResponseEntity<ApiResponse<ReturnResponse>> updateReturnStatus(
            @PathVariable Integer returnId,
            @Valid @RequestBody UpdateReturnStatusRequest request) {

        ReturnResponse response = returnService.updateReturnStatus(returnId, request);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(SuccessMessages.RETURN_STATUS_UPDATED, response)
        );
    }
}