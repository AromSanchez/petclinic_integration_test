package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import org.junit.jupiter.api.Test;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class SpecialtyServiceTest {

    @Autowired
    private SpecialtyService specialtyService;

    /**
     * Test finding a specialty by ID
     */
    @Test
    public void testFindSpecialtyById() {

        String NAME_EXPECTED = "radiology";

        Integer ID = 1;

        SpecialtyDTO specialty = null;

        try {
            specialty = this.specialtyService.findById(ID);
        } catch (SpecialtyNotFoundException e) {
            fail(e.getMessage());
        }
        assertEquals(NAME_EXPECTED, specialty.getName());
    }

    /**
     * Test finding specialties by name
     */
    @Test
    public void testFindSpecialtyByName() {

        String FIND_NAME = "surgery";
        int SIZE_EXPECTED = 1;

        List<SpecialtyDTO> specialties = this.specialtyService.findByName(FIND_NAME);

        assertEquals(SIZE_EXPECTED, specialties.size());
    }

    /**
     * Test finding all specialties
     */
    @Test
    public void testFindAllSpecialties() {

        int MIN_SIZE_EXPECTED = 3;

        List<Specialty> specialties = this.specialtyService.findAll();

        assertTrue(specialties.size() >= MIN_SIZE_EXPECTED);
    }

    /**
     * Test creating a new specialty
     */
    @Test
    public void testCreateSpecialty() {

        String SPECIALTY_NAME = "cardiology";

        SpecialtyDTO specialtyDTO = SpecialtyDTO.builder()
                .name(SPECIALTY_NAME)
                .build();

        SpecialtyDTO newSpecialtyDTO = this.specialtyService.create(specialtyDTO);

        log.info("SPECIALTY CREATED: " + newSpecialtyDTO.toString());

        assertNotNull(newSpecialtyDTO.getId());
        assertEquals(SPECIALTY_NAME, newSpecialtyDTO.getName());
    }

    /**
     * Test updating an existing specialty
     */
    @Test
    public void testUpdateSpecialty() {

        String SPECIALTY_NAME = "neurology";
        String UP_SPECIALTY_NAME = "neurology-advanced";

        SpecialtyDTO specialtyDTO = SpecialtyDTO.builder()
                .name(SPECIALTY_NAME)
                .build();

        // ------------ Create ---------------

        log.info(">" + specialtyDTO);
        SpecialtyDTO specialtyDTOCreated = this.specialtyService.create(specialtyDTO);
        log.info(">>" + specialtyDTOCreated);

        // ------------ Update ---------------

        // Prepare data for update
        specialtyDTOCreated.setName(UP_SPECIALTY_NAME);

        // Execute update
        SpecialtyDTO upgradeSpecialtyDTO = this.specialtyService.update(specialtyDTOCreated);
        log.info(">>>>" + upgradeSpecialtyDTO);

        //            EXPECTED              ACTUAL
        assertEquals(UP_SPECIALTY_NAME, upgradeSpecialtyDTO.getName());
    }

    /**
     * Test deleting a specialty
     */
    @Test
    public void testDeleteSpecialty() {

        String SPECIALTY_NAME = "dermatology";

        // ------------ Create ---------------

        SpecialtyDTO specialtyDTO = SpecialtyDTO.builder()
                .name(SPECIALTY_NAME)
                .build();

        SpecialtyDTO newSpecialtyDTO = this.specialtyService.create(specialtyDTO);
        log.info("" + specialtyDTO);

        // ------------ Delete ---------------

        try {
            this.specialtyService.delete(newSpecialtyDTO.getId());
        } catch (SpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validation ---------------

        try {
            this.specialtyService.findById(newSpecialtyDTO.getId());
            assertTrue(false);
        } catch (SpecialtyNotFoundException e) {
            assertTrue(true);
        }
    }
}
