package be.afelio.software_academy.pco.controllers;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.afelio.software_academy.pco.controllers.contacts.ContactController;
import be.afelio.software_academy.pco.controllers.countries.CountryController;
import be.afelio.software_academy.pco.controllers.tags.TagController;
import be.afelio.software_academy.pco.repository.DataRepository;

public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 6916575889603944259L;
	
	protected TagController tagController;
	protected CountryController countryController;
	protected ContactController contactController;

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("org.postgresql.Driver");
			String url = config.getInitParameter("database.url");
			String user = config.getInitParameter("database.user");
			String password = config.getInitParameter("database.password");
			DataRepository repository = new DataRepository(url, user, password);
			tagController = new TagController(repository);
			countryController = new CountryController(repository);
			contactController = new ContactController(repository);
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("what_to_do");
		System.out.println("FrontController.doGet() => " + action);
		
		switch(action) {
			case "tag_list":
				tagController.list(request);
				request.getRequestDispatcher("/WEB-INF/jsp/tags.jsp").forward(request, response);
				break;
			case "country_list":
				countryController.list(request);
				request.getRequestDispatcher("/WEB-INF/jsp/countries.jsp").forward(request, response);
				break;
			case "country_delete":
				countryController.delete(request);
				request.getRequestDispatcher("/WEB-INF/jsp/countries.jsp").forward(request, response);
				break;
			case "contact_list":
				contactController.list(request);
				request.getRequestDispatcher("/WEB-INF/jsp/contacts.jsp").forward(request, response);
				break;
			case "contact_delete":
				contactController.delete(request);
				request.getRequestDispatcher("/WEB-INF/jsp/contacts.jsp").forward(request, response);
				break;				
			default:
				response.setStatus(404);	
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("what_to_do");
		System.out.println("FrontController.doPost() => " + action);
		
		switch(action) {
			case "tag_add":
				tagController.add(request);
				request.getRequestDispatcher("/WEB-INF/jsp/tags.jsp").forward(request, response);
				break;			
			case "country_add":
					countryController.add(request);
					request.getRequestDispatcher("/WEB-INF/jsp/countries.jsp").forward(request, response);
					break;
			case "contact_add":
					contactController.add(request);
					request.getRequestDispatcher("/WEB-INF/jsp/contacts.jsp").forward(request, response);
					break;					
			case "tag_delete":
				tagController.delete(request);
				request.getRequestDispatcher("/WEB-INF/jsp/tags.jsp").forward(request, response);
				break;
			default:
				response.setStatus(404);	
		}
	}
	
}
