package com.cpi.correspondent.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.correspondent.domain.BillFinanceType;
import com.cpi.correspondent.domain.CorrespondentBill;
import com.cpi.correspondent.service.CorrespondentDocsService;
import com.cpi.correspondent.web.rest.errors.BadRequestAlertException;
import com.cpi.correspondent.web.rest.util.HeaderUtil;
import com.cpi.correspondent.web.rest.util.PaginationUtil;
import com.cpi.correspondent.service.dto.CorrespondentDocsDTO;
import com.cpi.correspondent.service.dto.CorrespondentDocsCriteria;
import com.cpi.correspondent.service.CorrespondentDocsQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CorrespondentDocsResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentDocsResource.class);

    private static final String ENTITY_NAME = "correspondentDocs";

    private final CorrespondentDocsService correspondentDocsService;

    private final CorrespondentDocsQueryService correspondentDocsQueryService;

    public CorrespondentDocsResource(CorrespondentDocsService correspondentDocsService, CorrespondentDocsQueryService correspondentDocsQueryService) {
        this.correspondentDocsService = correspondentDocsService;
        this.correspondentDocsQueryService = correspondentDocsQueryService;
    }

    /**
     * POST  /correspondent-docs : Create a new correspondentDocs.
     *
     * @param correspondentDocsDTO the correspondentDocsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new correspondentDocsDTO, or with status 400 (Bad Request) if the correspondentDocs has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/correspondent-docs")
    @Timed
    public ResponseEntity<CorrespondentDocsDTO> createCorrespondentDocs(@RequestBody CorrespondentDocsDTO correspondentDocsDTO) throws URISyntaxException {
        log.debug("REST request to save CorrespondentDocs : {}", correspondentDocsDTO);
        if (correspondentDocsDTO.getId() != null) {
            throw new BadRequestAlertException("A new correspondentDocs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrespondentDocsDTO result = correspondentDocsService.save(correspondentDocsDTO);
        return ResponseEntity.created(new URI("/api/correspondent-docs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /correspondent-docs : Updates an existing correspondentDocs.
     *
     * @param correspondentDocsDTO the correspondentDocsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated correspondentDocsDTO,
     * or with status 400 (Bad Request) if the correspondentDocsDTO is not valid,
     * or with status 500 (Internal Server Error) if the correspondentDocsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/correspondent-docs")
    @Timed
    public ResponseEntity<CorrespondentDocsDTO> updateCorrespondentDocs(@RequestBody CorrespondentDocsDTO correspondentDocsDTO) throws URISyntaxException {
        log.debug("REST request to update CorrespondentDocs : {}", correspondentDocsDTO);
        if (correspondentDocsDTO.getId() == null) {
            return createCorrespondentDocs(correspondentDocsDTO);
        }
        CorrespondentDocsDTO result = correspondentDocsService.save(correspondentDocsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, correspondentDocsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /correspondent-docs : get all the correspondentDocs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of correspondentDocs in body
     */
    @GetMapping("/correspondent-docs")
    @Timed
    public ResponseEntity<List<CorrespondentDocsDTO>> getAllCorrespondentDocs(CorrespondentDocsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CorrespondentDocs by criteria: {}", criteria);
        Page<CorrespondentDocsDTO> page = correspondentDocsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/correspondent-docs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /correspondent-docs/:id : get the "id" correspondentDocs.
     *
     * @param id the id of the correspondentDocsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the correspondentDocsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/correspondent-docs/{id}")
    @Timed
    public ResponseEntity<CorrespondentDocsDTO> getCorrespondentDocs(@PathVariable Long id) {
        log.debug("REST request to get CorrespondentDocs : {}", id);
        CorrespondentDocsDTO correspondentDocsDTO = correspondentDocsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(correspondentDocsDTO));
    }

    /**
     * DELETE  /correspondent-docs/:id : delete the "id" correspondentDocs.
     *
     * @param id the id of the correspondentDocsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/correspondent-docs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorrespondentDocs(@PathVariable Long id) {
        log.debug("REST request to delete CorrespondentDocs : {}", id);
        correspondentDocsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/correspondent-docs/{id}/download")
    @Timed
    public ResponseEntity<byte[]> downloadDocFile(@PathVariable Long id) {
        CorrespondentDocsDTO correspondentDocsDTO = correspondentDocsService.findOne(id);

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
