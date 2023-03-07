package com.example.api.rest.model.entity;

import com.example.api.rest.model.enumerated.Sexo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Persona")
@Table(name = "persona")
public class Persona implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String PATTER_DNI ="[\\s]*[0-9]*[0-9]+";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Integer id;

	@Column(name = "nombres")
	private String nombres;

	@Column(name = "apellidos")
	private String apellidos;

	@Column(name = "sexo")
	@Enumerated(EnumType.STRING)
	private Sexo sexo;

	@Pattern(regexp = PATTER_DNI, message="En el campo DNI debe ingresar un valor numerico")
	@Column(name = "documento")
	private String documento;

	@Column(name = "empleado")
	private boolean esEmpleado;
}