package com.idev4.loans.service;

import com.idev4.loans.domain.MwAddr;
import com.idev4.loans.domain.MwAddrRel;
import com.idev4.loans.dto.AddressCombinationDto;
import com.idev4.loans.dto.AddressDto;
import com.idev4.loans.repository.MwAddrRelRepository;
import com.idev4.loans.repository.MwAddrRepository;
import com.idev4.loans.repository.MwClntRepository;
import com.idev4.loans.repository.MwLoanAppRepository;
import com.idev4.loans.web.rest.util.Common;
import com.idev4.loans.web.rest.util.Queries;
import com.idev4.loans.web.rest.util.TableNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing MwAddr.
 */
@Service
@Transactional
public class MwAddrService {

    private final Logger log = LoggerFactory.getLogger(MwAddrService.class);

    private final MwAddrRepository mwAddrRepository;

    private final MwAddrRelRepository mwAddrRelRepository;

    private final MwAddrRelService mwAddrRelService;

    private final MwLoanAppRepository mwLoanAppRepository;

    private final MwClntRepository mwClntRepository;

    private final EntityManager em;

    public MwAddrService(MwAddrRepository mwAddrRepository, MwAddrRelService mwAddrRelService, EntityManager em,
                         MwAddrRelRepository mwAddrRelRepository, MwLoanAppRepository mwLoanAppRepository, MwClntRepository mwClntRepository) {
        this.mwAddrRepository = mwAddrRepository;
        this.mwAddrRelService = mwAddrRelService;
        this.em = em;
        this.mwAddrRelRepository = mwAddrRelRepository;
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.mwClntRepository = mwClntRepository;
    }

    /**
     * Save a mwAddr.
     *
     * @param mwAddr the entity to save
     * @return the persisted entity
     */
    public MwAddr save(MwAddr mwAddr) {
        log.debug("Request to save MwAddr : {}", mwAddr);
        return mwAddrRepository.save(mwAddr);
    }

