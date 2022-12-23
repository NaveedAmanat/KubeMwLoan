package com.idev4.loans.repository;

import com.idev4.loans.domain.MwDvcRgstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MwAddr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwDvcRgstryRepository extends JpaRepository<MwDvcRgstr, Long> {

    public MwDvcRgstr findOneByDvcRgstrSeqAndCrntRecFlg(long seq, boolean flag);

    public MwDvcRgstr findOneByDvcAddrAndCrntRecFlg(String seq, boolean flag);
}
