package com.cpi.correspondent.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the CPICorrespondent entity.
 */
public class CPICorrespondentDTO implements Serializable {

    private Long id;

    @NotNull
    private String correspondentCode;

    @NotNull
    private String year;

    @NotNull
    private String vesselName;

    private String clientRef;

    private String keyWord;

    private Instant registerDate;

    private Instant caseDate;

    private Long handlerUser;

    @Lob
    private String remark;

    private Long correspondentTypeId;

    private String correspondentTypeCorrespondentTypeName;

    private Long clubId;

    private String clubClubName;

    private Long clubPersonId;

    private String clubPersonClubPersonName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorrespondentCode() {
        return correspondentCode;
    }

    public void setCorrespondentCode(String correspondentCode) {
        this.correspondentCode = correspondentCode;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getClientRef() {
        return clientRef;
    }

    public void setClientRef(String clientRef) {
        this.clientRef = clientRef;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Instant getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Instant registerDate) {
        this.registerDate = registerDate;
    }

    public Instant getCaseDate() {
        return caseDate;
    }

    public void setCaseDate(Instant caseDate) {
        this.caseDate = caseDate;
    }

    public Long getHandlerUser() {
        return handlerUser;
    }

    public void setHandlerUser(Long handlerUser) {
        this.handlerUser = handlerUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCorrespondentTypeId() {
        return correspondentTypeId;
    }

    public void setCorrespondentTypeId(Long correspondentTypeId) {
        this.correspondentTypeId = correspondentTypeId;
    }

    public String getCorrespondentTypeCorrespondentTypeName() {
        return correspondentTypeCorrespondentTypeName;
    }

    public void setCorrespondentTypeCorrespondentTypeName(String correspondentTypeCorrespondentTypeName) {
        this.correspondentTypeCorrespondentTypeName = correspondentTypeCorrespondentTypeName;
    }

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public String getClubClubName() {
        return clubClubName;
    }

    public void setClubClubName(String clubClubName) {
        this.clubClubName = clubClubName;
    }

    public Long getClubPersonId() {
        return clubPersonId;
    }

    public void setClubPersonId(Long clubPersonId) {
        this.clubPersonId = clubPersonId;
    }

    public String getClubPersonClubPersonName() {
        return clubPersonClubPersonName;
    }

    public void setClubPersonClubPersonName(String clubPersonClubPersonName) {
        this.clubPersonClubPersonName = clubPersonClubPersonName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CPICorrespondentDTO cPICorrespondentDTO = (CPICorrespondentDTO) o;
        if(cPICorrespondentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cPICorrespondentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CPICorrespondentDTO{" +
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
