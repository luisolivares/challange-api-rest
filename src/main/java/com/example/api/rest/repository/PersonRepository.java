package com.example.api.rest.repository;

import com.example.api.rest.model.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("personRepository")
public interface PersonRepository extends JpaRepository<Persona, Integer> {

    @Query(value = "SELECT p FROM Persona p WHERE  p.documento = :documento ")
    Optional<Persona> findByDocumento(@Param("documento") String documento);

    @Modifying
    @Query(value = "DELETE FROM Persona p WHERE p.documento = :documento  ")
    void deleteByDocumento(@Param("documento") String documento);

}
