package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.mapper.OwnerMapper;
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

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.services.OwnerService;

import java.util.List;

/**
 * REST Controller for Owner entity
 * 
 * @author jgomezm
 *
 */
@RestController
@Slf4j
public class OwnerController {

	private OwnerService ownerService;

	private OwnerMapper mapper;

	/**
	 * Constructor with dependency injection
	 * 
	 * @param ownerService
	 * @param mapper
	 */
	public OwnerController(OwnerService ownerService, OwnerMapper mapper) {
		this.ownerService = ownerService;
		this.mapper = mapper;
	}

	/**
	 * Get all owners
	 *
	 * @return
	 */
	@GetMapping(value = "/owners")
	public ResponseEntity<List<OwnerDTO>> findAllOwners() {

		List<Owner> owners = ownerService.findAll();
		log.info("owners: " + owners);
		owners.forEach(item -> log.info("Owner >>  {} ", item));

		List<OwnerDTO> ownersDTO = this.mapper.mapToDtoList(owners);
		log.info("ownersDTO: " + ownersDTO);
		ownersDTO.forEach(item -> log.info("OwnerDTO >>  {} ", item));

		return ResponseEntity.ok(ownersDTO);

	}

	/**
	 * Create owner
	 *
	 * @param ownerDTO
	 * @return
	 */
	@PostMapping(value = "/owners")
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<OwnerDTO> create(@RequestBody OwnerDTO ownerDTO) {

		OwnerDTO newOwnerDTO = ownerService.create(ownerDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(newOwnerDTO);

	}

	/**
	 * Find owner by id
	 *
	 * @param id
	 * @return
	 * @throws OwnerNotFoundException
	 */
	@GetMapping(value = "/owners/{id}")
	ResponseEntity<OwnerDTO> findById(@PathVariable Long id) {

		OwnerDTO ownerDto = null;

		try {
			ownerDto = ownerService.findById(id);

		} catch (OwnerNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(ownerDto);
	}

	/**
	 * Update owner
	 *
	 * @param ownerDTO
	 * @param id
	 * @return
	 */
	@PutMapping(value = "/owners/{id}")
	ResponseEntity<OwnerDTO> update(@RequestBody OwnerDTO ownerDTO, @PathVariable Long id) {

		OwnerDTO updateOwnerDto = null;

		try {

			updateOwnerDto = ownerService.findById(id);

			updateOwnerDto.setFirstName(ownerDTO.getFirstName());
			updateOwnerDto.setLastName(ownerDTO.getLastName());
			updateOwnerDto.setAddress(ownerDTO.getAddress());
			updateOwnerDto.setCity(ownerDTO.getCity());
			updateOwnerDto.setTelephone(ownerDTO.getTelephone());

			ownerService.update(updateOwnerDto);

		} catch (OwnerNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(updateOwnerDto);
	}

	/**
	 * Delete owner by id
	 *
	 * @param id
	 */
	@DeleteMapping(value = "/owners/{id}")
	ResponseEntity<String> delete(@PathVariable Long id) {

		try {
			ownerService.delete(id);
			return ResponseEntity.ok(" Delete ID :" + id);
		} catch (OwnerNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
