package com.tecsup.petclinic.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tecsup.petclinic.entities.Vet;

/**
 * Repository for Vet entity
 * 
 * @author jgomezm
 *
 */
@Repository
public interface VetRepository 
	extends JpaRepository<Vet, Integer> {

	// Fetch vets by first name
	List<Vet> findByFirstName(String firstName);

	// Fetch vets by last name
	List<Vet> findByLastName(String lastName);

	@Override
	List<Vet> findAll();

}
