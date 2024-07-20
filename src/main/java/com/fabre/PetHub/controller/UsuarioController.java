package com.fabre.PetHub.controller;

import com.fabre.PetHub.model.Pet;
import com.fabre.PetHub.model.Usuario;
import com.fabre.PetHub.repository.UsuarioRepository;
import com.fabre.PetHub.service.PetService;
import com.fabre.PetHub.service.UsuarioService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PetService petService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/")
    public String inicio() {
        return "index";
    }

    @GetMapping("/inicio")
    public String inicio2(@CookieValue(name = "pref-estilo", defaultValue = "light") String tema, Model model) {
        model.addAttribute("css", tema);
        return "index";
    }

    @GetMapping("/busca")
    public String lista(Model model, @RequestParam(name = "id", required = false) Integer id) {
        List<Usuario> usuarios = usuarioService.buscarTodosUsuarios().stream()
                .filter(usuario -> !usuario.getTipoUsuario().equals("Tutor"))
                .collect(Collectors.toList());

        model.addAttribute("usuarios", usuarios);

        if (id != null) {
            Usuario usuario = usuarioService.buscarUsuarioPorId(id).orElse(null);
            model.addAttribute("usuario", usuario);
        } else {
            model.addAttribute("usuario", new Usuario());
        }

        return "busca";
    }

    @GetMapping("/cadastro")
    public String cadastro(String tema, Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/gravar-usuario")
    public String processarForm(@ModelAttribute Usuario usuario) {
        usuarioService.salvarUsuario(usuario);
        return "redirect:/menu?id=" + usuario.getId();
    }

    @GetMapping("/pets")
    public String mostrarPets(Model model, @RequestParam String id) {
        Integer idUsuario = Integer.parseInt(id);
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario).orElse(null);
        List<Pet> pets = petService.buscarPetsPorUsuarioId(idUsuario);

        model.addAttribute("usuario", usuario);
        model.addAttribute("pets", pets);
        Pet novoPet = new Pet();
        novoPet.setTutor(idUsuario);
        model.addAttribute("novoPet", novoPet);
        return "pets";
    }

    @GetMapping("/excluir-usuario")
    public String excluirUsuario(@RequestParam String id) {
        Integer idUsuario = Integer.parseInt(id);
        usuarioService.excluirUsuario(idUsuario);
        return "redirect:/cadastro";
    }

    @GetMapping("/alterar-usuario")
    public String alterarUsuario(Model model, @RequestParam String id) {
        Integer idUsuario = Integer.parseInt(id);
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario).orElse(null);
        model.addAttribute("usuario", usuario);
        return "cadastro";
    }

    @GetMapping("/login")
    public String login(Model model, @RequestParam(required = false) String email) {
        model.addAttribute("email", email);
        return "index";
    }

    @PostMapping("/validar-login")
    public String validarLogin(Model model, @RequestParam String email, @RequestParam String senha) {
        Usuario usuario = usuarioService.buscarTodosUsuarios().stream()
                .filter(u -> u.getEmail().equals(email) && u.getSenha().equals(senha))
                .findFirst()
                .orElse(null);

        if (usuario != null) {
            return "redirect:/menu?id=" + usuario.getId();
        } else {
            model.addAttribute("erro", "Email ou senha incorretos.");
            model.addAttribute("email", email);
            return "login";
        }
    }

    @GetMapping("/menu")
    public String mostrarMenu(@RequestParam(required = false) Integer id, Model model) {
        if (id == null) {
            // Redireciona para a página de login ou exibe uma mensagem de erro
            return "redirect:/login";
        }

        Usuario usuario = usuarioService.buscarUsuarioPorId(id).orElse(null);
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
            return "menu";
        } else {
            return "redirect:/login";
        }
    }
}
