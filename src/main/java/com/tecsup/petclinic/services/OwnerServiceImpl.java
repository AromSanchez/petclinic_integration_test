package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.mapper.OwnerMapper;
import com.tecsup.petclinic.repositories.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for Owner entity
 * 
 * @author jgomezm
 *
 */
@Service
@Slf4j
public class OwnerServiceImpl implements OwnerService {

	OwnerRepository ownerRepository;
	OwnerMapper ownerMapper;

	public OwnerServiceImpl(OwnerRepository ownerRepository, OwnerMapper ownerMapper) {
		this.ownerRepository = ownerRepository;
		this.ownerMapper = ownerMapper;
	}

	/**
	 * Create a new owner
	 * 
	 * @param ownerDTO
	 * @return
	 */
	@Override
	public OwnerDTO create(OwnerDTO ownerDTO) {

		Owner newOwner = ownerRepository.save(ownerMapper.mapToEntity(ownerDTO));

		return ownerMapper.mapToDto(newOwner);
	}

	/**
	 * Update an existing owner
	 * 
	 * @param ownerDTO
	 * @return
	 */
	@Override
	public OwnerDTO update(OwnerDTO ownerDTO) {

		Owner updatedOwner = ownerRepository.save(ownerMapper.mapToEntity(ownerDTO));

		return ownerMapper.mapToDto(updatedOwner);

	}

	/**
	 * Delete an owner by id
	 * 
	 * @param id
	 * @throws OwnerNotFoundException
	 */
	@Override
	public void delete(Long id) throws OwnerNotFoundException {

		OwnerDTO owner = findById(id);

		ownerRepository.delete(this.ownerMapper.mapToEntity(owner));

	}

	/**
	 * Find an owner by id
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public OwnerDTO findById(Long id) throws OwnerNotFoundException {

		Optional<Owner> owner = ownerRepository.findById(id);

		if (!owner.isPresent())
			throw new OwnerNotFoundException("Record not found...!");

		return this.ownerMapper.mapToDto(owner.get());
	}

	/**
	 * Find owners by first name
	 * 
	 * @param firstName
	 * @return
	 */
	@Override
	public List<OwnerDTO> findByFirstName(String firstName) {

		List<Owner> owners = ownerRepository.findByFirstName(firstName);

		owners.forEach(owner -> log.info("" + owner));

		return owners
				.stream()
				.map(this.ownerMapper::mapToDto)
				.collect(Collectors.toList());
	}

	/**
	 * Find owners by last name
	 * 
	 * @param lastName
	 * @return
	 */
	@Override
	public List<Owner> findByLastName(String lastName) {

		List<Owner> owners = ownerRepository.findByLastName(lastName);

		owners.forEach(owner -> log.info("" + owner));

		return owners;
	}
	
	/**
	 * Find owners by city
	 * 
	 * @param city
	 * @return
	 */
	@Override
	public List<Owner> findByCity(String city) {

		List<Owner> owners = ownerRepository.findByCity(city);

		owners.forEach(owner -> log.info("" + owner));

		return owners;
	}

	/**
	 * Find all owners
	 *
	 * @return
	 */
	@Override
	public List<Owner> findAll() {
		return ownerRepository.findAll();
	}
}
