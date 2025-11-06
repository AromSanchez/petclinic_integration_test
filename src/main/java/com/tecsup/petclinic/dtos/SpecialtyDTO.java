package com.tecsup.petclinic.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Specialty entity
 * 
 * @author jgomezm
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SpecialtyDTO {

	private Integer id;
	
	private String name;

}
