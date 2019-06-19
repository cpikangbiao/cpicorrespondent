package com.cpi.correspondent.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A CorrespondentDocs.
 */
@Entity
@Table(name = "correspondent_docs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CorrespondentDocs extends  AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "upload_date")
    private Instant uploadDate;

    @Lob
    @Column(name = "document")
    private byte[] document;

    @Column(name = "document_content_type")
    private String documentContentType;

    @ManyToOne
    @JsonIgnoreProperties("correspondentDocs")
    private CPICorrespondent cpiCorrespondent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public CorrespondentDocs documentName(String documentName) {
        this.documentName = documentName;
        return this;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Instant getUploadDate() {
        return uploadDate;
    }

    public CorrespondentDocs uploadDate(Instant uploadDate) {
        this.uploadDate = uploadDate;
        return this;
    }

    public void setUploadDate(Instant uploadDate) {
        this.uploadDate = uploadDate;
    }

    public byte[] getDocument() {
        return document;
    }

    public CorrespondentDocs document(byte[] document) {
        this.document = document;
        return this;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getDocumentContentType() {
        return documentContentType;
    }

    public CorrespondentDocs documentContentType(String documentContentType) {
        this.documentContentType = documentContentType;
        return this;
    }

    public void setDocumentContentType(String documentContentType) {
        this.documentContentType = documentContentType;
    }

    public CPICorrespondent getCpiCorrespondent() {
        return cpiCorrespondent;
    }

    public CorrespondentDocs cpiCorrespondent(CPICorrespondent cPICorrespondent) {
        this.cpiCorrespondent = cPICorrespondent;
        return this;
    }

    public void setCpiCorrespondent(CPICorrespondent cPICorrespondent) {
        this.cpiCorrespondent = cPICorrespondent;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CorrespondentDocs)) {
            return false;
        }
        return id != null && id.equals(((CorrespondentDocs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CorrespondentDocs{" +
            "id=" + getId() +
            ", documentName='" + getDocumentName() + "'" +
            ", uploadDate='" + getUploadDate() + "'" +
            ", document='" + getDocument() + "'" +
            ", documentContentType='" + getDocumentContentType() + "'" +
            "}";
    }
}
