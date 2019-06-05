package be.afelio.software_academy.pco.controllers.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.afelio.software_academy.pco.beans.Tag;
import be.afelio.software_academy.pco.repository.DataRepository;

public class TagController {

	protected DataRepository repository;

	public TagController(DataRepository repository) {
		super();
		this.repository = repository;
	}
	
	public void list(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		List<Tag> list = repository.findAllTags();
		request.setAttribute("tags", list);
		request.getRequestDispatcher("/WEB-INF/jsp/tags.jsp").forward(request, response);
	}
	
	public void add(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String parameter = request.getParameter("value");
		if (parameter != null) {
			repository.addTag(parameter);
		}
		list(request, response);
	}
	
	public void delete(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String parameter = request.getParameter("id");
		if (parameter != null) {
			try {
				int id = Integer.parseInt(parameter);
				repository.deleteTagById(id);
			} catch(NumberFormatException e) {}
		}
		list(request, response);
	}
}
