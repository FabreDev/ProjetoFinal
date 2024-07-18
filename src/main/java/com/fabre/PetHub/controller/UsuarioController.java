package com.fabre.PetHub.controller;

import com.fabre.PetHub.model.Dados;
import com.fabre.PetHub.model.Pet;
import com.fabre.PetHub.model.Usuario;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {

    @GetMapping("/")
    public String inicio() {
        return "index";
    }

    @GetMapping("/inicio")
    public String inicio2(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            Usuario usuario = Dados.buscarUsuario(id);
            if (usuario != null) {
                model.addAttribute("usuario", usuario);

                return "redirect:/menu?id=" + id;
            }
        }
        return "redirect:/";
    }

    @GetMapping("/inserir-usuario")
    public String cadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/gravar-usuario")
    public String processarForm(Model model, Usuario usuario) {
        if (usuario.getId() != null) {
            Dados.atualizarUsuario(usuario);
        } else {
            Dados.adicionarUsuario(usuario);
        }
        return "redirect:/menu?id=" + usuario.getId();
    }

    @GetMapping("/login")
    public String login(Model model, @RequestParam(required = false) String email) {
        model.addAttribute("email", email);
        return "index";
    }

    @GetMapping("/excluir-usuario")
    public String excluirUsuario(@RequestParam String id) {
        Integer idUsuario = Integer.parseInt(id);
        Dados.excluirUsuario(idUsuario);
        return "redirect:/listagem";
    }

    @GetMapping("/alterar-usuario")
    public String alterarUsuario(Model model, @RequestParam Integer id) {
        Usuario usuario = Dados.buscarUsuario(id);
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
            return "cadastro";
        }
        return "redirect:/";
    }

    @PostMapping("/validar-login")
    public String validarLogin(Model model, @RequestParam String email, @RequestParam String senha) {
        Usuario usuario = Dados.listarUsuarios().stream()
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
    public String menu(@RequestParam Integer id, Model model) {
        Usuario usuario = Dados.buscarUsuario(id);
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
            return "menu";
        }
        return "redirect:/";
    }

    @GetMapping("/busca")
    public String busca(@RequestParam(required = false) Integer id, Model model) {
        List<Usuario> usuarios = Dados.listarUsuarios().stream()
                .filter(usuario -> !usuario.getTipoUsuario().equals("Tutor"))
                .collect(Collectors.toList());

        if (id != null) {
            Usuario usuario = Dados.buscarUsuario(id);
            if (usuario != null) {
                model.addAttribute("usuario", usuario);
            }
        }

        model.addAttribute("usuarios", usuarios);
        return "busca";
    }

    @GetMapping("/pets")
    public String pets(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            Usuario usuario = Dados.buscarUsuario(id);
            if (usuario != null) {
                model.addAttribute("usuario", usuario);
                return "pets";
            }
        }
        return "redirect:/";
    }

    @GetMapping("/cadastroPet")
    public String cadastroPet(@RequestParam Integer id, Model model) {
        Usuario registroEncontrado = Dados.buscarUsuario(id);

        model.addAttribute("usuario", registroEncontrado);
        model.addAttribute("pets", "petsUsuario");
        model.addAttribute("novoPet", new Pet(null, "", "", 0, "", "", id));

        return "cadastroPet";
    }
}
