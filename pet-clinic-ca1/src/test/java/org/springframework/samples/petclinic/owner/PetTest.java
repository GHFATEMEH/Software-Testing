package org.springframework.samples.petclinic.owner;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.samples.petclinic.visit.Visit;

import static org.junit.Assert.*;
import static org.junit.Assume.*;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
//import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.List;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.stream.Collectors;

@RunWith(Theories.class)
public class PetTest {

	Pet pet;
	
	@Before
	public void setUp() throws Exception {
		pet = new Pet();
	}
	
	// input of function and condition
	
	@DataPoints
	public static Set[] visitSetsForPet = {new LinkedHashSet<>(Arrays.asList(new Visit(), new Visit()))};

	@Theory
	public void testGetVisits(Set<Visit> visitsPet) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		assumeTrue(visitsPet != null);
		
		Iterator<Visit> testIterator = visitsPet.iterator();
		List<Visit> testDateVisitPets = new ArrayList<>();
		
		// convert Set to List
		while(testIterator.hasNext()) {
			testDateVisitPets.add(testIterator.next());
		}
		// Sort Visit class based on date field reverse mode
		testDateVisitPets.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
		
		
		// set visit to check method
		final Field fieldOfPet = pet.getClass().getDeclaredField("visits");
		fieldOfPet.setAccessible(true);
		fieldOfPet.set(pet, visitsPet);
		
		// check method getVisits
		List<Visit> visitList = pet.getVisits();
		
	    assertArrayEquals(visitList.toArray(), testDateVisitPets.toArray());
	}

}