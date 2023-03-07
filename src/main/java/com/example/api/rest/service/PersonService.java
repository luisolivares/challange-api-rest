package com.example.api.rest.service;

import com.example.api.rest.model.entity.Persona;
import com.example.api.rest.model.request.PersonaRequest;

import java.util.List;

public interface PersonService {

	public List<Persona> listAll(Integer pageNo, Integer pageSize);

	public Persona create(PersonaRequest request);

	public Persona update(PersonaRequest request, String documento);

	public Persona findByDocumento(String documento);

	public void delete(String documento);

}
