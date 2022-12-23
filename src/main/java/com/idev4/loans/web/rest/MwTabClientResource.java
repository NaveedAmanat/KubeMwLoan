package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.dto.ValidationDto;
import com.idev4.loans.dto.tab.TabClientAppDto;
import com.idev4.loans.service.MwTabClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MwTabClientResource {

    private final Logger log = LoggerFactory.getLogger(MwTabClientResource.class);

    @Autowired
    private MwTabClientService mwTabClientService;

    @PostMapping("/generate_mfcib")
    public ResponseEntity<?> generateMfcib(@RequestBody TabClientAppDto dto) throws Exception {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();

        mwTabClientService.generateMfcib(dto, user);

        return ResponseEntity.ok().body(mwTabClientService.getloanDetail(String.valueOf(dto.loan_info.loan_app_seq)));
    }

    @GetMapping("/get-loan-detail/{loanAppSeq}")
    public ResponseEntity<?> generateMfcib(@PathVariable String loanAppSeq) throws Exception {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok().body(mwTabClientService.getloanDetail(loanAppSeq));
    }

    @PutMapping("/validate_mfcib_policy/{isActiveLoan}")
    public ResponseEntity<?> validatePolicy(@RequestBody TabClientAppDto tabAppDto, @PathVariable long isActiveLoan) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        String response = mwTabClientService.validatePolicy(tabAppDto, isActiveLoan, user);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/mw-tags-validation-for-clnt-nom-cob-automation")
    @Timed
    public ResponseEntity<Map> getClientHistoryDetail(@RequestBody ValidationDto dto) {

        log.debug("REST request to get MWTags : {}", dto.toString());
        Map<String, Object> resp = new HashMap<String, Object>();
        if (dto.cnicNum != null)
            resp.put("client", mwTabClientService.getUserValidationDetail(dto.cnicNum, true, dto.isBm, dto.eventName));
        if (dto.nomCnic != null)
            resp.put("nominee", mwTabClientService.getUserValidationDetail(dto.nomCnic, false, dto.isBm, dto.eventName));
        if (dto.cobCnic != null)
            resp.put("coborrower", mwTabClientService.getUserValidationDetail(dto.cobCnic, false, dto.isBm, dto.eventName));

        System.out.println("resp : " + resp);
        return ResponseEntity.ok().body(resp);
    }

    @PutMapping("/submit-complete-application-automation")
    @Timed
    public ResponseEntity<?> submitApplication(@RequestBody TabClientAppDto dto) throws URISyntaxException, ParseException {
        log.debug("REST request to update MWTags : {}", dto.toString());

        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return mwTabClientService.submitCompletedApplication(dto, user);
    }

    @PutMapping("/loan-app-doc-prvl-rjctn/{isActiveLoan}")
    @Timed
    public ResponseEntity<?> loanAppPrvlRjtn(@RequestBody TabClientAppDto tabAppDto, @PathVariable long isActiveLoan) throws URISyntaxException, ParseException {

        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        //mwTabClientService.loanAppAprvlRjtcn(tabAppDto, user);

        return ResponseEntity.ok().body(mwTabClientService.validatePolicy(tabAppDto, isActiveLoan, user));
    }
}
