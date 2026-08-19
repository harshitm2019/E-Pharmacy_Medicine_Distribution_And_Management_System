package com.harshit.pharmacy.medicine.controller;


import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.medicine.dto.MedicineResponse;
import com.harshit.pharmacy.medicine.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/medicines")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<Page<MedicineResponse>>> getAllActiveMedicinesByCategory(
            @PathVariable Integer categoryId,
            @ParameterObject Pageable pageable) {

        Page<MedicineResponse> medicineResponse =
                medicineService.getAllActiveMedicinesByCategory(categoryId,pageable);

        return ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.ALL_MEDICINES_FETCHED,medicineResponse)

        );
    }
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<MedicineResponse>>> searchMedicines(@RequestParam String keyword, @ParameterObject Pageable pageable) {

        Page<MedicineResponse> medicineResponse = medicineService.searchActiveMedicines(keyword, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.ALL_MEDICINES_FETCHED, medicineResponse)

        );

    }

}