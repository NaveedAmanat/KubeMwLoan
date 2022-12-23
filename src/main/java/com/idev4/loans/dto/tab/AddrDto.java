package com.idev4.loans.dto.tab;

public class AddrDto {

    public AddressDto address;

    public AddrRelDto address_rel;

    public ClientPermAddressDto address_perm_rel;

    @Override
    public String toString() {
        return "AddrDto [address=" + ((address == null) ? "null" : address.toString()) + ", address_rel="
                + ((address_rel == null) ? "null" : address_rel.toString()) + "]";
    }

}