    /**
     * Get all the mwAddrs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwAddr> findAll(Pageable pageable) {
        log.debug("Request to get all MwAddrs");
        return mwAddrRepository.findAll(pageable);
    }

    /**
     * Get one mwAddr by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwAddr findOne(Long id) {
        log.debug("Request to get MwAddr : {}", id);
        return mwAddrRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public MwAddr findOneByAddrSeq(Long id) {
        log.debug("Request to get MwAddr : {}", id);
        return mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(id, true);
    }

    public void deleteByEntity(MwAddr mwAddr) {
        log.debug("Request to delete MwAddr : {}", mwAddr.getAddrSeq());
        mwAddr.setCrntRecFlg(false);
        mwAddr.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
        mwAddr.setDelFlg(true);
        mwAddr.setLastUpdDt(Instant.now());
        mwAddr.setEffEndDt(Instant.now());
        mwAddrRepository.save(mwAddr);
    }

    public List findAddressOptions() {
        Query q = em.createNativeQuery(Queries.addressCombs);

        List<Object[]> cityCombinations = q.getResultList();
        List<AddressCombinationDto> combs = new ArrayList<AddressCombinationDto>();

        for (Object[] comb : cityCombinations) {

            AddressCombinationDto dto = new AddressCombinationDto();
            dto.country = Long.valueOf(comb[0].toString());
            dto.countryName = comb[1].toString();
            dto.province = Long.valueOf(comb[2].toString());
            dto.provinceName = comb[3].toString();
            dto.district = Long.valueOf(comb[4].toString());
            dto.districtName = comb[5].toString();
            dto.tehsil = Long.valueOf(comb[6].toString());
            dto.tehsilName = comb[7].toString();
            dto.uc = Long.valueOf(comb[8].toString());
            dto.ucName = (comb[9] == null ? "" : comb[9].toString()) + " " + (comb[10] == null ? "" : comb[10].toString());
            dto.city = Long.valueOf(comb[11].toString());
            dto.cityName = comb[12].toString();
            combs.add(dto);
        }

        log.debug("Result set is: {}", q.getMaxResults());
        // return q.getResultList();
        return combs;
    }

    public List findAddressOptions(Long id) {
        Query q = em.createNativeQuery(Queries.addressCombsForPort + id);

        List<Object[]> cityCombinations = q.getResultList();
        List<AddressCombinationDto> combs = new ArrayList<AddressCombinationDto>();

        for (Object[] comb : cityCombinations) {

            AddressCombinationDto dto = new AddressCombinationDto();
            dto.country = Long.valueOf(comb[0].toString());
            dto.countryName = comb[1].toString();
            dto.province = Long.valueOf(comb[2].toString());
            dto.provinceName = comb[3].toString();
            dto.district = Long.valueOf(comb[4].toString());
            dto.districtName = comb[5].toString();
            dto.tehsil = Long.valueOf(comb[6].toString());
            dto.tehsilName = comb[7].toString();
            dto.uc = Long.valueOf(comb[8].toString());
            dto.ucName = comb[9].toString() + " " + comb[10].toString();
            dto.city = Long.valueOf(comb[11].toString());
            dto.cityName = comb[12].toString();
            combs.add(dto);
        }

        log.debug("Result set is: {}", q.getMaxResults());
        // return q.getResultList();
        return combs;
    }

    @Transactional
    public long saveAddress(AddressDto dto, String curUser, String entity) {
        MwAddrRel rel = null;
        long entitySeq = 0;
        long addrSeq = 0;
        if (entity.equals("Client")) {
            rel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(dto.clientSeq, Common.clntAddress, true);
            entitySeq = dto.clientSeq;
            addrSeq = Long.parseLong((Common.GenerateTableSequence(dto.cnicNum + "", TableNames.MW_ADDR, dto.cycleNum) + "")
                    .concat(Common.Client + ""));
        } else if (entity.equals("Nominee")) {
            rel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(dto.clientRelSeq, "Nominee", true);
            entitySeq = dto.clientRelSeq;
            addrSeq = Long.parseLong((Common.GenerateTableSequence(dto.cnicNum + "", TableNames.MW_ADDR, dto.cycleNum) + "")
                    .concat(Common.Nominee + ""));
        } else if (entity.equals("CoBorrower")) {
            rel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(dto.clientRelSeq, Common.cobAddress, true);
            entitySeq = dto.clientRelSeq;
            addrSeq = Long.parseLong((Common.GenerateTableSequence(dto.cnicNum + "", TableNames.MW_ADDR, dto.cycleNum) + "")
                    .concat(Common.Coborrower + ""));
        } else if (entity.equals("Business")) {
            rel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(dto.bizSeq, Common.businessAddress, true);
            entitySeq = dto.bizSeq;
            addrSeq = Long.parseLong((Common.GenerateTableSequence(dto.cnicNum + "", TableNames.MW_ADDR, dto.cycleNum) + "")
                    .concat(Common.BusinessAppraisal + ""));
        } else if (entity.equals("ClientRel")) {
            rel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(dto.clientRelSeq, Common.relAddress, true);
            entitySeq = dto.clientRelSeq;
            addrSeq = Long.parseLong((Common.GenerateTableSequence(dto.cnicNum + "", TableNames.MW_ADDR, dto.cycleNum) + "")
                    .concat(Common.Relatives + ""));
        } else if (entity.equals("SchoolAppraisal")) {
            rel = mwAddrRelRepository.findOneByEntySeqAndEntyTypeAndCrntRecFlg(dto.schAprslSeq, Common.schApAddress, true);
            entitySeq = dto.schAprslSeq;
            addrSeq = Long.parseLong((Common.GenerateTableSequence(dto.cnicNum + "", TableNames.MW_ADDR, dto.cycleNum) + "")
                    .concat(Common.SchoolAppraisal + ""));
        } else {
            return 0;
        }

        if (rel != null)
            return 0;

        MwAddr addr = new MwAddr();
        Instant curIns = Instant.now();
        addr.setAddrSeq(addrSeq);
        addr.setCrntRecFlg(true);
        addr.setCrtdBy("w-" + curUser);
        addr.setCrtdDt(curIns);
        addr.setDelFlg(false);
        addr.setEffStartDt(curIns);
        addr.setHseNum(dto.houseNum);
        addr.setLastUpdBy("w-" + curUser);
        addr.setLastUpdDt(curIns);
        addr.setLatitude(dto.lat);
        addr.setLongitude(dto.lon);
        addr.setCitySeq(dto.city);
        addr.setCmntySeq(dto.community);
        addr.setOthDtl(dto.otherDetails);
        addr.setStrt(dto.sreet_area);
        addr.setVlg(dto.village);
        addr.setSyncFlg(true);
        // addr.setAddrTypKey("PERMANENT");

        long adrSeq = mwAddrRepository.save(addr).getAddrSeq();
        MwAddrRel addrRel = new MwAddrRel();
        addrRel.setAddrRelSeq(Common.GenerateTableSequence(dto.cnicNum + "", TableNames.MW_ADDR_REL, dto.cycleNum));
        addrRel.setAddrSeq(addrSeq);
        addrRel.setCrntRecFlg(true);
        addrRel.setCrtdBy("w-" + curUser);
        addrRel.setCrtdDt(curIns);
        addrRel.setDelFlg(false);
        addrRel.setEffStartDt(curIns);
        addrRel.setEntySeq(entitySeq);
        addrRel.setEntyType(entity);
        addrRel.setLastUpdBy("w-" + curUser);
        addrRel.setLastUpdDt(curIns);
        addrRel.setSyncFlg(true);

        mwAddrRelService.save(addrRel).getAddrRelSeq();
        return adrSeq;
    }

    @Transactional
    public long updateAddress(AddressDto dto, String curUser) {
        MwAddr mwAddr = mwAddrRepository.findOneByAddrSeqAndCrntRecFlg(dto.addrSeq, true);
        Instant curIns = Instant.now();
        if (mwAddr == null)
            return 0;

        mwAddr.setLastUpdDt(curIns);
        mwAddr.setLastUpdBy("w-" + curUser);
        mwAddr.setLatitude(dto.lat);
        mwAddr.setLongitude(dto.lon);
        mwAddr.setCitySeq(dto.city);
        mwAddr.setCmntySeq(dto.community);
        mwAddr.setOthDtl(dto.otherDetails);
        mwAddr.setStrt(dto.sreet_area);
        mwAddr.setVlg(dto.village);
        mwAddr.setHseNum(dto.houseNum);
        return mwAddrRepository.save(mwAddr).getAddrSeq();

        // exAddr.setCrntRecFlg( false );
        // exAddr.setEffEndDt( curIns );
        // exAddr.setLastUpdBy( "w-" + curUser );
        // exAddr.setLastUpdDt( curIns );
        // mwAddrRepository.save( exAddr );
        //
        // MwAddr addr = new MwAddr();
        // addr.setAddrSeq( dto.addrSeq );
        // addr.setCrntRecFlg( true );
        // addr.setCrtdBy( "w-" + curUser );
        // addr.setCrtdDt( curIns );
        // addr.setDelFlg( false );
        // addr.setEffStartDt( curIns );
        // addr.setHseNum( dto.houseNum );
        // addr.setLastUpdBy( "w-" + curUser );
        // addr.setLastUpdDt( curIns );
        // addr.setLatitude( dto.lat );
        // addr.setLongitude( dto.lon );
        // addr.setCitySeq( dto.city );
        // addr.setCmntySeq( dto.community );
        // addr.setOthDtl( dto.otherDetails );
        // addr.setStrt( dto.sreet_area );
        // addr.setVlg( dto.village );
        // addr.setSyncFlg( true );
        // addr.setAddrTypKey("PERMANENT");

        // mwAddrRepository.save( addr ).getAddrSeq();
        // return dto.addrSeq;
    }
}
