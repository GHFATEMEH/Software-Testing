package org.springframework.samples.petclinic.owner;

import java.lang.instrument.Instrumentation;


import static org.junit.Assert.*;
import java.io.*;


import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class OwnerTest {

	Owner owner; 
	//todo:: check sets with null and check if any other kind of test is needed
	//set telephone check again fraction and stuff
	
	@Before
	public void setUp() throws Exception {
		owner = new Owner();
	}

//	@Test
//	public void testGetAddress() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
//		final Field field = owner.getClass().getDeclaredField("address");
//        field.setAccessible(true);
//        field.set(owner, "abcd");
//        assertEquals(owner.getAddress(), "abcd");
//	}
//
//	@Test
//	public void testSetAddress() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
//		owner.setAddress("abcd");
//        final Field field = owner.getClass().getDeclaredField("address");
//        field.setAccessible(true);
//        assertEquals(field.get(owner), "abcd");
//	}
//	
////	@Test
////	public void testSetAddressWithNullValue() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
////		owner.setAddress(null);
//////        final Field field = owner.getClass().getDeclaredField("address");
//////        field.setAccessible(true);
//////        final Instrumentation instrumentation = new Instrumentation(); 
//////        assertEquals(instrumentation.getObjectSize(field.get(owner)), 1);
////	}
//	
//	@Test
//	public void testGetCity() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
//		final Field field = owner.getClass().getDeclaredField("city");
//        field.setAccessible(true);
//        field.set(owner, "Tehran");
//        assertEquals(owner.getCity(), "Tehran");
//	}
//	
//	@Test
//	public void testSetCity() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
//		owner.setCity("Tehran");
//        final Field field = owner.getClass().getDeclaredField("city");
//        field.setAccessible(true);
//        assertEquals(field.get(owner), "Tehran");
//	}
//	
//	@Test
//	public void testGetTelephone() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
//		final Field field = owner.getClass().getDeclaredField("telephone");
//        field.setAccessible(true);
//        field.set(owner, "091202");
//        assertEquals(owner.getTelephone(), "091202");
//	}
//	
//	@Test
//	public void testSetTelephone() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
//		owner.setTelephone("091202");
//        final Field field = owner.getClass().getDeclaredField("telephone");
//        field.setAccessible(true);
//        assertEquals(field.get(owner), "091202");
//	}
	
	@Test
	public void testGetPetsInternal_withNullValue() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final Field field = owner.getClass().getDeclaredField("pets");
		field.setAccessible(true);
        field.set(owner, null);
        assertEquals(owner.getPetsInternal().size(), 0);
	}
	
	@Test
	public void testGetPetsInternal() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final Field field = owner.getClass().getDeclaredField("pets");
		field.setAccessible(true);
		Set<Pet> pet = new HashSet<Pet>(); 
		pet.add(new Pet());
        field.set(owner, pet);
        assertEquals(owner.getPetsInternal(), pet);
	}
	
