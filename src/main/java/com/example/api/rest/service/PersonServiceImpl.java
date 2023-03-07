package com.example.api.rest.service;

import com.example.api.rest.model.entity.Persona;
import com.example.api.rest.model.request.PersonaRequest;
import com.example.api.rest.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("personServiceImpl")
public class PersonServiceImpl implements PersonService {

    @Qualifier("personRepository")
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Persona> listAll(Integer pageNo, Integer pageSize) {
        String sortBy = "documento";
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Persona> pagedResult = personRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    @Override
    public Persona create(PersonaRequest request) {
        Optional<Persona> persona = personRepository.findByDocumento(request.getDocumento());
        if (!persona.isPresent()) {
            try {
                Persona person = new Persona();
                person.setNombres(request.getNombres());
                person.setApellidos(request.getApellidos());
                person.setSexo(request.getSexo());
                person.setDocumento(request.getDocumento());
                person.setEsEmpleado(request.isEsEmpleado());
                return personRepository.save(person);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear una persona", e);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Ya existe un usuario con el DNI %s", request.getDocumento()));
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    @Override
    public Persona update(PersonaRequest request, String documento) {
        Optional<Persona> persona = personRepository.findByDocumento(documento);
        if (persona.isPresent()) {
            persona.get().setNombres(request.getNombres());
            persona.get().setApellidos(request.getApellidos());
            persona.get().setSexo(request.getSexo());
            persona.get().setDocumento(request.getDocumento());
            persona.get().setEsEmpleado(request.isEsEmpleado());
            return personRepository.saveAndFlush(persona.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Error al modificar la persona con el DNI %s", request.getDocumento()));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Persona findByDocumento(String documento) {
        Optional<Persona> persona = personRepository.findByDocumento(documento);
        if (persona.isPresent()) {
            return persona.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Error al modificar la persona con el DNI %s", documento));
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    @Override
    public void delete(String documento) {
        Optional<Persona> persona = personRepository.findByDocumento(documento);
        if (persona.isPresent()) {
            personRepository.deleteByDocumento(documento);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Error al eliminar la persona con el DNI %s", documento));
        }
    }

}
