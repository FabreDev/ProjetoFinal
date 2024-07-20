package com.fabre.PetHub.service;

import com.fabre.PetHub.model.Pet;
import com.fabre.PetHub.model.Usuario;
import com.fabre.PetHub.repository.PetRepository;
import com.fabre.PetHub.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Pet adicionarPet(Pet pet, Integer tutorId) {
        Usuario usuario = usuarioRepository.findById(tutorId).orElse(null);
        if (usuario != null) {
            pet.setTutor(tutorId);
            return petRepository.save(pet);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Pet buscarPetPorId(Integer id) {
        return petRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Pet> buscarPetsPorUsuarioId(Integer usuarioId) {

        return petRepository.findByTutor(usuarioId);
    }

    @Transactional
    public void excluirPet(Integer id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet não encontrado"));

        petRepository.deleteById(id);
    }
}
