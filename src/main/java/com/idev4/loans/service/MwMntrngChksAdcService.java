package com.idev4.loans.service;

import com.idev4.loans.domain.MwMntrngChksAdc;
import com.idev4.loans.domain.MwMntrngChksAdcQstnr;
import com.idev4.loans.dto.tab.MntrngChks;
import com.idev4.loans.repository.MwMntrngChksAdcQstnrRepository;
import com.idev4.loans.repository.MwMntrngChksAdcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;

@Service
@Transactional
public class MwMntrngChksAdcService {

    private final Logger log = LoggerFactory.getLogger(MwMntrngChksAdcService.class);

    private final MwMntrngChksAdcRepository mwMntrngChksAdcRepository;

    private final MwMntrngChksAdcQstnrRepository mwMntrngChksAdcQstnrRepository;

    private final DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    private final DateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");

    public MwMntrngChksAdcService(MwMntrngChksAdcRepository mwMntrngChksAdcRepository,
                                  MwMntrngChksAdcQstnrRepository mwMntrngChksAdcQstnrRepository) {
        this.mwMntrngChksAdcRepository = mwMntrngChksAdcRepository;
        this.mwMntrngChksAdcQstnrRepository = mwMntrngChksAdcQstnrRepository;
    }

    public MntrngChks saveMntrngChks(MntrngChks dto) {
        dto.mwMntrngChksAdc.forEach(chk -> {
            MwMntrngChksAdc exAdc = mwMntrngChksAdcRepository.findOneByMntrngChksSeqAndCrtdByAndCrntRecFlg(chk.mntrng_chks_seq,
                    SecurityContextHolder.getContext().getAuthentication().getName(), true);
            if (exAdc != null) {
                exAdc.setCrntRecFlg(false);
                exAdc.setDelFlg(true);
                exAdc.setLastUpdDt(Instant.now());
                exAdc.setEffEndDt(Instant.now());
                exAdc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                mwMntrngChksAdcRepository.save(exAdc);
            }
            MwMntrngChksAdc adc = chk.DtoToDomain(formatter);
            mwMntrngChksAdcRepository.save(adc);
        });
        dto.mwMntrngChksAdcQstnr.forEach(chk -> {
            MwMntrngChksAdcQstnr exAdc = mwMntrngChksAdcQstnrRepository
                    .findOneByMntrngChksAdcQstnrSeqAndCrntRecFlg(chk.mntrng_chks_adc_qstnr_seq, true);
            if (exAdc != null) {
                exAdc.setCrntRecFlg(false);
                exAdc.setDelFlg(true);
                exAdc.setLastUpdDt(Instant.now());
                exAdc.setEffEndDt(Instant.now());
                exAdc.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
                mwMntrngChksAdcQstnrRepository.save(exAdc);
            }
            MwMntrngChksAdcQstnr qstnr = chk.DtoToDomain(formatter);
            mwMntrngChksAdcQstnrRepository.save(qstnr);
        });
        return dto;
    }
}
