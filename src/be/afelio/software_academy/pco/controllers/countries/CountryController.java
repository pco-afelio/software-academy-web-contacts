package be.afelio.software_academy.pco.controllers.countries;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import be.afelio.software_academy.pco.beans.Country;
import be.afelio.software_academy.pco.repository.DataRepository;

public class CountryController {

	protected DataRepository repository;

	public CountryController(DataRepository repository) {
		super();
		this.repository = repository;
	}
	
	public void list(HttpServletRequest request) {
			
		List<Country> list = repository.findAllCountries();
		request.setAttribute("countries", list);
	}
		
	public void add(HttpServletRequest request) {
		
		String name = request.getParameter("name");
		String abbreviation = request.getParameter("abbreviation");
		if (name != null && !name.isBlank() && abbreviation != null && !abbreviation.isBlank()) {
			repository.addCountry(name, abbreviation);
		}
		list(request);
	}
	
	public void delete(HttpServletRequest request) {
		
		String parameter = request.getParameter("id");
		if (parameter != null) {
			try {
				int id = Integer.parseInt(parameter);
				repository.deleteCountryById(id);
			} catch(NumberFormatException e) {}
		}
		list(request);
	}	
}
