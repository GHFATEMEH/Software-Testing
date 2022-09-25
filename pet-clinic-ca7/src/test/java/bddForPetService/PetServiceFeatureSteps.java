package bddForPetService;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.slf4j.Logger;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.Assert.assertEquals;

public class PetServiceFeatureSteps {
	@Autowired
	PetService petService;

	@Autowired
	PetTimedCache pets;

	@Autowired
	OwnerRepository owners;

	@Autowired
	Logger criticalLogger;

	@Autowired
	PetTypeRepository petTypeRepository;

	private PetType petType;
	private Owner gholam;
	private Owner gholi;
	private Pet pet;
	private Owner findOwner;
	private Pet newPet;
	private Pet petFound;
	private Pet savePet;

	@Before("@sample_annotation")
	public void setup() {
		// sample setup code
	}

	@Given("There is a pet owner called {string}")
	public void thereIsAPetOwnerCalled(String name) {
		gholam = new Owner();
		gholam.setFirstName("Amu");
		gholam.setLastName("Gholam");
		gholam.setAddress("Najibie - Kooche shahid abbas alavi");
		gholam.setCity("Tehran");
		gholam.setTelephone("09191919223");
		gholam.setId(1);
		owners.save(gholam);
	}

	// save pet
	@When("He performs save pet service to add a pet to his list")
	public void hePerformsSavePetService() {
		pet = new Pet();
		pet.setType(petType);
		petService.savePet(pet, gholam);
	}

	@Then("The pet is saved successfully")
	public void petIsSaved() {
		assertNotNull(petService.findPet(petType.getId()));
	}

	@And("The pet is saved for owner successfully")
	public void petIsSavedForOwner() {
		List<Pet> petsOfOwner = gholam.getPets();
		assertEquals("Check pet add to owner", pet, petsOfOwner.get(0));
	}

	// find owner
	@Given("There is a owner called {string}")
	public void thereIsOwnerCalled(String name) {
		gholi = new Owner();
		gholi.setFirstName("Amu");
		gholi.setLastName("Gholi");
		gholi.setAddress("Najibie - Kooche shahid abbas alavi");
		gholi.setCity("Tehran");
		gholi.setTelephone("09191919223");
		gholi.setId(2);
		owners.save(gholi);
	}

	@When("He performs find owner of {int}")
	public void hePerformsFindOwnerOfPet(int ownerId) {
		findOwner = petService.findOwner(ownerId);
	}

	@Then("The owner is found successfully")
	public void ownerIsFound() {
		assertEquals("owner is found", "Gholi", findOwner.getLastName());
	}

	// new pet
	@When("He performs new pet")
	public void hePerformsNewPet() {
		newPet = petService.newPet(gholam);
	}

	@Then("The new pet is added successfully")
	public void newPetAdded() {
		assertEquals("new pet is added", newPet, gholam.getPets().get(0));
	}

	@Given("There is a pet")
	public void thereIsAPet() {
		System.out.println("Givennnn start");

		savePet = new Pet();
		savePet.setType(petType);
		savePet.setId(1);

		Owner hasan = new Owner();
		hasan.setFirstName("Amu");
		hasan.setLastName("hasan");
		hasan.setAddress("Najibie - Kooche shahid abbas alavi");
		hasan.setCity("Tehran");
		hasan.setTelephone("09191919223");
		hasan.setId(3);
		hasan.addPet(savePet);

		pets.save(savePet);
		System.out.println("Givennnn end");

	}

	// find pet
	@When("He performs find pet {int}")
	public void hePerformsFindPet(int petId) {
		petFound = petService.findPet(petId);
	}

	@Then("The pet is found successfully")
	public void findPet() {
		Integer id = 1;
		assertEquals("pet is found", id, petFound.getId());
	}

	@Given("There is some predefined pet types like {string}")
	public void thereIsSomePredefinedPetTypesLike(String petTypeName) {
		petType = new PetType();
		petType.setName(petTypeName);
		petTypeRepository.save(petType);
	}
}
