package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.dto.SchoolInformationDto;
import com.idev4.loans.service.MwSchAstsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class MwSchAstsResource {

    private final MwSchAstsService mwSchAstsService;

    public MwSchAstsResource(MwSchAstsService mwSchAstsService) {
        this.mwSchAstsService = mwSchAstsService;
    }

    @PostMapping("/add-new-sch-asts")
    @Timed
    public ResponseEntity<Map> createMwSchAprsl(@RequestBody SchoolInformationDto dto) throws URISyntaxException {

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        SchoolInformationDto resdto = mwSchAstsService.addNewSchAsts(dto, currUser);
        Map<String, SchoolInformationDto> respData = new HashMap<String, SchoolInformationDto>();
        respData.put("schoolInformationDto", resdto);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-sch-asts/{seq}")
    @Timed
    public ResponseEntity getSchoolInformation(@PathVariable Long seq) {

        SchoolInformationDto schoolAppraisalDto = mwSchAstsService.getSchoolAsts(seq);
        return ResponseEntity.ok().body(schoolAppraisalDto);
    }

//    @DeleteMapping("/mw-sch-aprsl/{seq}")
//    @Timed
//    public ResponseEntity<Map> deleteMwSchoolAppraisal(@PathVariable Long seq) {
//
//    	Map<String, String> resp = new HashMap<String,String>();
//        if(mwSchAprslService.delete(seq)){
//        	resp.put("data","Deleted Successfully !!");
//        	return ResponseEntity.ok().body(resp);
//        }
//        else {
//        	resp.put("error","Delete Child First !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }        
//    }


    @PutMapping("/update-sch-asts")
    @Timed
    public ResponseEntity updateMwSchAprsl(@RequestBody SchoolInformationDto dto) throws URISyntaxException {

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (dto.loanAppSeq <= 0) {
            resp.put("error", "LoanAppSeq Seq is missing !!");
            return ResponseEntity.badRequest().body(resp);
        }


        SchoolInformationDto schoolInformation = mwSchAstsService.updateSchoolAsts(dto, currUser);
        Map<String, Object> respData = new HashMap<>();
        respData.put("schoolInformationDto", schoolInformation);
        return ResponseEntity.ok().body(respData);
    }
}
