package com.fabre.PetHub.controller;

import com.fabre.PetHub.model.Dados;
import com.fabre.PetHub.model.Pet;
import com.fabre.PetHub.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PetController {

    @PostMapping("/adicionar-pet")
    public String adicionarPet(@ModelAttribute("novoPet") Pet pet) {
        pet.setId(Dados.listarPets(pet.getTutor_id()).size() + 1);
        Dados.adicionarPet(pet);
        return "redirect:/pets?id=" + pet.getTutor_id();
    }

    @GetMapping("/excluir-pet")
    public String excluirPet(@RequestParam String id, @RequestParam String tutorId, Usuario usuario) {
        Integer idPet = Integer.parseInt(id);
        Integer idUsuario = Integer.parseInt(tutorId);
        Dados.excluirPet(idPet);
        return "redirect:/pets?id=" + usuario.getId();
    }

    @GetMapping("/alterar-pet")
    public String alterarPet(@RequestParam String id, @RequestParam String tutorId, Model model, Pet pet) {
        Integer idPet = Integer.parseInt(id);
        model.addAttribute("pet", Dados.buscarPet(idPet));
        return "redirect:/cadastroPet?id=" + pet.getId();
    }
}
