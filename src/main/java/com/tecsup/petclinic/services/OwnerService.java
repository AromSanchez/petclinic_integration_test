package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;

import java.util.List;

/**
 * Service interface for Owner entity
 * 
 * @author jgomezm
 *
 */
public interface OwnerService {

	/**
	 * Create a new owner
	 * 
	 * @param ownerDTO
	 * @return
	 */
	public OwnerDTO create(OwnerDTO ownerDTO);

	/**
	 * Update an existing owner
	 * 
	 * @param ownerDTO
	 * @return
	 */
	OwnerDTO update(OwnerDTO ownerDTO);

	/**
	 * Delete an owner by id
	 * 
	 * @param id
	 * @throws OwnerNotFoundException
	 */
	void delete(Long id) throws OwnerNotFoundException;

	/**
	 * Find an owner by id
	 * 
	 * @param id
	 * @return
	 */
	OwnerDTO findById(Long id) throws OwnerNotFoundException;

	/**
	 * Find owners by first name
	 * 
	 * @param firstName
	 * @return
	 */
	List<OwnerDTO> findByFirstName(String firstName);

	/**
	 * Find owners by last name
	 * 
	 * @param lastName
	 * @return
	 */
	List<Owner> findByLastName(String lastName);
	
	/**
	 * Find owners by city
	 * 
	 * @param city
	 * @return
	 */
	List<Owner> findByCity(String city);

	/**
	 * Find all owners
	 *
	 * @return
	 */
	List<Owner> findAll();
}
