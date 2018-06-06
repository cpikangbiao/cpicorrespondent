package com.cpi.correspondent.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Correspondent.
 */
@Entity
@Table(name = "correspondent")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Correspondent extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "correspondent_code", nullable = false)
    private String correspondentCode;

    @NotNull
    @Column(name = "jhi_year", nullable = false)
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
    private CorrespondentType correspondentType;

    @ManyToOne
    private Club club;

    @ManyToOne
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

    public Correspondent correspondentCode(String correspondentCode) {
        this.correspondentCode = correspondentCode;
        return this;
    }

    public void setCorrespondentCode(String correspondentCode) {
        this.correspondentCode = correspondentCode;
    }

    public String getYear() {
        return year;
    }

    public Correspondent year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getVesselName() {
        return vesselName;
    }

    public Correspondent vesselName(String vesselName) {
        this.vesselName = vesselName;
        return this;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getClientRef() {
        return clientRef;
    }

    public Correspondent clientRef(String clientRef) {
        this.clientRef = clientRef;
        return this;
    }

    public void setClientRef(String clientRef) {
        this.clientRef = clientRef;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public Correspondent keyWord(String keyWord) {
        this.keyWord = keyWord;
        return this;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Instant getRegisterDate() {
        return registerDate;
    }

    public Correspondent registerDate(Instant registerDate) {
        this.registerDate = registerDate;
        return this;
    }

    public void setRegisterDate(Instant registerDate) {
        this.registerDate = registerDate;
    }

    public Instant getCaseDate() {
        return caseDate;
    }

    public Correspondent caseDate(Instant caseDate) {
        this.caseDate = caseDate;
        return this;
    }

    public void setCaseDate(Instant caseDate) {
        this.caseDate = caseDate;
    }

    public Long getHandlerUser() {
        return handlerUser;
    }

    public Correspondent handlerUser(Long handlerUser) {
        this.handlerUser = handlerUser;
        return this;
    }

    public void setHandlerUser(Long handlerUser) {
        this.handlerUser = handlerUser;
    }

    public String getRemark() {
        return remark;
    }

    public Correspondent remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public CorrespondentType getCorrespondentType() {
        return correspondentType;
    }

    public Correspondent correspondentType(CorrespondentType correspondentType) {
        this.correspondentType = correspondentType;
        return this;
    }

    public void setCorrespondentType(CorrespondentType correspondentType) {
        this.correspondentType = correspondentType;
    }

    public Club getClub() {
        return club;
    }

    public Correspondent club(Club club) {
        this.club = club;
        return this;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public ClubPerson getClubPerson() {
        return clubPerson;
    }

    public Correspondent clubPerson(ClubPerson clubPerson) {
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Correspondent correspondent = (Correspondent) o;
        if (correspondent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correspondent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Correspondent{" +
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
