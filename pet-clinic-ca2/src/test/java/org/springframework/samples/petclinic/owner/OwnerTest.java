package org.springframework.samples.petclinic.owner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class OwnerTest {

	Owner owner; 
	
	@Before
	public void setUp() throws Exception {
		owner = new Owner();
	}

	// state verification, old pet
	@Test
	public void testAddPet_withOldPet_ShouldNotAdd() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Pet pet = new Pet();
		final Field petField = pet.getClass().getSuperclass().getSuperclass().getDeclaredField("id");
		petField.setAccessible(true);
		petField.set(pet, 1);
        final Field ownerField = owner.getClass().getDeclaredField("pets");
        ownerField.setAccessible(true);
        Object pets = ownerField.get(owner);
        Set<Pet> copyPets;
        if(pets == null)
        	copyPets = null;
        else
        	copyPets = new HashSet<>((Set<Pet>)pets);
        owner.addPet(pet);
        final Field petFieldOwner = pet.getClass().getDeclaredField("owner");
        petFieldOwner.setAccessible(true);
        assertEquals("Failed on adding to owner's pets.", copyPets, ownerField.get(owner));
        assertEquals("Failed on changing the owner.", owner, petFieldOwner.get(pet));
	}
	
	// state verification, new pet
	@Test
	public void testAddPet_withNewPet_ShouldAdd() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Pet pet = new Pet();
        final Field ownerField = owner.getClass().getDeclaredField("pets");
        ownerField.setAccessible(true);
        Object pets = ownerField.get(owner);
        Set<Pet> copyPets;
        if(pets == null)
        	copyPets = new HashSet<>();
        else
        	copyPets = new HashSet<>((Set<Pet>)pets);
        owner.addPet(pet);
        copyPets.add(pet);
        final Field petField = pet.getClass().getDeclaredField("owner");
        petField.setAccessible(true);
        assertEquals("Failed on adding to owner's pets.", copyPets, ownerField.get(owner));
        assertEquals("Failed on changing the owner.", owner, petField.get(pet));
	}
		
	// behavioral
	@Test 
	public void testAddPet_withOldPet_ShouldNotCallAdd_Behavioral() {
		Pet pet = Mockito.spy(Pet.class);
		pet.setId(1);
		owner.addPet(pet);
		verify(pet).isNew();
		verify(pet).setOwner(owner);
	}
	
	// behavioral
	@Test 
	public void testAddPet_withNewPet_ShouldAdd_Behavioral() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Pet pet = Mockito.spy(Pet.class);
		owner.addPet(pet);
		verify(pet).isNew();
		verify(pet).setOwner(owner);
		final Field petField = owner.getClass().getDeclaredField("pets");
        petField.setAccessible(true);
        assertEquals(1, ((Set<Pet>) petField.get(owner)).size());
	}

}
