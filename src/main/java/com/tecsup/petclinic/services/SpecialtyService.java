package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;

import java.util.List;

/**
 * Service interface for Specialty entity
 * 
 * @author jgomezm
 *
 */
public interface SpecialtyService {

	/**
	 * Create a new specialty
	 * 
	 * @param specialtyDTO
	 * @return
	 */
	public SpecialtyDTO create(SpecialtyDTO specialtyDTO);

	/**
	 * Update an existing specialty
	 * 
	 * @param specialtyDTO
	 * @return
	 */
	SpecialtyDTO update(SpecialtyDTO specialtyDTO);

	/**
	 * Delete a specialty by id
	 * 
	 * @param id
	 * @throws SpecialtyNotFoundException
	 */
	void delete(Integer id) throws SpecialtyNotFoundException;

	/**
	 * Find a specialty by id
	 * 
	 * @param id
	 * @return
	 */
	SpecialtyDTO findById(Integer id) throws SpecialtyNotFoundException;

	/**
	 * Find specialties by name
	 * 
	 * @param name
	 * @return
	 */
	List<SpecialtyDTO> findByName(String name);

	/**
	 * Find all specialties
	 *
	 * @return
	 */
	List<Specialty> findAll();
}
