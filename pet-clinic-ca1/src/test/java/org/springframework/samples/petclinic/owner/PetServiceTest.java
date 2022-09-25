package org.springframework.samples.petclinic.owner;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.slf4j.Logger;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith (Parameterized.class)
public class PetServiceTest {

	public Integer petId;
	public Pet returnPet;
	private static Pet pet;
	PetService petService;
	
	public PetServiceTest(Integer _petId, Pet _returnPet) {
		this.petId = _petId;
		this.returnPet = _returnPet;
	}
	
	@Parameters 
	public static List<Object> parameters () throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		pet =  new Pet();
		final Field idField = pet.getClass().getSuperclass().getSuperclass().getDeclaredField("id");
		idField.setAccessible(true);
		idField.set(pet, 1);
		return Arrays.asList(new Object [][] {{1, pet}, {2, null}});
	}
	
	@BeforeEach
	public void setUp() throws Exception {
		PetTimedCache petTimedCache = Mockito.mock(PetTimedCache.class);
		OwnerRepository ownerRepository = Mockito.mock(OwnerRepository.class);
		Logger logger = Mockito.mock(Logger.class);
		this.petService = new PetService(petTimedCache, ownerRepository, logger);
		petTimedCache.save(pet);
	}
	
	@Test
	public void testFindPet() {
//		this.petService.findPet(1);
//		System.out.println("a");
		assertEquals(this.returnPet, petService.findPet(this.petId));
	}

}
