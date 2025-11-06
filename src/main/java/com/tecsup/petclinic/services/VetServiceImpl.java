package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.mapper.VetMapper;
import com.tecsup.petclinic.repositories.VetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for Vet entity
 * 
 * @author jgomezm
 *
 */
@Service
@Slf4j
public class VetServiceImpl implements VetService {

	VetRepository vetRepository;
	VetMapper vetMapper;

	public VetServiceImpl(VetRepository vetRepository, VetMapper vetMapper) {
		this.vetRepository = vetRepository;
		this.vetMapper = vetMapper;
	}

	/**
	 * Create a new vet
	 * 
	 * @param vetDTO
	 * @return
	 */
	@Override
	public VetDTO create(VetDTO vetDTO) {

		Vet newVet = vetRepository.save(vetMapper.mapToEntity(vetDTO));

		return vetMapper.mapToDto(newVet);
	}

	/**
	 * Update an existing vet
	 * 
	 * @param vetDTO
	 * @return
	 */
	@Override
	public VetDTO update(VetDTO vetDTO) {

		Vet updatedVet = vetRepository.save(vetMapper.mapToEntity(vetDTO));

		return vetMapper.mapToDto(updatedVet);

	}

	/**
	 * Delete a vet by id
	 * 
	 * @param id
	 * @throws VetNotFoundException
	 */
	@Override
	public void delete(Integer id) throws VetNotFoundException {

		VetDTO vet = findById(id);

		vetRepository.delete(this.vetMapper.mapToEntity(vet));

	}

	/**
	 * Find a vet by id
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public VetDTO findById(Integer id) throws VetNotFoundException {

		Optional<Vet> vet = vetRepository.findById(id);

		if (!vet.isPresent())
			throw new VetNotFoundException("Record not found...!");

		return this.vetMapper.mapToDto(vet.get());
	}

	/**
	 * Find vets by first name
	 * 
	 * @param firstName
	 * @return
	 */
	@Override
	public List<VetDTO> findByFirstName(String firstName) {

		List<Vet> vets = vetRepository.findByFirstName(firstName);

		vets.forEach(vet -> log.info("" + vet));

		return vets
				.stream()
				.map(this.vetMapper::mapToDto)
				.collect(Collectors.toList());
	}

	/**
	 * Find vets by last name
	 * 
	 * @param lastName
	 * @return
	 */
	@Override
	public List<Vet> findByLastName(String lastName) {

		List<Vet> vets = vetRepository.findByLastName(lastName);

		vets.forEach(vet -> log.info("" + vet));

		return vets;
	}

	/**
	 * Find all vets
	 *
	 * @return
	 */
	@Override
	public List<Vet> findAll() {
		return vetRepository.findAll();
	}
}
