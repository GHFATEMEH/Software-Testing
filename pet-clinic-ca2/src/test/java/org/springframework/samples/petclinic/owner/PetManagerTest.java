package org.springframework.samples.petclinic.owner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;
import java.lang.reflect.Field;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.visit.Visit;

public class PetManagerTest {
	PetManager petManager;
	PetTimedCache petTimedCache;
	OwnerRepository owners;
	Logger criticalLogger;

	@Before
	public void setUp() throws Exception {
		petTimedCache = Mockito.mock(PetTimedCache.class);
		owners = Mockito.mock(OwnerRepository.class);
		criticalLogger = Mockito.mock(Logger.class);
		petManager = new PetManager(petTimedCache, owners, criticalLogger);
	}

	// mock, behavioral, mockist
	// owners.findById(1) is indirect input
	@Test
	public void testFindOwner() {
		Owner owner = new Owner();
		owner.setId(1);
		when(owners.findById(1)).thenReturn(owner);
		assertEquals(owner, petManager.findOwner(1));
		verify(criticalLogger).info("find owner {}", 1);
	}
	
	// spy, behavioral, mockist
	@Test
	public void testNewPet() {
		Owner owner = Mockito.spy(Owner.class);
		owner.setId(1);
		Pet pet = petManager.newPet(owner);
		verify(owner).addPet(pet);
		verify(criticalLogger).info("add pet for owner {}", 1);
	}
	
	// mock, behavioral, mockist
	// petTimedCache.get(1) is indirect input
	@Test
	public void testFindPet() {
		Pet pet = new Pet();
		pet.setId(1);
		when(petTimedCache.get(1)).thenReturn(pet);
		assertEquals(pet, petManager.findPet(1));
		verify(criticalLogger).info("find pet by id {}", 1);
	}
	
	// spy, behavioral, mockist
	@Test
	public void testSavePet() {
		Pet pet = new Pet();
		Owner owner = Mockito.spy(Owner.class);
		pet.setId(1);
		petManager.savePet(pet, owner);
		verify(owner).addPet(pet);
		verify(petTimedCache).save(pet);
		verify(criticalLogger).info("save pet {}", 1);
	}
	
	// indirect input is owner, indirect output log file
	// mock, behavioral, mockist
	@Test
	public void testGetOwnerPets() {
		Owner owner = new Owner();
		owner.setId(1);
		Set<Pet> pets = new HashSet<>();
		Pet pet1 = new Pet();
		Pet pet2 = new Pet();
		pets.add(pet1);
		pets.add(pet2);
		owner.setPetsInternal(pets);
		
		List<Pet> petsList = new ArrayList<>();
		petsList.addAll(pets);
		
		when(petManager.findOwner(1)).thenReturn(owner);
		assertEquals(petsList, petManager.getOwnerPets(1));
		verify(criticalLogger).info("finding the owner's pets by id {}", 1);
	}
	
	// mock, behavioral, mockist
	@Test
	public void testGetOwnerPetTypes() {
		Owner owner = new Owner();
		owner.setId(1);
		Set<Pet> pets = new HashSet<>();
		Set<PetType> petTypes = new HashSet<>();
		
		Pet pet1 = new Pet();
		PetType type1 = new PetType();
		type1.setName("DOG");
		pet1.setType(type1);
		petTypes.add(type1);
		pets.add(pet1);
		
		Pet pet2 = new Pet();
		PetType type2 = new PetType();
		type2.setName("CAT");
		pet2.setType(type2);
		petTypes.add(type2);
		pets.add(pet2);
		
		owner.setPetsInternal(pets);
		
		when(petManager.findOwner(1)).thenReturn(owner);
		assertEquals(petTypes, petManager.getOwnerPetTypes(1));
		verify(criticalLogger).info("finding the owner's petTypes by id {}", 1);
	}
	
	
	
	
	// indirect input is pet, indirect output log file
	// mock, behavioral, mockist
	@Test
	public void testGetVisitsBetween() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Pet pet = new Pet();
		pet.setId(1);
		
		Visit actualVisit = new Visit();
		Set<Visit> visits = new LinkedHashSet<>();
		visits.add(actualVisit);
		
		final Field visitField = pet.getClass().getDeclaredField("visits");
		visitField.setAccessible(true);
		visitField.set(pet, visits);
		
		// argument
		LocalDate now = LocalDate.now();
		LocalDate startDate = now.minusDays(1);
		LocalDate endDate = startDate.plusDays(2);
		
		when(petTimedCache.get(1)).thenReturn(pet);
		
		List<Visit> actualVisits = new ArrayList<>();
		actualVisits.add(actualVisit);
		
		assertEquals(actualVisits, petManager.getVisitsBetween(1, startDate, endDate));
		verify(criticalLogger).info("get visits for pet {} from {} since {}", 1, startDate, endDate);
		
	}

}
