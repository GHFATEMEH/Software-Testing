package bddForPetService;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
	glue = "bddForPetService",
	tags = "not @ignore",
	monochrome = true,
	plugin = {"pretty", "summary" ,"html:target/cucumber/cucumber.html", "json:target/cucumber.json"},
	features = {"classpath:features/petService.feature"})
public class BDDEntry {

}



