package com.idev4.loans.dto.tab;

import java.util.List;

public class DashboardDto {

    public String trgtClnts;

    public String achvdClnts;

    public String totAmtDue;

    public String totAmtRcvd;

    public String tbCmpltd;

    public String rtndClnt;

    public String tat;

    public String newLoanSize;

    public String rptLoanSize;

    public String femalePart;

    public String ipClnt;

    public String ipAmt;

    public String startDate;

    public String endDate;

    public List<Long> ports;

    public List<Long> prds;

    public List<Long> grps;

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
