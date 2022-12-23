package com.idev4.loans.dto.tab;

import java.util.List;

public class MntrngChks {

    public List<MntrngChksAdcDto> mwMntrngChksAdc;

    public List<MntrngChksAdcQstnrDto> mwMntrngChksAdcQstnr;

    @Override
    public String toString() {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
