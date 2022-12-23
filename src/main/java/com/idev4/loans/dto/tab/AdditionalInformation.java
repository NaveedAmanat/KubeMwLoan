package com.idev4.loans.dto.tab;

import java.io.Serializable;

/**
 * @Added, Naveed
 * @Date, 17-08-2022
 * @Description, SCR - CIRPLUS TASDEEQ
 */

public class AdditionalInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    public long id;
    public String CNIC;
    public String relation;
    public String name;


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
