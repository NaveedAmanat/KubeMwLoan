package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.dto.tab.MntrngChks;
import com.idev4.loans.service.MwMntrngChksAdcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class MwMntrngChksResource {

    private final Logger log = LoggerFactory.getLogger(MwMntrngChksResource.class);

    private final MwMntrngChksAdcService mwMntrngChksAdcService;

    public MwMntrngChksResource(MwMntrngChksAdcService mwMntrngChksAdcService) {
        this.mwMntrngChksAdcService = mwMntrngChksAdcService;
    }

    @PostMapping("/save-mntrng-chks")
    @Timed
    public ResponseEntity<MntrngChks> saveMntrngChks(@RequestBody MntrngChks dto) throws URISyntaxException {
        log.debug("REST request to save MwBizSect : {}", dto);
        return ResponseEntity.ok().body(mwMntrngChksAdcService.saveMntrngChks(dto));
    }

    // @GetMapping ( "/mw-biz-sect-prd/{prdSeq}" )
    // @Timed
    // public ResponseEntity< List > getBizSectPrd( @PathVariable Long prdSeq ) {
    // log.debug( "REST request to get MwBizSect : {}", prdSeq );
    // List< MwBizSect > mwBizSect = mwBizSectService.findAllByPrdSeq( prdSeq );
    // return ResponseEntity.ok().body( mwBizSect );
    // }

}
