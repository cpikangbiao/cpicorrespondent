package com.cpi.correspondent.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the CorrespondentDocs entity. This class is used in CorrespondentDocsResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /correspondent-docs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CorrespondentDocsCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter documentName;

    private InstantFilter uploadDate;

    private LongFilter cpiCorrespondentId;

    public CorrespondentDocsCriteria() {
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
    public String toString() {
        return "CorrespondentDocsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (documentName != null ? "documentName=" + documentName + ", " : "") +
                (uploadDate != null ? "uploadDate=" + uploadDate + ", " : "") +
                (cpiCorrespondentId != null ? "cpiCorrespondentId=" + cpiCorrespondentId + ", " : "") +
            "}";
    }

}
