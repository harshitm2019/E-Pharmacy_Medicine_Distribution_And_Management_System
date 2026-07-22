package com.harshit.pharmacy.medicine.controller;


import com.harshit.pharmacy.common.constants.SuccessMessages;
import com.harshit.pharmacy.common.response.ApiResponse;
import com.harshit.pharmacy.common.swagger.annotations.medicine.GetActiveMedicineById;
import com.harshit.pharmacy.common.swagger.annotations.medicine.GetAllActiveMedicines;
import com.harshit.pharmacy.common.swagger.annotations.medicine.SearchActiveMedicines;
import com.harshit.pharmacy.medicine.dto.MedicineResponse;
import com.harshit.pharmacy.medicine.service.MedicineService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "Medicine",
        description = "Public APIs for browsing medicines."
)
@RestController
@RequestMapping("/api/v1/medicines")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @GetAllActiveMedicines
    @GetMapping
    public ResponseEntity<ApiResponse<Page<MedicineResponse>>> getAllMedicines(
            @ParameterObject Pageable pageable) {

        Page<MedicineResponse> medicineResponse = medicineService.getAllActiveMedicines(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.ALL_MEDICINES_FETCHED,medicineResponse)

        );

    }

    @GetActiveMedicineById
    @GetMapping("/{medicineId}")
    public ResponseEntity<ApiResponse<MedicineResponse>> getMedicineById(@PathVariable Integer medicineId) {

        MedicineResponse medicineResponse = medicineService.getActiveMedicineById(medicineId);

        return ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.MEDICINE_FETCHED, medicineResponse)

        );

    }

    @SearchActiveMedicines
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<MedicineResponse>>> searchMedicines(@RequestParam String keyword, @ParameterObject Pageable pageable) {

        Page<MedicineResponse> medicineResponse = medicineService.searchActiveMedicines(keyword, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(

                ApiResponse.success(SuccessMessages.ALL_MEDICINES_FETCHED, medicineResponse)

        );

    }



}
