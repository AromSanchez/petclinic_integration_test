package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.OwnerDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for OwnerController
 * 
 * @author jgomezm
 */
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class OwnerControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;
	
	/**
	 * Test to find all owners
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindAllOwners() throws Exception {

		final int ID_FIRST_RECORD = 1;

		this.mockMvc.perform(get("/owners"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
	}
	
	/**
	 * Test to find an owner by id - OK case
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindOwnerOK() throws Exception {

		String OWNER_FIRST_NAME = "George";
		String OWNER_LAST_NAME = "Franklin";
		String OWNER_CITY = "Madison";

		this.mockMvc.perform(get("/owners/1"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.firstName", is(OWNER_FIRST_NAME)))
				.andExpect(jsonPath("$.lastName", is(OWNER_LAST_NAME)))
				.andExpect(jsonPath("$.city", is(OWNER_CITY)));
	}

	/**
	 * Test to find an owner by id - KO case (not found)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindOwnerKO() throws Exception {

		mockMvc.perform(get("/owners/999"))
				.andExpect(status().isNotFound());

	}
	
	/**
	 * Test to create a new owner
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateOwner() throws Exception {

		String OWNER_FIRST_NAME = "Juan";
		String OWNER_LAST_NAME = "Perez";
		String OWNER_ADDRESS = "123 Main St.";
		String OWNER_CITY = "Lima";
		String OWNER_TELEPHONE = "987654321";

		OwnerDTO newOwnerDTO = OwnerDTO.builder()
                .firstName(OWNER_FIRST_NAME)
                .lastName(OWNER_LAST_NAME)
                .address(OWNER_ADDRESS)
                .city(OWNER_CITY)
                .telephone(OWNER_TELEPHONE)
                .build();

		this.mockMvc.perform(post("/owners")
						.content(om.writeValueAsString(newOwnerDTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstName", is(OWNER_FIRST_NAME)))
				.andExpect(jsonPath("$.lastName", is(OWNER_LAST_NAME)))
				.andExpect(jsonPath("$.address", is(OWNER_ADDRESS)))
				.andExpect(jsonPath("$.city", is(OWNER_CITY)))
				.andExpect(jsonPath("$.telephone", is(OWNER_TELEPHONE)));

	}

	/**
	 * Test to delete an owner
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeleteOwner() throws Exception {

		String OWNER_FIRST_NAME = "Ana";
		String OWNER_LAST_NAME = "Garcia";
		String OWNER_ADDRESS = "456 Oak Ave.";
		String OWNER_CITY = "Arequipa";
		String OWNER_TELEPHONE = "912345678";

        OwnerDTO newOwnerDTO = OwnerDTO.builder()
                .firstName(OWNER_FIRST_NAME)
                .lastName(OWNER_LAST_NAME)
                .address(OWNER_ADDRESS)
                .city(OWNER_CITY)
                .telephone(OWNER_TELEPHONE)
                .build();

		ResultActions mvcActions = mockMvc.perform(post("/owners")
						.content(om.writeValueAsString(newOwnerDTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());

		String response = mvcActions.andReturn().getResponse().getContentAsString();

		Long id = JsonPath.parse(response).read("$.id", Long.class);

		mockMvc.perform(delete("/owners/" + id ))
				.andExpect(status().isOk());
	}

	/**
	 * Test to delete an owner - KO case (not found)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeleteOwnerKO() throws Exception {

		mockMvc.perform(delete("/owners/" + "999" ))
				.andExpect(status().isNotFound());
	}

	/**
	 * Test to update an owner
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateOwner() throws Exception {

		String OWNER_FIRST_NAME = "Luis";
		String OWNER_LAST_NAME = "Martinez";
		String OWNER_ADDRESS = "789 Pine St.";
		String OWNER_CITY = "Cusco";
		String OWNER_TELEPHONE = "923456789";

		String UP_OWNER_FIRST_NAME = "Luis Alberto";
		String UP_OWNER_LAST_NAME = "Martinez Lopez";
		String UP_OWNER_ADDRESS = "789 Pine Street";
		String UP_OWNER_CITY = "Cusco City";
		String UP_OWNER_TELEPHONE = "923456790";

        OwnerDTO newOwnerDTO = OwnerDTO.builder()
                .firstName(OWNER_FIRST_NAME)
                .lastName(OWNER_LAST_NAME)
                .address(OWNER_ADDRESS)
                .city(OWNER_CITY)
                .telephone(OWNER_TELEPHONE)
                .build();

		// CREATE
		ResultActions mvcActions = mockMvc.perform(post("/owners")
						.content(om.writeValueAsString(newOwnerDTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
						.andDo(print())
						.andExpect(status().isCreated());

		String response = mvcActions.andReturn().getResponse().getContentAsString();
		Long id = JsonPath.parse(response).read("$.id", Long.class);

		// UPDATE
        OwnerDTO upOwnerDTO = OwnerDTO.builder()
                .id(id)
                .firstName(UP_OWNER_FIRST_NAME)
                .lastName(UP_OWNER_LAST_NAME)
                .address(UP_OWNER_ADDRESS)
                .city(UP_OWNER_CITY)
                .telephone(UP_OWNER_TELEPHONE)
                .build();

		mockMvc.perform(put("/owners/"+id)
						.content(om.writeValueAsString(upOwnerDTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());

		// FIND
		mockMvc.perform(get("/owners/" + id))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(id.intValue())))
				.andExpect(jsonPath("$.firstName", is(UP_OWNER_FIRST_NAME)))
				.andExpect(jsonPath("$.lastName", is(UP_OWNER_LAST_NAME)))
				.andExpect(jsonPath("$.address", is(UP_OWNER_ADDRESS)))
				.andExpect(jsonPath("$.city", is(UP_OWNER_CITY)))
				.andExpect(jsonPath("$.telephone", is(UP_OWNER_TELEPHONE)));

		// DELETE
		mockMvc.perform(delete("/owners/" + id))
				.andExpect(status().isOk());
	}

}
