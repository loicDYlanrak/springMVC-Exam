package com.exemple.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemple.model.Exemplaire;
import com.exemple.model.Livre;
import com.exemple.service.ExemplaireService;
import com.exemple.service.LivreService;
import com.exemple.service.PretService;

@RestController
@RequestMapping("/api/livre")
public class LivreApiController {
	@Autowired
	private LivreService livreService;
	
	@Autowired
	private PretService pretService;
	
	@Autowired
	private ExemplaireService exemplaireService;
	
	@GetMapping(value = "/info/{idLivre}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getLivreDetailsJson(@PathVariable Integer idLivre) {
		Livre livre = livreService.getLivreById(idLivre);
		if (livre == null) {
			return ResponseEntity.notFound().build();
		}
		
		List<Exemplaire> exemplaires = exemplaireService.getExemplairesByLivreId(idLivre);
		List<Map<String, Object>> exemplairesList = exemplaires.stream()
				.map(exemplaire -> {
					Map<String, Object> map = new HashMap<>();
					map.put("id_exemplaire", exemplaire.getId_exemplaire());
					map.put("disponible", pretService.getPretsEnCoursByExemplaire(exemplaire.getId_exemplaire()).isEmpty());
					return map;
				})
				.collect(Collectors.toList());
		
		Map<String, Object> response = new HashMap<>();
		response.put("livre", livre);
		response.put("exemplaires", exemplairesList);
		
		return ResponseEntity.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.body(response);
	}
}
