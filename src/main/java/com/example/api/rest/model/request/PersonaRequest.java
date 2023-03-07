package com.example.api.rest.model.request;

import com.example.api.rest.model.enumerated.Sexo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaRequest {

    @NotBlank(message = "El campo nombres es obligatorio")
    @NotNull(message = "El campo nombres es obligatorio")
    private String nombres;

    @NotBlank(message = "El campo apellidos es obligatorio")
    @NotNull(message = "El campo apellidos es obligatorio")
    private String apellidos;

    @NotNull(message = "El campo sexo es obligatorio")
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @NotBlank(message = "El campo documento es obligatorio")
    @NotNull(message = "El campo documento es obligatorio")
    private String documento;

    @NotNull(message = "El campo empleado es obligatorio")
    private boolean esEmpleado;

}