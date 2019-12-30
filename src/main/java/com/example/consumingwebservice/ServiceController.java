package com.example.consumingwebservice;

import com.example.consumingwebservice.soap.CountryClient;
import com.example.consumingwebservice.wsdl.Country;
import com.example.consumingwebservice.wsdl.GetCountryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ServiceController {
    @Autowired
    CountryClient countryClient;
    /*@GetMapping("/greeting")
    public Country greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        GetCountryResponse response = countryClient.getCountry(name);
        Country myCountry = response.getCountry();
        String res = myCountry.getName() + " " +
        model.addAttribute("name", response.getCountry().getCurrency());
        return myCountry;
    }*/
    @RequestMapping(value = "/greeting/{name}", method = RequestMethod.GET)
    @ResponseBody
    public List<Country> welcome(@PathVariable("name") String name)
    {
        try
        {
            GetCountryResponse response = countryClient.getCountry(name);
            Country myCountry = response.getCountry();
            Country myCountry2 = new Country();
            myCountry2.setCapital("Bogota");
            myCountry2.setCurrency(myCountry.getCurrency());
            myCountry2.setName(myCountry.getName());
            myCountry2.setPopulation(myCountry.getPopulation());
            List<Country> myList = new ArrayList<>();
            myList.add(myCountry);
            myList.add(myCountry2);
            return myList;
        }
        catch (NullPointerException ex)
        {
            return new ArrayList<>();
        }
    }
    /*@Bean
	CommandLineRunner lookup(CountryClient quoteClient) {
		return args -> {
			String country = "Poland";

			if (args.length > 0) {
				country = args[0];
			}
			GetCountryResponse response = quoteClient.getCountry(country);
			System.err.println(response.getCountry().getCurrency());
		};
	}*/
}
