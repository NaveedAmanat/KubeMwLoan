package com.idev4.loans.dto;

import java.util.List;

public class BusinessAprsalDto {

    public Long clientSeq;
    public Long loanAppSeq;
    public Long sectorKey;
    public Long activityKey;
    public String businessDetailStr;
    public Long personRunningBusinessKey;
    public Long businessOwnerShip;
    public Integer yearsInBusiness;
    public Integer monthsInBusiness;

    public Boolean isbizAddrSAC;
    public Long bizPropertyOwnKey;
    public String bizPhoneNum;


    public Long bizAddressSeq;
    public Long bizSeq;
    public String houseNum;
    public String sreet_area;
    public Long community;
    public String village;
    public String otherDetails;
    public Long tehsil;
    public Long district;
    public Long city;
    public Long uc;
    public Double lat;
    public Double lon;
    public Integer yearsOfResidence;
    public Integer mnthsOfResidence;
    public Boolean isPermAddress;

    public Long incomeHdrSeq;
    public Double maxMonthSale;
    public Integer maxSaleMonth;
    public Double minMonthSale;
    public Integer minSaleMonth;


    public Double expAmount;
    public Long expCategoryKey;
    public Long expTypKey;

    public Long bizAprslSeq;

    public Long country;
    public String countryName;
    public Long province;
    public String provinceName;
    public String districtName;
    public String tehsilName;
    public String ucName;
    public String cityName;

    public Long formSeq;

    public List<IncomeDtlDto> primaryIncome;
    public List<IncomeDtlDto> secondaryIncome;
    public List<BizExpDto> businessExpense;
    public List<BizExpDto> householdExpense;
    public List<BizAprslExtngLvstkDto> extngLvStk;
    public List<BizAprslEstLvstkFinDto> estLvStk;

    public AddressDto businessAddress;

    // added by yousaf dated : 10062022
    public String remarks;
}
