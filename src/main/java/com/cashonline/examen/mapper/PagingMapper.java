package com.cashonline.examen.mapper;

import com.cashonline.examen.common.loan.PagingData;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PagingMapper {

    default PagingData pageToPaggingData(Page<?> page) {
        PagingData response = new PagingData();
        response.setPage(page.getNumber() + 1);
        response.setTotal(page.getTotalElements());
        response.setSize(page.getSize());
        return response;
    }

}
