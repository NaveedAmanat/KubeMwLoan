package com.idev4.loans.repository;

import com.idev4.loans.domain.MwEmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the MwEmp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwEmpRepository extends JpaRepository<MwEmp, Long> {

    public List<MwEmp> findAllByEmpNmContaining(String chars);

    public MwEmp findOneByEmpNmContaining(String chars);

    public MwEmp findOneByEmpSeq(long seq);
}
