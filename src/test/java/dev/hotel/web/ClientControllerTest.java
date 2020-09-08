package dev.hotel.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;
import dev.hotel.service.ClientService;
import dev.hotel.web.client.ClientController;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ClientService clientService;


	@Test
	public void listerClients() throws Exception {
		Client c1 = new Client();
		c1.setNom("Nom 1");
		c1.setPrenoms("Prenoms 1");

		Client c2 = new Client();
		c2.setNom("Nom 2");
		c2.setPrenoms("Prenoms 2");
		
		Mockito.when(clientService.listerClients(0, 2))
		.thenReturn(Arrays.asList(c1, c2));
		

		mockMvc.perform(MockMvcRequestBuilders.get("/clients?start=0&size=2"))
				.andExpect(MockMvcResultMatchers.jsonPath("[0].nom").value("Nom 1"))
				.andExpect(MockMvcResultMatchers.jsonPath("[0].prenoms").value("Prenoms 1"))
				.andExpect(MockMvcResultMatchers.jsonPath("[1].nom").value("Nom 2"));
	}
	
	
	// Tests Get/clients/uuid
	
	@Test
	public void findByUuidTest() throws Exception {
		Client c1 = new Client("Dupont", "Jean");
		UUID id = UUID.randomUUID();
		c1.setUuid(id);
		
		Mockito.when(clientService.recupererClient(id)).thenReturn(Optional.of(c1));

	
		mockMvc.perform(MockMvcRequestBuilders.get("/clients/{uuid}", id))
		.andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("Dupont"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.prenoms").value("Jean"));
	}
	
	
	//Test Post/Clients
	
	@Test
	public void creerClientTest() throws Exception {
		Client c1 = new Client("Dupont", "Jean");
		
		Mockito.when(clientService.creerNouveauClient("Dupont", "Jean")).thenReturn(c1);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/clients")
		           .contentType(MediaType.APPLICATION_JSON)
		           .content("{ \"nom\": \"Dupont\", \"prenoms\": \"Jean\" }") 
		           .accept(MediaType.APPLICATION_JSON))
				   .andExpect(status().isOk());
			
	}
	

}
