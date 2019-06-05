package be.afelio.software_academy.pco.controllers.tags;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import be.afelio.software_academy.pco.beans.Tag;
import be.afelio.software_academy.pco.repository.DataRepository;

public class TagController {

	protected DataRepository repository;

	public TagController(DataRepository repository) {
		super();
		this.repository = repository;
	}
	
	public void list(HttpServletRequest request) {
		
		List<Tag> list = repository.findAllTags();
		request.setAttribute("tags", list);
		
	}
	
	public void add(HttpServletRequest request) {
		
		String parameter = request.getParameter("value");
		if (parameter != null) {
			repository.addTag(parameter);
		}
		list(request);
	}
	
	public void delete(HttpServletRequest request) {
		
		String parameter = request.getParameter("id");
		if (parameter != null) {
			try {
				int id = Integer.parseInt(parameter);
				repository.deleteTagById(id);
			} catch(NumberFormatException e) {}
		}
		list(request);
	}
}
