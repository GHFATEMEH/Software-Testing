package org.springframework.samples.petclinic.model.priceCalculators;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.UserType;

public class CustomerDependentPriceCalculatorTest {

	CustomerDependentPriceCalculator customerDependentPriceCalculator;
	@Before
	public void setUp() throws Exception {
		customerDependentPriceCalculator = new CustomerDependentPriceCalculator();
	}

	//1,2,3,12,17,16
	@Test
	public void testJustReturn() {
		List<Pet> pets = new ArrayList<>();
		double baseCharge = 1.0;
		double basePricePerPet = 1.0;
		double result = customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, UserType.NEW);
		assertEquals(0.0, result, 0);
	}

	//1,2,3,4,5,7,11,3,12,17,16
	@Test
	public void testForIfIf() {
		List<Pet> pets = new ArrayList<>();
		Pet pet1 = new Pet();
		PetType type = new PetType();
		pet1.setType(type);
		Date date = new Date();
		pet1.setBirthDate(date);
		pets.add(pet1);
		double baseCharge = 1.0;
		double basePricePerPet = 1.0;
		double result = customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, UserType.NEW);
		assertEquals(1.68, result, 0);
	}

	//1,2,3,4,5,8,11,3,12,17,16
	@Test
	public void testForIfElse() {
		List<Pet> pets = new ArrayList<>();
		Pet pet1 = new Pet();
		PetType type = new PetType();
		pet1.setType(type);
		Date date = new Date(2323223232L);
		pet1.setBirthDate(date);
		pets.add(pet1);
		double baseCharge = 1.0;
		double basePricePerPet = 1.0;
		double result = customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, UserType.NEW);
		assertEquals(1.2, result, 0);
	}

	//1,2,3,12,17,18,16
	@Test
	public void testNotForNotIfElseIf() {
		List<Pet> pets = new ArrayList<>();
		double baseCharge = 1.0;
		double basePricePerPet = 1.0;
		double result = customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, UserType.GOLD);
		assertEquals(1.0, result, 0);
	}

	//1,2,3,4,...,12,13,14,16
	@Test
	public void testNotForIfIf() {
		List<Pet> pets = new ArrayList<>();
		PetType type = new PetType();
		Date date = new Date();

		Pet pet1 = new Pet();
		pet1.setType(type);
		pet1.setBirthDate(date);
		pets.add(pet1);

		Pet pet2 = new Pet();
		pet2.setType(type);
		pet2.setBirthDate(date);
		pets.add(pet2);

		Pet pet3 = new Pet();
		pet3.setType(type);
		pet3.setBirthDate(date);
		pets.add(pet3);

		Pet pet4 = new Pet();
		pet4.setType(type);
		pet4.setBirthDate(date);
		pets.add(pet4);

		Pet pet5 = new Pet();
		pet5.setType(type);
		pet5.setBirthDate(date);
		pets.add(pet5);

		double baseCharge = 1.0;
		double basePricePerPet = 1.0;
		double result = customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, UserType.NEW);
		assertEquals(8.98, result, 0);
	}

	//1,2,3,4,...,12,13,15,16
	@Test
	public void testNotForIfNotIf() {
		List<Pet> pets = new ArrayList<>();
		PetType type = new PetType();
		Date date = new Date();

		Pet pet1 = new Pet();
		pet1.setType(type);
		pet1.setBirthDate(date);
		pets.add(pet1);

		Pet pet2 = new Pet();
		pet2.setType(type);
		pet2.setBirthDate(date);
		pets.add(pet2);

		Pet pet3 = new Pet();
		pet3.setType(type);
		pet3.setBirthDate(date);
		pets.add(pet3);

		Pet pet4 = new Pet();
		pet4.setType(type);
		pet4.setBirthDate(date);
		pets.add(pet4);

		Pet pet5 = new Pet();
		pet5.setType(type);
		pet5.setBirthDate(date);
		pets.add(pet5);

		double baseCharge = 1.0;
		double basePricePerPet = 1.0;
		double result = customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, UserType.GOLD);
		assertEquals(7.5200000000000005, result, 0);
	}

	//1,2,3,4,6,9,10,11,3,12,17,16
	@Test
	public void testForElseIf() {
		List<Pet> pets = new ArrayList<>();
		Date date = new Date();
		PetType type = Mockito.mock(PetType.class);
		Pet pet1 = Mockito.mock(Pet.class);
		when(type.getRare()).thenReturn(false);
		when(pet1.getType()).thenReturn(type);
		when(pet1.getBirthDate()).thenReturn(date);
		pets.add(pet1);
		double baseCharge = 1.0;
		double basePricePerPet = 1.0;
		double result = customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, UserType.NEW);
		assertEquals(1.2, result, 0);
	}

	//1,2,3,4,6,10,11,3,12,17,16
	@Test
	public void testForElseNotIf() {
		List<Pet> pets = new ArrayList<>();
		Date date = new Date(2323223232L);
		PetType type = Mockito.mock(PetType.class);
		Pet pet1 = Mockito.mock(Pet.class);
		when(type.getRare()).thenReturn(false);
		when(pet1.getType()).thenReturn(type);
		when(pet1.getBirthDate()).thenReturn(date);
		pets.add(pet1);
		double baseCharge = 1.0;
		double basePricePerPet = 1.0;
		double result = customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet, UserType.NEW);
		assertEquals(1.0, result, 0);
	}

}
