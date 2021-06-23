package com.cashonline.examen.common.loan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PagingData {

    @JsonProperty("page")
    private long page;

    @JsonProperty("size")
    private long size;

    @JsonProperty("total")
    private long total;

}
