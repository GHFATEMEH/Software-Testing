package org.springframework.samples.petclinic.owner;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import java.lang.reflect.Field;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.junit.Before;

@RunWith(Theories.class)
public class OwnerTestTheory {

	Owner owner;
	
	@Before
	public void setUp() throws Exception {
		owner = new Owner();
	}
	
	@DataPoints
	public static String[] name = {"Dog", "cat"};
	
	@DataPoints
	public static boolean[] ignoreNew = {true, false};
	
	@DataPoints
	public static Integer[] petId = {null, 1};
	
	@DataPoints
	public static String[] petNameToSet = {"Dog", "cat"};
	
	@Theory
	public void testGetPets(String name, boolean ignoreNew, Integer id, String petNameToSet) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		
		assumeTrue(name != null && petNameToSet != null);
		
		Pet pet = new Pet();
		
		//set name
		final Field petNameField = pet.getClass().getSuperclass().getDeclaredField("name");
		petNameField.setAccessible(true);
		petNameField.set(pet, petNameToSet);
		
		//set id
		final Field petIdField = pet.getClass().getSuperclass().getSuperclass().getDeclaredField("id");
		petIdField.setAccessible(true);
		petIdField.set(pet, id);
		
		// set pet for set of pet for owner
		Set<Pet> pets = new HashSet<Pet>(); 
		pets.add(pet);
		
		final Field petsfield = owner.getClass().getDeclaredField("pets");
		petsfield.setAccessible(true);
		petsfield.set(owner, pets);
		
		Pet comperPet = new Pet();
		if ((ignoreNew &&  id != null) || !ignoreNew) {
			//check the lower
			if (petNameToSet.toLowerCase().equals(name.toLowerCase())) {
				comperPet = pet;
			}
			else {
				comperPet = null;
			}
		}
		// ignoreNew == true and id == null(new id)
		else {
			comperPet = null;
		}		
		
		assertEquals(owner.getPet(name, ignoreNew), comperPet);
		
	}
}