package com.idev4.loans.repository;

import com.idev4.loans.domain.MWTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MWTags entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MWTagsRepository extends JpaRepository<MWTags, Long> {

    public MWTags findOneByTagId(String cnic);

    public MWTags findOneByTagIdAndCrntRecFlg(String cnic, boolean recFlag);
}
