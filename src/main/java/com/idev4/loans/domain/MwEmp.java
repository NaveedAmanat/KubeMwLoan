package com.idev4.loans.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MwEmp.
 */
@Entity
@Table(name = "mw_emp")
public class MwEmp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_seq")
    private Long empSeq;

    @Column(name = "emp_cnic")
    private String empCnic;

    @Column(name = "emp_nm")
    private String empNm;

    @Column(name = "emp_lan_id")
    private String empLanId;

    public Long getEmpSeq() {
        return empSeq;
    }

    public void setEmpSeq(Long empSeq) {
        this.empSeq = empSeq;
    }

    public MwEmp empSeq(Long empSeq) {
        this.empSeq = empSeq;
        return this;
    }

    public String getEmpCnic() {
        return empCnic;
    }

    public void setEmpCnic(String empCnic) {
        this.empCnic = empCnic;
    }

    public MwEmp empCnic(String empCnic) {
        this.empCnic = empCnic;
        return this;
    }

    public String getEmpNm() {
        return empNm;
    }

    public void setEmpNm(String empNm) {
        this.empNm = empNm;
    }

    public MwEmp empNm(String empNm) {
        this.empNm = empNm;
        return this;
    }

    public String getEmpLanId() {
        return empLanId;
    }

    public void setEmpLanId(String empLanId) {
        this.empLanId = empLanId;
    }

    public MwEmp empLanId(String empLanId) {
        this.empLanId = empLanId;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MwEmp mwEmp = (MwEmp) o;
        if (mwEmp.getEmpSeq() == null || getEmpSeq() == null) {
            return false;
        }
        return Objects.equals(getEmpSeq(), mwEmp.getEmpSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEmpSeq());
    }

    @Override
    public String toString() {
        return "MwEmp{" +
                "id=" + getEmpSeq() +
                ", empSeq=" + getEmpSeq() +
                ", empCnic='" + getEmpCnic() + "'" +
                ", empNm='" + getEmpNm() + "'" +
                ", empLanId='" + getEmpLanId() + "'" +
                "}";
    }
}
