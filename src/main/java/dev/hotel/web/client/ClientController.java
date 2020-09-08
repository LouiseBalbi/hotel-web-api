package dev.hotel.web.client;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;
import dev.hotel.service.ClientService;


@RestController
@RequestMapping("clients")
public class ClientController {
	
	private ClientService clientService;
	
	public ClientController(ClientService clientService) {
		super();
		this.clientService = clientService;
	}
	
	
	//@RequestMapping(method = RequestMethod.GET, path = "clients")
	@GetMapping
	// GET liste de tous les clients
	public List<Client> listeClients(
			@RequestParam Integer start,
			@RequestParam Integer size) {

		return clientService.listerClients(start, size);
	}
	
	
	//@RequestMapping(method = RequestMethod.GET, path = "clients/{uuid}")
	@GetMapping("{uuid}")
	public ResponseEntity<?> findByUuid(@PathVariable UUID uuid){

		Optional<Client> optClient = clientService.recupererClient(uuid);

		if (optClient.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optClient);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Veuillez entrer un autre identifiant client");
		}
	}
	
	
	@PostMapping
	public ResponseEntity<?> creerClient(@RequestBody @Valid CreerClientRequestDto client, BindingResult resultatValidation) {

		if (resultatValidation.hasErrors()) {
			return ResponseEntity.badRequest().body("Erreur");
		}

		return ResponseEntity.ok(new CreerClientResponseDto(clientService.creerNouveauClient(client.getNom(), client.getPrenoms())));
	}
	


}
