package be.afelio.software_academy.pco.controllers.countries;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.afelio.software_academy.pco.beans.Country;
import be.afelio.software_academy.pco.repository.DataRepository;

public class CountryController {

	protected DataRepository repository;

	public CountryController(DataRepository repository) {
		super();
		this.repository = repository;
	}
	
	public void list(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
			
		List<Country> list = repository.findAllCountries();
		request.setAttribute("countries", list);
		request.getRequestDispatcher("/WEB-INF/jsp/countries.jsp").forward(request, response);
	}
		
	public void add(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String abbreviation = request.getParameter("abbreviation");
		if (name != null && !name.isBlank() && abbreviation != null && !abbreviation.isBlank()) {
			repository.addCountry(name, abbreviation);
		}
		list(request, response);
	}
	
	public void delete(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String parameter = request.getParameter("id");
		if (parameter != null) {
			try {
				int id = Integer.parseInt(parameter);
				repository.deleteCountryById(id);
			} catch(NumberFormatException e) {}
		}
		list(request, response);
	}	
}
