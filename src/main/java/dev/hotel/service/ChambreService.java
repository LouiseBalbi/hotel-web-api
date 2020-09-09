package dev.hotel.service;

import java.util.Optional;
import java.util.UUID;

import dev.hotel.entite.Chambre;
import dev.hotel.repository.ChambreRepository;


public class ChambreService {
	
	private ChambreRepository cRep;

	/**
	 * @param cRep
	 */
	public ChambreService(ChambreRepository cRep) {
		this.cRep = cRep;
	}

	public Optional<Chambre> recupererChambre(UUID uuid) {
		return cRep.findById(uuid);
	}

}
