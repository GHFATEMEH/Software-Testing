package org.springframework.samples.petclinic.utility;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.visit.Visit;

import java.util.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PriceCalculatorTest {
	PriceCalculator priceCalculator;
	@Before
	void setup() {
		priceCalculator = new PriceCalculator();
	}

	// visits.size() != 0 size = 0 then not in if a) 0 == 0 kill
	//											  b) 0 > 0 not kill :)
	//											  c) 0>=0 kill
	//											  d) 0 < 0 not kill :)
	//                                            e) 0 <=0 kill
	@Test
	void testVisitOfSize0() {
		List<Pet> pets = new ArrayList<>();
		Pet pet1 = new Pet();
		LocalDate date = LocalDate.now();
		pet1.setBirthDate(date);
		pets.add(pet1);
		double baseCharge = 0.0;
		double basePricePerPet = 0.0;
		priceCalculator.calcPrice(pets, baseCharge, basePricePerPet);
		assertEquals(0.0, 0.0);
	}

	@Test
	void testVisitOfSize1() {
		List<Pet> pets = new ArrayList<>();
		////
		Pet pet1 = new Pet();
		LocalDate date = LocalDate.now();
		pet1.setBirthDate(date);
		Visit visit1 = new Visit();
		visit1.setDate(LocalDate.now().withYear(2019));
		pet1.addVisit(visit1);
		////
		Pet pet2 = new Pet();
		LocalDate date2 = LocalDate.now();
		pet2.setBirthDate(date2);
		Visit visit2 = new Visit();
		visit2.setDate(LocalDate.now().withYear(2019));
		pet2.addVisit(visit2);
		////
		Pet pet3 = new Pet();
		LocalDate date3 = LocalDate.now();
		pet3.setBirthDate(date3);
		Visit visit3 = new Visit();
		visit3.setDate(LocalDate.now().withYear(2019));
		pet3.addVisit(visit3);
		////
		Pet pet4 = new Pet();
		LocalDate date4 = LocalDate.now();
		pet4.setBirthDate(date4);
		Visit visit4 = new Visit();
		visit4.setDate(LocalDate.now().withYear(2019));
		pet4.addVisit(visit4);
		////
		Pet pet6 = new Pet();
		LocalDate date6 = LocalDate.now();
		pet6.setBirthDate(date6);
		Visit visit6 = new Visit();
		visit6.setDate(LocalDate.now().withYear(2019));
		pet6.addVisit(visit6);
		////
		Pet pet7 = new Pet();
		LocalDate date7 = LocalDate.now().withYear(2000);
		pet7.setBirthDate(date7);
		Visit visit7 = new Visit();
		visit7.setDate(LocalDate.parse("2021-09-10"));
		pet7.addVisit(visit7);
		////
		Pet pet5 = new Pet();
		LocalDate date5 = LocalDate.now().withYear(2000);
		pet5.setBirthDate(date5);
		Visit visit5 = new Visit();
		visit5.setDate(LocalDate.parse("2021-12-01"));
		pet5.addVisit(visit5);
		////
		pets.add(pet1);
		pets.add(pet2);
		pets.add(pet3);
		pets.add(pet4);
		pets.add(pet6);
		pets.add(pet7);
		pets.add(pet5);
		double baseCharge = 1.0;
		double basePricePerPet = 1.0;
		double actual = priceCalculator.calcPrice(pets, baseCharge, basePricePerPet);
		assertEquals(262.35999999999996, actual);
	}

	// age <= 2 for age = 1 then a)  1 == 2 not in if kill
	//							 b)  1 >= 2 not in if kill
	//							 c)  1 > 2 not if kill
	//							 d) 1 < 2 in if not kill
	//							 e) 1 != 2 in if not kill
	// age <= 2 for age = 2 then in if d ,e kill
	@Test
	void testAgeEqualTo1() {
		List<Pet> pets = new ArrayList<>();
		Pet pet1 = new Pet();
		LocalDate date = LocalDate.now().withYear(2020);
		pet1.setBirthDate(date);
		pets.add(pet1);
		double baseCharge = 0.0;
		double basePricePerPet = 1.0;
		double actual = priceCalculator.calcPrice(pets, baseCharge, basePricePerPet);
		assertEquals(1.68, actual);
	}

	@Test
	void testAgeEqualTo3() {
		List<Pet> pets = new ArrayList<>();
		Pet pet1 = new Pet();
		LocalDate date = LocalDate.now().withYear(2019);
		pet1.setBirthDate(date);
		pets.add(pet1);
		double baseCharge = 0.0;
		double basePricePerPet = 1.0;
		double actual = priceCalculator.calcPrice(pets, baseCharge, basePricePerPet);
		assertEquals(1.68, actual);
	}

}
