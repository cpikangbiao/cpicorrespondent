/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CpiCorrespondentTimeLineBean
 * Author:   admin
 * Date:     2018/8/8 10:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.correspondent.service.bean.timeline;

import java.time.Instant;
import java.util.Objects;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/8/8
 * @since 1.0.0
 */

public class CpiCorrespondentTimeLineBean  implements Comparable<CpiCorrespondentTimeLineBean> {
    @Override
    public int compareTo(CpiCorrespondentTimeLineBean o) {
        int mark = 1;
        if(this.getIncidentTime().equals(o.getIncidentTime())){
            mark =  0;
        } else if (this.getIncidentTime().isAfter(o.getIncidentTime()) ) {
            mark =  -1;
        }

        return mark;
    }

    private Instant incidentTime;

    private String  incident;

    private String  incidentType;

    public CpiCorrespondentTimeLineBean(Instant incidentTime, String incident, String incidentType) {
        this.incidentTime = incidentTime;
        this.incident = incident;
        this.incidentType = incidentType;
    }

    @Override
    public String toString() {
        return "CpiCorrespondentTimeLineBean{" +
            "incident='" + incident + '\'' +
            ", incidentType='" + incidentType + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CpiCorrespondentTimeLineBean)) return false;
        CpiCorrespondentTimeLineBean that = (CpiCorrespondentTimeLineBean) o;
        return Objects.equals(getIncidentTime(), that.getIncidentTime()) &&
            Objects.equals(getIncident(), that.getIncident()) &&
            Objects.equals(getIncidentType(), that.getIncidentType());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIncidentTime(), getIncident(), getIncidentType());
    }

    public Instant getIncidentTime() {
        return incidentTime;
    }

    public void setIncidentTime(Instant incidentTime) {
        this.incidentTime = incidentTime;
    }

    public String getIncident() {
        return incident;
    }

    public void setIncident(String incident) {
        this.incident = incident;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }
}
