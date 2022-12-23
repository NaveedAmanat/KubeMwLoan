package com.idev4.loans.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.loans.domain.MwBrnch;
import com.idev4.loans.service.MwBrnchService;
import com.idev4.loans.web.rest.errors.BadRequestAlertException;
import com.idev4.loans.web.rest.util.HeaderUtil;
import com.idev4.loans.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MwBrnch.
 */
@RestController
@RequestMapping("/api")
public class MwBrnchResource {

    private static final String ENTITY_NAME = "mwBrnch";
    private final Logger log = LoggerFactory.getLogger(MwBrnchResource.class);
    private final MwBrnchService mwBrnchService;

    public MwBrnchResource(MwBrnchService mwBrnchService) {
        this.mwBrnchService = mwBrnchService;
    }

    /**
     * POST /mw-brnches : Create a new mwBrnch.
     *
     * @param mwBrnch the mwBrnch to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwBrnch, or with status 400 (Bad Request) if the mwBrnch
     * has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-brnches")
    @Timed
    public ResponseEntity<MwBrnch> createMwBrnch(@RequestBody MwBrnch mwBrnch) throws URISyntaxException {
        log.debug("REST request to save MwBrnch : {}", mwBrnch);
        if (mwBrnch.getBrnchSeq() != null) {
            throw new BadRequestAlertException("A new mwBrnch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwBrnch result = mwBrnchService.save(mwBrnch);
        return ResponseEntity.created(new URI("/api/mw-brnches/" + result.getBrnchSeq()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getBrnchSeq().toString())).body(result);
    }

    @PostMapping("/upload-docs")
    @Timed
    public ResponseEntity<String> uploadDocs(@RequestParam("file") MultipartFile file) throws URISyntaxException {
        log.debug("REST request to save MwBrnch : {}", file.getName());

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileAlreadyExistsException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path fileStorageLocation = Paths.get("/media/");
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
            log.debug("Could not store file {} " + fileName + ". Please try again! {}", ex);
        }

        return ResponseEntity.ok().body("Doc uploaded Successfully  !!");
    }

    /**
     * PUT /mw-brnches : Updates an existing mwBrnch.
     *
     * @param mwBrnch the mwBrnch to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwBrnch, or with status 400 (Bad Request) if the mwBrnch is
     * not valid, or with status 500 (Internal Server Error) if the mwBrnch couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-brnches")
    @Timed
    public ResponseEntity<MwBrnch> updateMwBrnch(@RequestBody MwBrnch mwBrnch) throws URISyntaxException {
        log.debug("REST request to update MwBrnch : {}", mwBrnch);
        if (mwBrnch.getBrnchSeq() == null) {
            return createMwBrnch(mwBrnch);
        }
        MwBrnch result = mwBrnchService.save(mwBrnch);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwBrnch.getBrnchSeq().toString()))
                .body(result);
    }

    /**
     * GET /mw-brnches : get all the mwBrnches.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwBrnches in body
     */
    @GetMapping("/mw-brnches")
    @Timed
    public ResponseEntity<List<MwBrnch>> getAllMwBrnches(Pageable pageable) {
        log.debug("REST request to get a page of MwBrnches");
        Page<MwBrnch> page = mwBrnchService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-brnches");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mw-brnches/:id : get the "id" mwBrnch.
     *
     * @param id the id of the mwBrnch to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwBrnch, or with status 404 (Not Found)
     */
    @GetMapping("/mw-brnches/{id}")
    @Timed
    public ResponseEntity<MwBrnch> getMwBrnch(@PathVariable Long id) {
        log.debug("REST request to get MwBrnch : {}", id);
        MwBrnch mwBrnch = mwBrnchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwBrnch));
    }

}
