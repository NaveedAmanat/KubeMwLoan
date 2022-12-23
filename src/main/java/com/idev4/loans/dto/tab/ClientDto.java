package com.idev4.loans.dto.tab;

public class ClientDto {

    public ClntBasicInfoDto basic_info;

    public AddrDto client_address;

    public ClientCnicToken cnic_token;

    @Override
    public String toString() {
        return "ClientDto [basic_info=" + ((basic_info == null) ? "null" : basic_info.toString()) + ", client_address="
                + ((client_address == null) ? "null" : client_address.toString()) + "]";
    }

}
