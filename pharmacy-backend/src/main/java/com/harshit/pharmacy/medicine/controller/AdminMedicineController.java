package com.harshit.pharmacy.medicine.controller;


import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.common.swagger.annotations.medicine.*;
import com.harshit.pharmacy.medicine.dto.MedicineRequest;
import com.harshit.pharmacy.medicine.dto.MedicineResponse;
import com.harshit.pharmacy.medicine.dto.MedicineStatusRequest;
import com.harshit.pharmacy.medicine.enums.MedicineStatus;
import com.harshit.pharmacy.medicine.service.MedicineService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Admin Medicine Management",
        description = "APIs for managing medicines. Accessible only to administrators."
)
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/medicines")
public class AdminMedicineController {


    private final MedicineService medicineService;

    @CreateMedicine
    @PostMapping
    public ResponseEntity<ApiResponse<MedicineResponse>> createMedicine(@Valid @RequestBody MedicineRequest request) {

        MedicineResponse medicineResponse =  medicineService.createMedicine(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(

                ApiResponse.success(SuccessMessages.MEDICINE_CREATED, medicineResponse)

        );

    }

    @UpdateMedicine
    @PutMapping("/{medicineId}")
    public ResponseEntity<ApiResponse<MedicineResponse>> updateMedicine(@PathVariable Integer medicineId,
            @Valid @RequestBody MedicineRequest request) {

        MedicineResponse medicineResponse =  medicineService.updateMedicine(medicineId, request);

        return ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.MEDICINE_UPDATED, medicineResponse)

        );
    }

    @SearchMedicine
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<MedicineResponse>>> searchMedicines(
            @RequestParam String keyword,
            @ParameterObject Pageable pageable) {

        Page<MedicineResponse> medicineResponse = medicineService.searchMedicines(keyword, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.ALL_MEDICINES_FETCHED, medicineResponse)

        );

    }

    @GetAllMedicines
    @GetMapping
    public ResponseEntity<ApiResponse<Page<MedicineResponse>>> getAllMedicines(
            @ParameterObject Pageable pageable) {

        Page<MedicineResponse> response = medicineService.getAllMedicines(pageable);

        return  ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.ALL_MEDICINES_FETCHED, response)

        );

    }

    @GetMedicineById
    @GetMapping("/{medicineId}")
    public ResponseEntity<ApiResponse<MedicineResponse>> getMedicineById(
            @PathVariable Integer medicineId) {

        MedicineResponse medicineResponse = medicineService.getMedicineById(medicineId);

        return ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.MEDICINE_FETCHED, medicineResponse)

        );

    }

    @UpdateMedicineStatus
    @PatchMapping("/status")
    public ResponseEntity<ApiResponse<List<MedicineResponse>>> updateStatus(@Valid @RequestBody MedicineStatusRequest request) {


          List<MedicineResponse> medicineResponse = medicineService.updateStatus(request);

          return ResponseEntity.status(HttpStatus.OK).body(

                  ApiResponse.success(SuccessMessages.MEDICINE_STATUS_UPDATED, medicineResponse)

          );
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<Page<MedicineResponse>>> filterMedicines(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String status,
            @ParameterObject Pageable pageable
    ) {


        MedicineStatus medicineStatus = status == null ? null : MedicineStatus.valueOf(status);

        Page<MedicineResponse> response = medicineService.filterMedicines(
                        categoryId,
                        medicineStatus,
                        pageable);

        return ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.ALL_MEDICINES_FETCHED, response)
        );
    }


}
