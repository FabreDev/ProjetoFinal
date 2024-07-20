package com.fabre.PetHub.controller;

import com.fabre.PetHub.model.Pet;
import com.fabre.PetHub.service.PetService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets")
public class PetRestController {

    private final PetService petService;

    public PetRestController(PetService petService) {
        this.petService = petService;
    }

        @PostMapping
    public ResponseEntity<Pet> adicionarPet(@RequestBody Pet pet) {
        if (pet.getTutor() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Pet novoPet = petService.adicionarPet(pet, pet.getTutor());
        if (novoPet != null) {
            return new ResponseEntity<>(novoPet, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> buscarPet(@PathVariable Integer id) {
        Pet pet = petService.buscarPetPorId(id);
        if (pet != null) {
            return new ResponseEntity<>(pet, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPet(@PathVariable Integer id) {
        if (petService.buscarPetPorId(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        petService.excluirPet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Pet>> listarPetsPorUsuario(@PathVariable Integer id) {
        List<Pet> pets = petService.buscarPetsPorUsuarioId(id);
        if (pets != null && !pets.isEmpty()) {
            return new ResponseEntity<>(pets, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
