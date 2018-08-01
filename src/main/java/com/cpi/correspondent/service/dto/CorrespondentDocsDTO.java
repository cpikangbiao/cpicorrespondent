package com.cpi.correspondent.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the CorrespondentDocs entity.
 */
public class CorrespondentDocsDTO implements Serializable {

    private Long id;

    private String documentName;

    private Instant uploadDate;

    @Lob
    private byte[] document;
    private String documentContentType;

    private Long cpiCorrespondentId;

    private String cpiCorrespondentCorrespondentCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Instant getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Instant uploadDate) {
        this.uploadDate = uploadDate;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getDocumentContentType() {
        return documentContentType;
    }

    public void setDocumentContentType(String documentContentType) {
        this.documentContentType = documentContentType;
    }

    public Long getCpiCorrespondentId() {
        return cpiCorrespondentId;
    }

    public void setCpiCorrespondentId(Long cPICorrespondentId) {
        this.cpiCorrespondentId = cPICorrespondentId;
    }

    public String getCpiCorrespondentCorrespondentCode() {
        return cpiCorrespondentCorrespondentCode;
    }

    public void setCpiCorrespondentCorrespondentCode(String cPICorrespondentCorrespondentCode) {
        this.cpiCorrespondentCorrespondentCode = cPICorrespondentCorrespondentCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CorrespondentDocsDTO correspondentDocsDTO = (CorrespondentDocsDTO) o;
        if(correspondentDocsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), correspondentDocsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorrespondentDocsDTO{" +
            "id=" + getId() +
            ", documentName='" + getDocumentName() + "'" +
            ", uploadDate='" + getUploadDate() + "'" +
            ", document='" + getDocument() + "'" +
            "}";
    }
}
