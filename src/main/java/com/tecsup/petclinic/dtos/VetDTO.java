package com.tecsup.petclinic.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Vet entity
 * 
 * @author jgomezm
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VetDTO {

	private Integer id;
	
	private String firstName;
	
	private String lastName;

}
