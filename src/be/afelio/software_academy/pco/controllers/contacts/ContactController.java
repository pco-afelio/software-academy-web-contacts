package be.afelio.software_academy.pco.controllers.contacts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.afelio.software_academy.pco.beans.Contact;
import be.afelio.software_academy.pco.beans.Country;
import be.afelio.software_academy.pco.beans.Tag;
import be.afelio.software_academy.pco.repository.DataRepository;

public class ContactController {

	protected DataRepository repository;

	public ContactController(DataRepository repository) {
		super();
		this.repository = repository;
	}
	
	public void list(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
			
		List<Contact> contacts = repository.findAllContact();
		List<Country> countries = repository.findAllCountries();
		List<Tag> tags = repository.findAllTags();
		request.setAttribute("contacts", contacts);
		request.setAttribute("countries", countries);
		request.setAttribute("tags", tags);
		request.getRequestDispatcher("/WEB-INF/jsp/contacts.jsp").forward(request, response);
	}
		
	public void add(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String firstname = request.getParameter("firstname");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		if (name != null && !name.isBlank() 
				&& firstname != null && !firstname.isBlank() 
				&& email != null && !email.isBlank()
		) {
			String countryIdAsString = request.getParameter("countryId");
			Integer countryId = null;
			if (countryIdAsString != null && !"none".equalsIgnoreCase(countryIdAsString)) {
				try {
					countryId = Integer.parseInt(countryIdAsString);
				} catch(NumberFormatException e) {}
			}
			
			String[] tagIdsAsString = request.getParameterValues("tagIds");
			List<Integer> tagIds = new ArrayList<>();
			if (tagIdsAsString != null 
					&& !(tagIdsAsString.length == 1 && "none".equalsIgnoreCase(tagIdsAsString[0]))) {
				for (String s : tagIdsAsString) {
					try {
						tagIds.add(Integer.parseInt(s));
					} catch(NumberFormatException e) {}
				}
			}
			repository.addContact(firstname, name, email, countryId, tagIds.toArray(new Integer[] {}));
		}
		
		list(request, response);
	}
	
	public void delete(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String parameter = request.getParameter("id");
		if (parameter != null) {
			try {
				int id = Integer.parseInt(parameter);
				repository.deleteContactById(id);
			} catch(NumberFormatException e) {}
		}
		list(request, response);
	}
}
