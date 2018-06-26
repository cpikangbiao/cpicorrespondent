package com.cpi.correspondent.service.dto.common;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Currency entity.
 */
public class CurrencyDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String fullNameEnglish;

    @Size(max = 255)
    private String fullNameChinese;

    @NotNull
    @Size(max = 255)
    private String countryNameEnglish;

    @Size(max = 255)
    private String countryNameChinese;

    @NotNull
    @Size(max = 100)
    private String nameAbbre;

    private Integer currencySort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullNameEnglish() {
        return fullNameEnglish;
    }

    public void setFullNameEnglish(String fullNameEnglish) {
        this.fullNameEnglish = fullNameEnglish;
    }

    public String getFullNameChinese() {
        return fullNameChinese;
    }

    public void setFullNameChinese(String fullNameChinese) {
        this.fullNameChinese = fullNameChinese;
    }

    public String getCountryNameEnglish() {
        return countryNameEnglish;
    }

    public void setCountryNameEnglish(String countryNameEnglish) {
        this.countryNameEnglish = countryNameEnglish;
    }

    public String getCountryNameChinese() {
        return countryNameChinese;
    }

    public void setCountryNameChinese(String countryNameChinese) {
        this.countryNameChinese = countryNameChinese;
    }

    public String getNameAbbre() {
        return nameAbbre;
    }

    public void setNameAbbre(String nameAbbre) {
        this.nameAbbre = nameAbbre;
    }

    public Integer getCurrencySort() {
        return currencySort;
    }

    public void setCurrencySort(Integer currencySort) {
        this.currencySort = currencySort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurrencyDTO currencyDTO = (CurrencyDTO) o;
        if(currencyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), currencyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CurrencyDTO{" +
            "id=" + getId() +
            ", fullNameEnglish='" + getFullNameEnglish() + "'" +
            ", fullNameChinese='" + getFullNameChinese() + "'" +
            ", countryNameEnglish='" + getCountryNameEnglish() + "'" +
            ", countryNameChinese='" + getCountryNameChinese() + "'" +
            ", nameAbbre='" + getNameAbbre() + "'" +
            ", currencySort=" + getCurrencySort() +
            "}";
    }
}
