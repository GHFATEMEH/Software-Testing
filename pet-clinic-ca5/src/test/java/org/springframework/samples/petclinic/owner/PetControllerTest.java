package org.springframework.samples.petclinic.owner;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;

//@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PetController.class)
public class PetControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private PetService petService;
	@MockBean
	private OwnerRepository owners;
	@MockBean
	private PetRepository pets;

	private Pet a;
	private Owner george;

	@Before
	public void setUp() throws Exception {
		a = new Pet();
		a.setId(1);
		george = new Owner();
		george.setId(1);
		george.setFirstName("George");
		george.setLastName("Franklin");
		george.setAddress("110 W. Liberty St.");
		george.setCity("Madison");
		george.setTelephone("6085551023");
		Owner b = new Owner();
		b.setId(2);
		a.setOwner(b);
	}

	@Test
	public void initCreationFormTest() throws Exception {
		mockMvc.perform(get("/owners/1/pets/new")).andExpect(status().isOk()).andExpect(model().attributeExists("pet"))
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	public void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(
			post("/owners/1/pets/new").param("name", "Joe"))
			.andExpect(status().isOk()).andExpect(model().attributeHasErrors("pet"))
			.andExpect(model().attributeHasFieldErrors("pet", "name"))
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	public void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/1/pets/new").param("name", "Joe"))
			.andExpect(status().is3xxRedirection());
	}

	@Test
	public void testInitUpdatePetForm() throws Exception {
		mockMvc.perform(get("/owners/1/pets/{petId}/edit", 1)).andExpect(status().isOk())
			.andExpect(model().attributeExists("pet"))
			.andExpect(model().attribute("pet", hasProperty("id", is(1))))
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	public void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(post("/pets/{petId}/edit", 1).param("firstName", "Joe"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Test
	public void testProcessUpdatePetFormHasErrors() throws Exception {
		mockMvc.perform(post("/owners/1/pets/1/edit").param("name", "Joe"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("pet"))
			.andExpect(model().attributeHasFieldErrors("pet", "name"))
			.andExpect(model().attributeExists("pet"))
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}
}
