package com.cpi.correspondent.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.cpi.correspondent.domain.CorrespondentDocs} entity. This class is used
 * in {@link com.cpi.correspondent.web.rest.CorrespondentDocsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /correspondent-docs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CorrespondentDocsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter documentName;

    private InstantFilter uploadDate;

    private LongFilter cpiCorrespondentId;

    public CorrespondentDocsCriteria(){
    }

    public CorrespondentDocsCriteria(CorrespondentDocsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.documentName = other.documentName == null ? null : other.documentName.copy();
        this.uploadDate = other.uploadDate == null ? null : other.uploadDate.copy();
        this.cpiCorrespondentId = other.cpiCorrespondentId == null ? null : other.cpiCorrespondentId.copy();
    }

    @Override
    public CorrespondentDocsCriteria copy() {
        return new CorrespondentDocsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDocumentName() {
        return documentName;
    }

    public void setDocumentName(StringFilter documentName) {
        this.documentName = documentName;
    }

    public InstantFilter getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(InstantFilter uploadDate) {
        this.uploadDate = uploadDate;
    }

    public LongFilter getCpiCorrespondentId() {
        return cpiCorrespondentId;
    }

    public void setCpiCorrespondentId(LongFilter cpiCorrespondentId) {
        this.cpiCorrespondentId = cpiCorrespondentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CorrespondentDocsCriteria that = (CorrespondentDocsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(documentName, that.documentName) &&
            Objects.equals(uploadDate, that.uploadDate) &&
            Objects.equals(cpiCorrespondentId, that.cpiCorrespondentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        documentName,
        uploadDate,
        cpiCorrespondentId
        );
    }

    @Override
    public String toString() {
        return "CorrespondentDocsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (documentName != null ? "documentName=" + documentName + ", " : "") +
                (uploadDate != null ? "uploadDate=" + uploadDate + ", " : "") +
                (cpiCorrespondentId != null ? "cpiCorrespondentId=" + cpiCorrespondentId + ", " : "") +
            "}";
    }

}
