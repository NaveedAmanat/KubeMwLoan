package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.dto.SchoolAppraisalDto;
import com.idev4.loans.service.MwSchAprslService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class MwSchAprslResource {

    private final MwSchAprslService mwSchAprslService;

    public MwSchAprslResource(MwSchAprslService mwSchAprslService) {
        this.mwSchAprslService = mwSchAprslService;
    }

    @PostMapping("/add-new-sch-aprsl")
    @Timed
    public ResponseEntity<Map> createMwSchAprsl(@RequestBody SchoolAppraisalDto schoolAppraisalDto) throws URISyntaxException {

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

//    	if(schoolAppraisalDto.getSchNm() == null || schoolAppraisalDto.getSchNm().isEmpty()) {
//        	resp.put("error", "School Name is missing !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
//
//    	if(schoolAppraisalDto.getSchRegdFlg()<0) {
//        	resp.put("error", "Registered Flag is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//
//    	if(schoolAppraisalDto.getPefSptFlg()<0) {
//        	resp.put("error", "PEF Flag is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//    	if(schoolAppraisalDto.getSchArea()<0) {
//        	resp.put("error", "School Area is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//    	if(schoolAppraisalDto.getSchAge()<0) {
//        	resp.put("error", "School Age is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//    	if(schoolAppraisalDto.getSchOwnTypKey()<0) {
//        	resp.put("error", "School Owner type key is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getRelWthOwnKey()<0) {
//        	resp.put("error", "Relation with Owner type key is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getSchPpalKey()<0) {
//        	resp.put("error", "School principle key is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getBldngOwnKey()<0) {
//        	resp.put("error", "Building Owner key is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getSchTypKey()<0) {
//        	resp.put("error", "School Type key is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getSchLvlKey()<0) {
//        	resp.put("error", "School Level key is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getSchMedmKey()<0) {
//        	resp.put("error", "School Medium key is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getSchAreaUntKey()<0) {
//        	resp.put("error", "School Area Unit key is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getLoanAppSeq()<0) {
//        	resp.put("error", "Loan App Seq is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().regSeq<0) {
//        	resp.put("error", "Region Seq is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().brnchSeq<0) {
//        	resp.put("error", "Branch Seq is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().areaSeq<0) {
//        	resp.put("error", "Area Seq is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().houseNum == null || schoolAppraisalDto.getAddressDto().houseNum.isEmpty()) {
//        	resp.put("error", "House Number is Missing!!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().sreet_area == null || schoolAppraisalDto.getAddressDto().sreet_area.isEmpty()) {
//        	resp.put("error", "Steet is missing !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().community<0) {
//        	resp.put("error", "Communit Seq is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().village == null || schoolAppraisalDto.getAddressDto().village.isEmpty()) {
//        	resp.put("error", "Village is missing !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().otherDetails == null || schoolAppraisalDto.getAddressDto().otherDetails.isEmpty()) {
//        	resp.put("error", "Other details is missing !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().city<0) {
//        	resp.put("error", "City Seq is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().uc == null || schoolAppraisalDto.getAddressDto().uc.isEmpty()) {
//        	resp.put("error", "UC is missing !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getTotFemTchrs()<0) {
//        	resp.put("error", "Total Female Teachers is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
//       	if(schoolAppraisalDto.getTotMaleTchrs()<0) {
//        	resp.put("error", "Total Male Teachers is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
//       	if(schoolAppraisalDto.getLastYrDrop()<0) {
//        	resp.put("error", "Last Year Drop is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
//       	if(schoolAppraisalDto.getSchoolGradeDtos()==null) {
//        	resp.put("error", "Grades List is Missing !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
//       	if(schoolAppraisalDto.getSchoolQualityCheckDtos()==null) {
//        	resp.put("error", "Quality Check List is Missing !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }


        SchoolAppraisalDto dto = mwSchAprslService.addNewSchAprsl(schoolAppraisalDto, currUser);
        Map<String, SchoolAppraisalDto> respData = new HashMap<String, SchoolAppraisalDto>();
        respData.put("SchoolAppraisalDto", dto);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-sch-aprsl/{seq}")
    @Timed
    public ResponseEntity getSchoolAppraisal(@PathVariable Long seq) {

        SchoolAppraisalDto schoolAppraisalDto = mwSchAprslService.getSchoolAppraisal(seq);
        return ResponseEntity.ok().body(schoolAppraisalDto);
    }

    @GetMapping("/mw-sch-aprsl")
    @Timed
    public ResponseEntity<List> getAllSchoolAppraisals() {

        List<SchoolAppraisalDto> schoolAppraisalDtos = mwSchAprslService.getAllSchoolAppraisal();
        return ResponseEntity.ok().body(schoolAppraisalDtos);
    }

    @DeleteMapping("/mw-sch-aprsl/{seq}")
    @Timed
    public ResponseEntity<Map> deleteMwSchoolAppraisal(@PathVariable Long seq) {

        Map<String, String> resp = new HashMap<String, String>();
        if (mwSchAprslService.delete(seq)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }
    }


    @PutMapping("/update-sch-aprsl")
    @Timed
    public ResponseEntity updateMwSchAprsl(@RequestBody SchoolAppraisalDto schoolAppraisalDto) throws URISyntaxException {

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (schoolAppraisalDto.getSchAprslSeq() <= 0) {
            resp.put("error", "School Appraisal Seq is missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

//    	if(schoolAppraisalDto.getSchNm() == null || schoolAppraisalDto.getSchNm().isEmpty()) {
//        	resp.put("error", "School Name is missing !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
//
//    	if(schoolAppraisalDto.getSchRegdFlg()<0) {
//        	resp.put("error", "Registered Flag is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//
//    	if(schoolAppraisalDto.getPefSptFlg()<0) {
//        	resp.put("error", "PEF Flag is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//    	if(schoolAppraisalDto.getSchArea()<0) {
//        	resp.put("error", "School Area is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//    	if(schoolAppraisalDto.getSchAge()<0) {
//        	resp.put("error", "School Age is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//    	if(schoolAppraisalDto.getSchOwnTypKey()<0) {
//        	resp.put("error", "School Owner type key is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getRelWthOwnKey()<0) {
//        	resp.put("error", "Relation with Owner type key is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getSchPpalKey()<0) {
//        	resp.put("error", "School principle key is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getBldngOwnKey()<0) {
//        	resp.put("error", "Building Owner key is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getSchTypKey()<0) {
//        	resp.put("error", "School Type key is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getSchLvlKey()<0) {
//        	resp.put("error", "School Level key is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getSchMedmKey()<0) {
//        	resp.put("error", "School Medium key is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getSchAreaUntKey()<0) {
//        	resp.put("error", "School Area Unit key is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getLoanAppSeq()<0) {
//        	resp.put("error", "Loan App Seq is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().regSeq<0) {
//        	resp.put("error", "Region Seq is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().brnchSeq<0) {
//        	resp.put("error", "Branch Seq is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().areaSeq<0) {
//        	resp.put("error", "Area Seq is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().houseNum == null || schoolAppraisalDto.getAddressDto().houseNum.isEmpty()) {
//        	resp.put("error", "House Number is Missing!!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().sreet_area == null || schoolAppraisalDto.getAddressDto().sreet_area.isEmpty()) {
//        	resp.put("error", "Steet is missing !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().community<0) {
//        	resp.put("error", "Communit Seq is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().village == null || schoolAppraisalDto.getAddressDto().village.isEmpty()) {
//        	resp.put("error", "Village is missing !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().otherDetails == null || schoolAppraisalDto.getAddressDto().otherDetails.isEmpty()) {
//        	resp.put("error", "Other details is missing !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().city<0) {
//        	resp.put("error", "City Seq is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getAddressDto().uc == null || schoolAppraisalDto.getAddressDto().uc.isEmpty()) {
//        	resp.put("error", "UC is missing !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }	
//       	if(schoolAppraisalDto.getTotFemTchrs()<0) {
//        	resp.put("error", "Total Female Teachers is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
//       	if(schoolAppraisalDto.getTotMaleTchrs()<0) {
//        	resp.put("error", "Total Male Teachers is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
//       	if(schoolAppraisalDto.getLastYrDrop()<0) {
//        	resp.put("error", "Last Year Drop is not correct !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
//       	if(schoolAppraisalDto.getSchoolGradeDtos()==null) {
//        	resp.put("error", "Grades List is Missing !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
//       	if(schoolAppraisalDto.getSchoolQualityCheckDtos()==null) {
//        	resp.put("error", "Quality Check List is Missing !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }

        SchoolAppraisalDto schoolAppraisal = mwSchAprslService.updateSchoolAppraisal(schoolAppraisalDto, currUser);
        Map<String, Object> respData = new HashMap<>();
        respData.put("SchoolAppraisalDto", schoolAppraisal);
        return ResponseEntity.ok().body(respData);
    }
}
