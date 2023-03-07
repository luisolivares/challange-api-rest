package com.example.api.rest.controller;

import com.example.api.rest.model.entity.Persona;
import com.example.api.rest.model.request.PersonaRequest;
import com.example.api.rest.service.PersonService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping(value = "/api/v1/personas")
public class PersonaController {

	private final PersonService personaService;

	public PersonaController(PersonService personaService) {
		this.personaService = personaService;
	}

	@GetMapping(value = "/{pageNo}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Persona>> listAllPaginate(@PathVariable("pageNo") Integer pageNo,
														 @PathVariable("pageSize") Integer pageSize) {
		return new ResponseEntity<>(this.personaService.listAll(pageNo, pageSize), HttpStatus.OK);
	}

	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Persona> create(@Valid @RequestBody PersonaRequest persona) {
		return new ResponseEntity<>(personaService.create(persona), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{documento}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Persona> update(@Valid @RequestBody PersonaRequest request, @PathVariable("documento") String documento) {
		return new ResponseEntity<>(personaService.update(request, documento), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{documento}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> delete(@PathVariable("documento") String documento) {
		personaService.delete(documento);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}