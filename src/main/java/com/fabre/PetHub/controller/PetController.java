package com.fabre.PetHub.controller;

import com.fabre.PetHub.model.Pet;
import com.fabre.PetHub.model.Usuario;
import com.fabre.PetHub.repository.UsuarioRepository;
import com.fabre.PetHub.service.PetService;
import com.fabre.PetHub.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PetController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PetService petService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping("/adicionar-pet")
    public String adicionarPet(@ModelAttribute("novoPet") Pet novoPet, @RequestParam Integer tutorId) {
        petService.adicionarPet(novoPet, tutorId);
        return "redirect:/pets?id=" + tutorId;
    }

    @GetMapping("/cadastroPet")
    public String exibirFormularioCadastroPet(@RequestParam(name = "id") Integer id, Model model) {
        Pet novoPet = new Pet();
        model.addAttribute("novoPet", novoPet);

        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        model.addAttribute("usuario", usuario);

        return "cadastroPet";
    }

    @GetMapping("/excluir-pet")
    public String excluirPet(@RequestParam Integer id, @RequestParam Integer usuarioId) {
        petService.excluirPet(id);
        return "redirect:/pets?id=" + usuarioId;
    }
}
