package dev.hotel.web;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;


@RestController
public class Controller {
	
	private static final Logger LOG = LoggerFactory.getLogger(Controller.class);
	
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
	
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = "clients/{uuid}"
	)

	public ResponseEntity<Client> findByUuid(@PathVariable UUID uuid){
		Optional<Client> optClient = this.clientRepository.findById(uuid);
		
		if(optClient.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optClient.get());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

		}

	}


}
