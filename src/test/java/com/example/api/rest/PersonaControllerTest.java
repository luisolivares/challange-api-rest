package com.example.api.rest;

import com.example.api.rest.model.entity.Persona;
import com.example.api.rest.model.enumerated.Sexo;
import com.example.api.rest.model.request.PersonaRequest;
import com.example.api.rest.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonaControllerTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @MockBean
    private PersonService personaService;

    @Autowired
    public PersonaControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    @DisplayName("Listado de personas con paginación.")
    @Order(2)
    public void lisAll() throws Exception {

        Persona persona1 = new Persona(1, "Luis Alberto", "Olivares Peña", Sexo.MASCULINO, "20390708", true);
        Persona persona2 = new Persona(2, "Ana", "Vazquez Peña", Sexo.FEMENINO, "42142152", true);
        Persona persona3 = new Persona(3, "Luis Oswaldo", "Olivares", Sexo.MASCULINO, "20390708", true);
        Persona persona4 = new Persona(5, "Luisana", "Olivares Peña", Sexo.FEMENINO, "20390708", true);

        List<Persona> users = Arrays.asList(persona1, persona2, persona3, persona4);

        when(personaService.listAll(1, 2)).thenReturn(users);

        mockMvc.perform(get("/api/v1/personas/{pageNo}/{pageSize}", 0, 2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(personaService, times(1)).listAll(0, 2);
        verifyNoMoreInteractions(personaService);

    }

    @Test
    @DisplayName("Creación de un registro de persona.")
    @Order(1)
    public void create() throws Exception {
        Persona personaRes = new Persona(1, "Luis Alberto", "Olivares Peña", Sexo.MASCULINO, "20390708", true);

        PersonaRequest request = new PersonaRequest();
        request.setNombres("Luis Alberto");
        request.setApellidos("Olivares Peña");
        request.setSexo(Sexo.MASCULINO);
        request.setDocumento("20390708");

        when(personaService.create(any(PersonaRequest.class))).thenReturn(personaRes);

        mockMvc.perform(post("/api/v1/personas/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.documento").value("20390708"));
    }


    @Test
    @DisplayName("Modificación de un registro de persona.")
    @Order(3)
    public void update() throws Exception {

        Persona personaRes = new Persona(1, "Luis Alberto", "Olivares Peña", Sexo.MASCULINO, "20390708", true);

        PersonaRequest request = new PersonaRequest();
        request.setNombres("Luis Oswaldo");
        request.setApellidos("Olivares Peña");
        request.setSexo(Sexo.MASCULINO);
        request.setDocumento("20390708");
        mockMvc.perform(
                        put("/api/v1/personas/{documento}", "18390608")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Eliminación de un registro de persona.")
    @Order(4)
    public void delete() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/personas/{documento}", "18390608"))
                .andExpect(status().isNoContent());

    }

}
