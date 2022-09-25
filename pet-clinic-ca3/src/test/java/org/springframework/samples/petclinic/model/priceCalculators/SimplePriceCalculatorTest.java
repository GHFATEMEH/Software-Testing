package org.springframework.samples.petclinic.model.priceCalculators;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.UserType;


public class SimplePriceCalculatorTest {

	SimplePriceCalculator simplePriceCalculator;

	@Before
	public void setUp() throws Exception {
		simplePriceCalculator = new SimplePriceCalculator();
	}

	//1,2,3,8,10
	@Test
	public void testNotLoopNotIf() {
		List<Pet> pets = new ArrayList<>();
		double baseCharge = 10.0;
		double basePricePerPet = 1.0;
		double result = simplePriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, UserType.SILVER);
		assertEquals(10.0, result, 0);
	}

	//1,2,3,8,9,10
	@Test
	public void testNotLoopIf() {
		List<Pet> pets = new ArrayList<>();
		double baseCharge = 10;
		double basePricePerPet = 1.0;
		double result = simplePriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, UserType.NEW);
		assertEquals(9.5, result, 0);
	}

	//1,2,3,4,5,7,3,8,10
	@Test
	public void testLoopIfNotIf() {
		List<Pet> pets = new ArrayList<>();
		Pet pet1 = new Pet();
		PetType type = new PetType();
		pet1.setType(type);
		pets.add(pet1);
		double baseCharge = 10.0;
		double basePricePerPet = 1.0;
		double result = simplePriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, UserType.SILVER);
		assertEquals(11.2, result, 0);
	}

	//1,2,3,4,6,7,3,8,10
	@Test
	public void testLoopElseNotIf() {
		List<Pet> pets = new ArrayList<>();
		PetType type = Mockito.mock(PetType.class);
		Pet pet1 = Mockito.mock(Pet.class);
		when(type.getRare()).thenReturn(false);
		when(pet1.getType()).thenReturn(type);
		pets.add(pet1);
		double baseCharge = 10.0;
		double basePricePerPet = 1.0;
		double result = simplePriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, UserType.SILVER);
		assertEquals(11.0, result, 0);
	}
}
