package com.cpi.correspondent.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A CPICorrespondent.
 */
@Entity
@Table(name = "cpi_correspondent")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CPICorrespondent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "correspondent_code", nullable = false)
    private String correspondentCode;

    @NotNull
    @Column(name = "year", nullable = false)
    private String year;

    @NotNull
    @Column(name = "vessel_name", nullable = false)
    private String vesselName;

    @Column(name = "client_ref")
    private String clientRef;

    @Column(name = "key_word")
    private String keyWord;

    @Column(name = "register_date")
    private Instant registerDate;

    @Column(name = "case_date")
    private Instant caseDate;

    @Column(name = "handler_user")
    private Long handlerUser;

    @Lob
    @Column(name = "remark")
    private String remark;

    @ManyToOne
    @JsonIgnoreProperties("cPICorrespondents")
    private CorrespondentType correspondentType;

    @ManyToOne
    @JsonIgnoreProperties("cPICorrespondents")
    private Club club;

    @ManyToOne
    @JsonIgnoreProperties("cPICorrespondents")
    private ClubPerson clubPerson;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorrespondentCode() {
        return correspondentCode;
    }

    public CPICorrespondent correspondentCode(String correspondentCode) {
        this.correspondentCode = correspondentCode;
        return this;
    }

    public void setCorrespondentCode(String correspondentCode) {
        this.correspondentCode = correspondentCode;
    }

    public String getYear() {
        return year;
    }

    public CPICorrespondent year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getVesselName() {
        return vesselName;
    }

    public CPICorrespondent vesselName(String vesselName) {
        this.vesselName = vesselName;
        return this;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getClientRef() {
        return clientRef;
    }

    public CPICorrespondent clientRef(String clientRef) {
        this.clientRef = clientRef;
        return this;
    }

    public void setClientRef(String clientRef) {
        this.clientRef = clientRef;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public CPICorrespondent keyWord(String keyWord) {
        this.keyWord = keyWord;
        return this;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Instant getRegisterDate() {
        return registerDate;
    }

    public CPICorrespondent registerDate(Instant registerDate) {
        this.registerDate = registerDate;
        return this;
    }

    public void setRegisterDate(Instant registerDate) {
        this.registerDate = registerDate;
    }

    public Instant getCaseDate() {
        return caseDate;
    }

    public CPICorrespondent caseDate(Instant caseDate) {
        this.caseDate = caseDate;
        return this;
    }

    public void setCaseDate(Instant caseDate) {
        this.caseDate = caseDate;
    }

    public Long getHandlerUser() {
        return handlerUser;
    }

    public CPICorrespondent handlerUser(Long handlerUser) {
        this.handlerUser = handlerUser;
        return this;
    }

    public void setHandlerUser(Long handlerUser) {
        this.handlerUser = handlerUser;
    }

    public String getRemark() {
        return remark;
    }

    public CPICorrespondent remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public CorrespondentType getCorrespondentType() {
        return correspondentType;
    }

    public CPICorrespondent correspondentType(CorrespondentType correspondentType) {
        this.correspondentType = correspondentType;
        return this;
    }

    public void setCorrespondentType(CorrespondentType correspondentType) {
        this.correspondentType = correspondentType;
    }

    public Club getClub() {
        return club;
    }

    public CPICorrespondent club(Club club) {
        this.club = club;
        return this;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public ClubPerson getClubPerson() {
        return clubPerson;
    }

    public CPICorrespondent clubPerson(ClubPerson clubPerson) {
        this.clubPerson = clubPerson;
        return this;
    }

    public void setClubPerson(ClubPerson clubPerson) {
        this.clubPerson = clubPerson;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CPICorrespondent)) {
            return false;
        }
        return id != null && id.equals(((CPICorrespondent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CPICorrespondent{" +
            "id=" + getId() +
            ", correspondentCode='" + getCorrespondentCode() + "'" +
            ", year='" + getYear() + "'" +
            ", vesselName='" + getVesselName() + "'" +
            ", clientRef='" + getClientRef() + "'" +
            ", keyWord='" + getKeyWord() + "'" +
            ", registerDate='" + getRegisterDate() + "'" +
            ", caseDate='" + getCaseDate() + "'" +
            ", handlerUser=" + getHandlerUser() +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
