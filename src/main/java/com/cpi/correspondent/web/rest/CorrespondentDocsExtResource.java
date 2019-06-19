package com.cpi.correspondent.web.rest;


import com.cpi.correspondent.service.CorrespondentDocsQueryService;
import com.cpi.correspondent.service.CorrespondentDocsService;
import com.cpi.correspondent.service.dto.CorrespondentDocsCriteria;
import com.cpi.correspondent.service.dto.CorrespondentDocsDTO;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CorrespondentDocs.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentDocsExtResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentDocsExtResource.class);

    private static final String ENTITY_NAME = "correspondentDocs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CorrespondentDocsService correspondentDocsService;

    private final CorrespondentDocsQueryService correspondentDocsQueryService;

    public CorrespondentDocsExtResource(CorrespondentDocsService correspondentDocsService, CorrespondentDocsQueryService correspondentDocsQueryService) {
        this.correspondentDocsService = correspondentDocsService;
        this.correspondentDocsQueryService = correspondentDocsQueryService;
    }



    @GetMapping("/correspondent-docs/{id}/download")
    public ResponseEntity<byte[]> downloadDocFile(@PathVariable Long id) {
        CorrespondentDocsDTO correspondentDocsDTO = correspondentDocsService.findOne(id).get();

        byte[] bytes = correspondentDocsDTO.getDocument();

        StringBuilder fileName = new StringBuilder();
        fileName.append(correspondentDocsDTO.getDocumentName());

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.parseMediaType(correspondentDocsDTO.getDocumentContentType()));
        try {
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(fileName.toString(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        header.setContentLength(bytes.length);

        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }
}
