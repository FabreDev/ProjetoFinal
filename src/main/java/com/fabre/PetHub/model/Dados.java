package com.fabre.PetHub.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Dados {

    private static final List<Usuario> listaUsuarios = new ArrayList<>();
    private static final List<Pet> listaPets = new ArrayList<>();

    public static void adicionarUsuario(Usuario usuario) {
        usuario.setId(listaUsuarios.size() + 1);
        listaUsuarios.add(usuario);
    }

    public static List<Usuario> listarUsuarios() {
        return listaUsuarios;
    }

    public static void excluirUsuario(Integer id) {
        listaUsuarios.removeIf(f -> f.getId().equals(id));
    }

    public static Usuario buscarUsuario(Integer id) {
        Usuario usuario = listaUsuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (usuario != null) {
            usuario.setId(id);
        }

        return usuario;
    }

    private static List<Pet> listarPetsDoUsuario(Integer idUsuario) {
        return listaPets.stream()
                .filter(pet -> idUsuario.equals(pet.getTutor()))
                .collect(Collectors.toList());
    }

    public static void atualizarUsuario(Usuario usuario) {
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getId().equals(usuario.getId())) {
                listaUsuarios.set(i, usuario);
                break;
            }
        }
    }

    public static void adicionarPet(Pet pet) {
        pet.setId(listaPets.size() + 1);
        listaPets.add(pet);
    }

    public static void excluirPet(Integer id) {
        listaPets.removeIf(a -> a.getId().equals(id));
    }

    public static List<Pet> listarPets(Integer tutor_id) {
        return listaPets.stream()
                .filter(pet -> pet.getTutor().equals(tutor_id))
                .collect(Collectors.toList());
    }

    public static Pet buscarPet(Integer id) {
        return listaPets.stream()
                .filter(pet -> pet.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
