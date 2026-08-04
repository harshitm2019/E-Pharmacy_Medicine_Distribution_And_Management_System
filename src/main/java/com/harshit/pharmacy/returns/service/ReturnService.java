package com.harshit.pharmacy.returns.service;

import com.harshit.pharmacy.returns.dto.CreateReturnRequest;
import com.harshit.pharmacy.returns.dto.ReturnResponse;
import com.harshit.pharmacy.returns.dto.UpdateReturnStatusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface ReturnService {

    ReturnResponse createReturn(CreateReturnRequest request);

    Page<ReturnResponse> getMyReturns(Pageable pageable);

    @Transactional(readOnly = true)
    Page<ReturnResponse> getReturnsByStatus(String status, Pageable pageable);

    ReturnResponse updateReturnStatus(Integer returnId, UpdateReturnStatusRequest request);

}
