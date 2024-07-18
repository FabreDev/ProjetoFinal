package com.fabre.PetHub.controller;

import com.fabre.PetHub.model.Dados;
import com.fabre.PetHub.model.Pet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets")
public class PetRestController {

    @PostMapping("/adicionar-pet")
    public ResponseEntity<Pet> adicionarPet(@RequestBody Pet pet) {
        pet.setId(Dados.listarPets(pet.getTutor_id()).size() + 1);
        Dados.adicionarPet(pet);
        return new ResponseEntity<>(pet, HttpStatus.CREATED);
    }

    @DeleteMapping("/excluir-pet/{id}")
    public ResponseEntity<Void> excluirPet(@PathVariable Integer id) {
        Pet petExistente = Dados.buscarPet(id);
        if (petExistente != null) {
            Dados.excluirPet(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
