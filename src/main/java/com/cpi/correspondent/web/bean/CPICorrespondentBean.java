/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CPICorrespondentBean
 * Author:   admin
 * Date:     2018/6/19 16:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.correspondent.web.bean;

import com.cpi.correspondent.service.dto.CPICorrespondentDTO;

import java.util.Date;
import java.util.Objects;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/6/19
 * @since 1.0.0
 */
public class CPICorrespondentBean {

    private CPICorrespondentDTO cpiCorrespondentDTO;

    private String correspondentCode;

    private String vesselName;

    private String clientRef;

    private String year;

    private String  portName;

    private String  correspondentTypeName;

    private Date registDate;

    private Date  caseDate;

    private String piClubName;

    private String piClubPersonName;

    private String handleUser;

    @Override
    public String toString() {
        return "CPICorrespondentBean{" +
            "cpiCorrespondentDTO=" + cpiCorrespondentDTO +
            ", correspondentCode='" + correspondentCode + '\'' +
            ", vesselName='" + vesselName + '\'' +
            ", clientRef='" + clientRef + '\'' +
            ", year='" + year + '\'' +
            ", portName='" + portName + '\'' +
            ", correspondentTypeName='" + correspondentTypeName + '\'' +
            ", registDate=" + registDate +
            ", caseDate=" + caseDate +
            ", piClubName='" + piClubName + '\'' +
            ", piClubPersonName='" + piClubPersonName + '\'' +
            ", handleUser='" + handleUser + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CPICorrespondentBean)) return false;
        CPICorrespondentBean that = (CPICorrespondentBean) o;
        return Objects.equals(getCpiCorrespondentDTO(), that.getCpiCorrespondentDTO()) &&
            Objects.equals(getCorrespondentCode(), that.getCorrespondentCode()) &&
            Objects.equals(getVesselName(), that.getVesselName()) &&
            Objects.equals(getClientRef(), that.getClientRef()) &&
            Objects.equals(getYear(), that.getYear()) &&
            Objects.equals(getPortName(), that.getPortName()) &&
            Objects.equals(getCorrespondentTypeName(), that.getCorrespondentTypeName()) &&
            Objects.equals(getRegistDate(), that.getRegistDate()) &&
            Objects.equals(getCaseDate(), that.getCaseDate()) &&
            Objects.equals(getPiClubName(), that.getPiClubName()) &&
            Objects.equals(getPiClubPersonName(), that.getPiClubPersonName()) &&
            Objects.equals(getHandleUser(), that.getHandleUser());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCpiCorrespondentDTO(), getCorrespondentCode(), getVesselName(), getClientRef(), getYear(), getPortName(), getCorrespondentTypeName(), getRegistDate(), getCaseDate(), getPiClubName(), getPiClubPersonName(), getHandleUser());
    }

    public void init(CPICorrespondentDTO cpiCorrespondentDTO) {
        this.cpiCorrespondentDTO = cpiCorrespondentDTO;
        this.correspondentCode = " ";
        this.vesselName = " ";
        this.clientRef = " ";
        this.year = " ";
        this.portName = " ";
        this.correspondentTypeName = " ";
        this.registDate = new Date();
        this.caseDate = new Date();
        this.piClubName = " ";
        this.piClubPersonName = " ";
        this.handleUser = " ";

//        this.correspondentCode = correspondent.getCorrespondentCode();
//        this.vesselName = correspondent.getVesselName();
//        this.clientRef = correspondent.getClientRef();
//        this.year = correspondent.getYear();
//        if (correspondent.getPortId() != null)
//            this.portName = correspondent.getPortId().getPortName();
//        if (correspondent.getCorrespondentTypeId() != null) {
//            this.correspondentTypeName = correspondent.getCorrespondentTypeId().getName();
//        }
//        this.registDate = correspondent.getRegistDate();
//        this.caseDate = correspondent.getCaseDate();
//        if (correspondent.getPiClubId() != null) {
//            this.piClubName = correspondent.getPiClubId().getPiclubName();
//        }
//        if (correspondent.getPiClubPersonId() != null) {
//            this.piClubPersonName = correspondent.getPiClubPersonId().getPersonName();
//        }
//        if (correspondent.getAssignedUser() != null) {
//            this.handleUser = correspondent.getAssignedUser().getUserName();
//        }
    }

    public CPICorrespondentDTO getCpiCorrespondentDTO() {
        return cpiCorrespondentDTO;
    }

    public void setCpiCorrespondentDTO(CPICorrespondentDTO cpiCorrespondentDTO) {
        this.cpiCorrespondentDTO = cpiCorrespondentDTO;
    }

    public String getCorrespondentCode() {
        return correspondentCode;
    }

    public void setCorrespondentCode(String correspondentCode) {
        this.correspondentCode = correspondentCode;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getCorrespondentTypeName() {
        return correspondentTypeName;
    }

    public void setCorrespondentTypeName(String correspondentTypeName) {
        this.correspondentTypeName = correspondentTypeName;
    }

    public Date getRegistDate() {
        return registDate;
    }

    public void setRegistDate(Date registDate) {
        this.registDate = registDate;
    }

    public Date getCaseDate() {
        return caseDate;
    }

    public void setCaseDate(Date caseDate) {
        this.caseDate = caseDate;
    }

    public String getPiClubName() {
        return piClubName;
    }

    public void setPiClubName(String piClubName) {
        this.piClubName = piClubName;
    }

    public String getPiClubPersonName() {
        return piClubPersonName;
    }

    public void setPiClubPersonName(String piClubPersonName) {
        this.piClubPersonName = piClubPersonName;
    }

    public String getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(String handleUser) {
        this.handleUser = handleUser;
    }
}
