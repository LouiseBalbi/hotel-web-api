package dev.hotel.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;

@RestController
public class Controller {
	
	
	ClientRepository clientRepository;
	
	public Controller(ClientRepository clientRepository) {
		super();
		this.clientRepository = clientRepository;
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = "clients"
	)
	// GET liste de tous les clients
	public List<Client> listeClients(
			@RequestParam Integer start,
			@RequestParam Integer size) {
		

		return clientRepository.findAll(PageRequest.of(start, size)).getContent();
	}

}
