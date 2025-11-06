package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.mapper.VetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.services.VetService;

import java.util.List;

/**
 * REST Controller for Vet entity
 * 
 * @author jgomezm
 *
 */
@RestController
@Slf4j
public class VetController {

	private VetService vetService;

	private VetMapper mapper;

	/**
	 * Constructor with dependency injection
	 * 
	 * @param vetService
	 * @param mapper
	 */
	public VetController(VetService vetService, VetMapper mapper) {
		this.vetService = vetService;
		this.mapper = mapper;
	}

	/**
	 * Get all vets
	 *
	 * @return
	 */
	@GetMapping(value = "/vets")
	public ResponseEntity<List<VetDTO>> findAllVets() {

		List<Vet> vets = vetService.findAll();
		log.info("vets: " + vets);
		vets.forEach(item -> log.info("Vet >>  {} ", item));

		List<VetDTO> vetsDTO = this.mapper.mapToDtoList(vets);
		log.info("vetsDTO: " + vetsDTO);
		vetsDTO.forEach(item -> log.info("VetDTO >>  {} ", item));

		return ResponseEntity.ok(vetsDTO);

	}

	/**
	 * Create vet
	 *
	 * @param vetDTO
	 * @return
	 */
	@PostMapping(value = "/vets")
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<VetDTO> create(@RequestBody VetDTO vetDTO) {

		VetDTO newVetDTO = vetService.create(vetDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(newVetDTO);

	}

	/**
	 * Find vet by id
	 *
	 * @param id
	 * @return
	 * @throws VetNotFoundException
	 */
	@GetMapping(value = "/vets/{id}")
	ResponseEntity<VetDTO> findById(@PathVariable Integer id) {

		VetDTO vetDto = null;

		try {
			vetDto = vetService.findById(id);

		} catch (VetNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(vetDto);
	}

	/**
	 * Update vet
	 *
	 * @param vetDTO
	 * @param id
	 * @return
	 */
	@PutMapping(value = "/vets/{id}")
	ResponseEntity<VetDTO> update(@RequestBody VetDTO vetDTO, @PathVariable Integer id) {

		VetDTO updateVetDto = null;

		try {

			updateVetDto = vetService.findById(id);

			updateVetDto.setFirstName(vetDTO.getFirstName());
			updateVetDto.setLastName(vetDTO.getLastName());

			vetService.update(updateVetDto);

		} catch (VetNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(updateVetDto);
	}

	/**
	 * Delete vet by id
	 *
	 * @param id
	 */
	@DeleteMapping(value = "/vets/{id}")
	ResponseEntity<String> delete(@PathVariable Integer id) {

		try {
			vetService.delete(id);
			return ResponseEntity.ok(" Delete ID :" + id);
		} catch (VetNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
