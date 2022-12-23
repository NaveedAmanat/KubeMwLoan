package com.idev4.loans.service;

import com.idev4.loans.domain.MwClntHlthInsrCard;
import com.idev4.loans.domain.MwLoanApp;
import com.idev4.loans.dto.ClntHeatlhInsuranceCardDto;
import com.idev4.loans.repository.MwClntHlthInsrCardRepository;
import com.idev4.loans.repository.MwLoanAppRepository;
import com.idev4.loans.web.rest.util.SequenceFinder;
import com.idev4.loans.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * Service Implementation for managing MwClntHlthInsr.
 */
@Service
@Transactional
public class MwClntHlthInsrCardService {

    public static String fixedValue = "010009";
    private final Logger log = LoggerFactory.getLogger(MwClntHlthInsrCardService.class);
    private final MwClntHlthInsrCardRepository mwClntHlthInsrCardRepository;
    private final MwLoanAppRepository mwLoanAppRepository;
    private final EntityManager em;
    private final DateFormat formatterDate = new SimpleDateFormat("dd/MMM/yyyy");

    public MwClntHlthInsrCardService(MwClntHlthInsrCardRepository mwClntHlthInsrCardRepository, EntityManager em,
                                     MwLoanAppRepository mwLoanAppRepository) {
        this.mwClntHlthInsrCardRepository = mwClntHlthInsrCardRepository;
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.em = em;
    }

    /**
     * Save a mwClntHlthInsr.
     *
     * @param mwClntHlthInsr the entity to save
     * @return the persisted entity
     */
    public MwClntHlthInsrCard save(MwClntHlthInsrCard mwClntHlthInsrCard) {
        log.debug("Request to save MwClntHlthInsr : {}", mwClntHlthInsrCard);
        return mwClntHlthInsrCardRepository.save(mwClntHlthInsrCard);
    }

    @Transactional
    public ClntHeatlhInsuranceCardDto createHealthCard(ClntHeatlhInsuranceCardDto dto, String currUsr) {

        Instant currIns = Instant.now();
        MwClntHlthInsrCard exCard = mwClntHlthInsrCardRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);

        // Added by Zohaib Asim - Dated 28-12-2020
        // CR: KSZB Claims - KM Product and Status 'ACTIVE' After Advance (1305)
        if ((dto.prdSeq == 10 || dto.prdSeq == 39 || dto.prdSeq == 40) && dto.loanAppSts.equals("703")) {
            return dto;
        }
        // End by Zohaib Asim

        if (exCard != null) {
            exCard.setCrntRecFlg(false);
            exCard.setLastUpdBy(currUsr);
            exCard.setLastUpdDt(currIns);
            exCard.setDelFlg(true);
            mwClntHlthInsrCardRepository.save(exCard);
        }
        // Instant fixedInstant = Instant.parse("2018-11-07T00:00:00.00Z");
        // Date fixedDt = Date.from(fixedInstant);
        // Date currDt = Date.from(currIns);
        // long diffInMillies = Math.abs(currDt.getTime() - fixedDt.getTime());
        // long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        // Calendar c = Calendar.getInstance();
        // c.setTime(currDt);
        // int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        //
        // String policyNumber = String.format("%011d", diff);
        //
        // List<MwClntHlthInsrCard> cards = mwClntHlthInsrCardRepository.findAllByCrntRecFlgOrderByClntHlthInsrCardSeqDesc(true);
        // int certificateNum = 1;
        // if(cards.size()>0) {
        // MwClntHlthInsrCard card = cards.get(0);
        // if(card.getCrtdDt() != null) {
        // Date recDt = Date.from(card.getCrtdDt());
        // long recDiffInMillies = Math.abs(currDt.getTime() - recDt.getTime());
        // long recDiff = TimeUnit.DAYS.convert(recDiffInMillies, TimeUnit.MILLISECONDS);
        // String prevCardNum = card.getCardNum();
        // String precCertificateNum = prevCardNum.substring(prevCardNum.length()-5);
        // certificateNum = Integer.parseInt(precCertificateNum);
        // if(recDiff>0) {
        // certificateNum++;
        // }
        // }
        // }
        //
        // String certificateNumber = String.format("%05d",certificateNum);
        // String cardNum = fixedValue+policyNumber+certificateNumber;


        // Added by Zohaib Asim - Dated 19/12/2020
        // Loan App Status for KM-Sale1 Card Generation
        String date = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
        Query q, hlth;
        String cardNum = "";
        String hlthFound = "";
        MwClntHlthInsrCard card = new MwClntHlthInsrCard();
        card.setClntHlthInsrCardSeq(SequenceFinder.findNextVal(Sequences.CLNT_HLTH_INSR_CARD_SEQ));
        card.setCardExpiryDt(dto.cardExpiryDate);

        // Added by Yousaf - 30-12-2021 - To Get Product

        hlth = em.createNativeQuery("SELECT COUNT (1)\n" +
                "  FROM MW_CLNT_HLTH_INSR hlth\n" +
                " WHERE     hlth.CRNT_REC_FLG = 1\n" +
                "       AND hlth.HLTH_INSR_PLAN_SEQ NOT IN (1223, 1263)\n" +
                "       AND hlth.LOAN_APP_SEQ = :loan_app_seq").setParameter("loan_app_seq", dto.loanAppSeq);
        hlthFound = hlth.getSingleResult().toString();

        if (!hlthFound.equals("0") || hlthFound != "0") {
            MwLoanApp mwLoanApp = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg(dto.loanAppSeq, true);

            // MODIFIED BY YOUSAF DATED : 31-OCT-2022 // ADD ANOTHER PARAMETER FOR KTK PRODUCT
            q = em.createNativeQuery("select fnc_HC_Generation(:prd, :clntseq)  from dual").setParameter("prd", mwLoanApp.prdSeq).setParameter("clntseq", mwLoanApp.clntSeq);
            cardNum = q.getSingleResult().toString();
            card.setCardNum(cardNum);
            // End by Zohaib Asim

            card.setCrntRecFlg(true);
            card.setCrtdBy(currUsr);
            card.setCrtdDt(currIns);
            card.setDelFlg(false);
            card.setEffStartDt(currIns);
            card.setLoanAppSeq(dto.loanAppSeq);
            card.setSyncFlg(true);
            mwClntHlthInsrCardRepository.save(card);
        }

        return dto;
    }
}