//	@Test
//	public void testSetPetsInternal() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
//		Set<Pet> pet = new HashSet<Pet>(); 
//		pet.add(new Pet());
//		owner.setPetsInternal(pet);
//        final Field field = owner.getClass().getDeclaredField("pets");
//        field.setAccessible(true);
//        assertEquals(field.get(owner), pet);
//	}
	
	@Test
	public void testGetPets() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Pet pet1 = new Pet();
		final Field nameFieldPet1 = pet1.getClass().getSuperclass().getDeclaredField("name");
		nameFieldPet1.setAccessible(true);
		nameFieldPet1.set(pet1, "fish");

		Pet pet2 = new Pet();
		final Field nameFieldPet2 = pet2.getClass().getSuperclass().getDeclaredField("name");
		nameFieldPet2.setAccessible(true);
		nameFieldPet2.set(pet2, "Dog");

		Set<Pet> pets = new HashSet<>();
		pets.add(pet1);
		pets.add(pet2);
		
		// set pets to check method
		final Field fieldOfPets = owner.getClass().getDeclaredField("pets");
		fieldOfPets.setAccessible(true);
		fieldOfPets.set(owner, pets);
		
		// check method getVisits
		List<Pet> petsListExpected = new ArrayList<Pet>();
		petsListExpected.add(pet2);
		petsListExpected.add(pet1);
		List<Pet> petsList = owner.getPets();
	    assertArrayEquals(petsList.toArray(), petsListExpected.toArray());
	}
	
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
	
	@Test
	public void testRemovePet_withPetInSet() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final Field field = owner.getClass().getDeclaredField("pets");
		field.setAccessible(true);
		Pet pet = new Pet();
		Set<Pet> pets = new HashSet<Pet>(); 
		pets.add(pet);
        field.set(owner, pets);
        Set<Pet> copyPets = new HashSet<>(pets);
        owner.removePet(pet);
        copyPets.remove(pet);
        assertEquals(copyPets, field.get(owner));
	}
	
	@Test
	public void testRemovePet_withEmptyPets() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final Field field = owner.getClass().getDeclaredField("pets");
		field.setAccessible(true);
		Pet pet = new Pet();
		Set<Pet> pets = new HashSet<Pet>(); 
        field.set(owner, pets);
        Set<Pet> copyPets = new HashSet<>(pets);
        owner.removePet(pet);
        assertEquals(copyPets, field.get(owner));
	}
	
	@Test
	public void testRemovePet_withPetNotInSet() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final Field field = owner.getClass().getDeclaredField("pets");
		field.setAccessible(true);
		Pet pet1 = new Pet();
		Pet pet2 = new Pet();
		Set<Pet> pets = new HashSet<Pet>(); 
		pets.add(pet1);
        field.set(owner, pets);
        Set<Pet> copyPets = new HashSet<>(pets);
        owner.removePet(pet2);
        assertEquals(copyPets, field.get(owner));
	}
	
	//ignoreNewFalse isNewFalse -> enters if and pet exists -> returns pet
	@Test
	public void testGetPet_ignoreNewFalse_isNewFalse_petExists() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Pet pet = new Pet();
		final Field petField = pet.getClass().getSuperclass().getDeclaredField("name");
		petField.setAccessible(true);
		petField.set(pet, "dog");
		
		final Field field = owner.getClass().getDeclaredField("pets");
		field.setAccessible(true);
		Set<Pet> pets = new HashSet<Pet>(); 
		pets.add(pet);
        field.set(owner, pets);
        assertEquals(owner.getPet("dog", false), pet);
        
	}
	
	//ignoreNewFalse isNewFalse -> enters if and  pet dosen't exist -> returns null
	@Test
	public void testGetPet_ignoreNewFalse_isNewFalse_petDosentExist() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Pet pet = new Pet();
		final Field petField = pet.getClass().getSuperclass().getDeclaredField("name");
		petField.setAccessible(true);
		petField.set(pet, "dog");
		
		final Field field = owner.getClass().getDeclaredField("pets");
		field.setAccessible(true);
		Set<Pet> pets = new HashSet<Pet>(); 
		pets.add(pet);
        field.set(owner, pets);
        assertEquals(owner.getPet("cat", false), null);
        
	}

	@Test
	public void testGetPet_ignoreNewFalse_namesOneUpperOneLowerCase() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Pet pet = new Pet();
		final Field petField = pet.getClass().getSuperclass().getDeclaredField("name");
		petField.setAccessible(true);
		petField.set(pet, "dog");
		
		final Field field = owner.getClass().getDeclaredField("pets");
		field.setAccessible(true);
		Set<Pet> pets = new HashSet<Pet>(); 
		pets.add(pet);
        field.set(owner, pets);
        assertEquals(owner.getPet("Dog", false), pet);
        
	}
	
	//empty set -> returns null
	@Test
	public void testGetPet_emptySet() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final Field field = owner.getClass().getDeclaredField("pets");
		field.setAccessible(true);
		Set<Pet> pets = new HashSet<Pet>(); 
        field.set(owner, pets);
        assertEquals(owner.getPet("dog", false), null);
	}

	//ignoreNew true isNew true -> returns null
	@Test
	public void testGetPet_ignoreNewTrue_NewPet() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		final Field field = owner.getClass().getDeclaredField("pets");
		field.setAccessible(true);
		Set<Pet> pets = new HashSet<Pet>(); 
		pets.add(new Pet());
        field.set(owner, pets);
        assertEquals(owner.getPet("dog", true), null);
	}
	
	//ignoreNew true isNew false pet exists -> returns pet
	@Test
	public void testGetPet_ignoreNewTrue_OldPet_petExists() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Pet pet = new Pet();
		final Field nameField = pet.getClass().getSuperclass().getDeclaredField("name");
		nameField.setAccessible(true);
		nameField.set(pet, "dog");
		final Field idField = pet.getClass().getSuperclass().getSuperclass().getDeclaredField("id");
		idField.setAccessible(true);
		idField.set(pet, 1);
		
		final Field field = owner.getClass().getDeclaredField("pets");
		field.setAccessible(true);
		Set<Pet> pets = new HashSet<Pet>(); 
		pets.add(pet);
        field.set(owner, pets);
        assertEquals(owner.getPet("dog", true), pet);
        
	}
	
	//ignoreNew true isNew false pet dosen't exist -> returns null
	@Test
	public void testGetPet_ignoreNewTrue_OldPet_petDoesntExist() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Pet pet = new Pet();
		final Field nameField = pet.getClass().getSuperclass().getDeclaredField("name");
		nameField.setAccessible(true);
		nameField.set(pet, "cat");
		final Field idField = pet.getClass().getSuperclass().getSuperclass().getDeclaredField("id");
		idField.setAccessible(true);
		idField.set(pet, 1);
		
		final Field field = owner.getClass().getDeclaredField("pets");
		field.setAccessible(true);
		Set<Pet> pets = new HashSet<Pet>(); 
		pets.add(pet);
        field.set(owner, pets);
        assertEquals(owner.getPet("dog", true), null);
        
	}
}
