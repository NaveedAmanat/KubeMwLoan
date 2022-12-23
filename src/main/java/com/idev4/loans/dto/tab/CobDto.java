package com.idev4.loans.dto.tab;

public class CobDto {

    public ClntRelDto basic_info;

    public AddrDto coborrower_address;

    public AddrDto client_relative_address;

    @Override
    public String toString() {
        return "CobDto [basic_info=" + ((basic_info == null) ? "null" : basic_info.toString()) + ", coborrower_address="
                + ((coborrower_address == null) ? "null" : coborrower_address.toString()) + "]";
    }

}
