package com.fabre.PetHub.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Dados {

    private static final List<Usuario> listaUsuarios = new ArrayList<>();
    private static final List<Pet> listaPets = new ArrayList<>();

    static {

        Usuario usuario1 = new Usuario(1, "João", "Silva", "Tutor", "01-01-1990", "123.456.789-00", null, "123456789", "12345-678", "Rua A", "123", "Apto 101", "Centro", "São Paulo", "SP", "joao@email.com", "senha123", null);
        Usuario usuario2 = new Usuario(2, "Maria", "Souza", "Petsitter", "05-10-1985", "123.456.789-01", null, "987654321", "54321-876", "Rua B", "456", null, "Bairro X", "Rio de Janeiro", "RJ", "maria@email.com", "senha456", null);
        Usuario usuario3 = new Usuario(3, "Pedro", "Oliveira", "Dogwalker", "05-10-1985", "123.456.789-02", null, "876543210", "98765-432", "Rua C", "789", null, "Bairro Y", "Porto Alegre", "RS", "pedro@email.com", "senha789", null);
        Usuario usuario4 = new Usuario(4, "Ana", "Fernandes", "Clínica Estética", "05-10-1985", "123.456.789-03", "12345678901234", "654321098", "45678-901", "Av. Principal", "987", null, "Centro", "Brasília", "DF", "ana@email.com", "senha987", null);
        Usuario usuario5 = new Usuario(5, "Carlos", "Santos", "Tutor", "05-10-1985", "987.654.321-00", null, "234567890", "54321-987", "Rua D", "234", null, "Bairro Z", "Salvador", "BA", "carlos@email.com", "senha321", null);
        Usuario usuario6 = new Usuario(6, "Juliana", "Martins", "Pethoster", "05-10-1985", "123.456.789-04", null, "876543210", "87654-321", "Rua E", "567", null, "Bairro W", "Curitiba", "PR", "juliana@email.com", "senha654", null);
        Usuario usuario7 = new Usuario(7, "Fernando", "Costa", "Dogwalker", "05-10-1985", "123.456.789-05", null, "765432109", "76543-210", "Rua F", "890", null, "Bairro V", "Fortaleza", "CE", "fernando@email.com", "senha987", null);
        Usuario usuario8 = new Usuario(8, "Mariana", "Rodrigues", "Petsitter", "05-10-1985", "123.456.789-06", null, "654321098", "65432-109", "Rua G", "1234", null, "Bairro U", "Recife", "PE", "mariana@email.com", "senha789", null);
        Usuario usuario9 = new Usuario(9, "Paulo", "Almeida", "Clínica Estética", "05-10-1985", "123.456.789-07", "98765432109876", "543210987", "98765-432", "Av. Secundária", "5678", null, "Centro", "Manaus", "AM", "paulo@email.com", "senha456", null);

        listaUsuarios.add(usuario1);
        listaUsuarios.add(usuario2);
        listaUsuarios.add(usuario3);
        listaUsuarios.add(usuario4);
        listaUsuarios.add(usuario5);
        listaUsuarios.add(usuario6);
        listaUsuarios.add(usuario7);
        listaUsuarios.add(usuario8);
        listaUsuarios.add(usuario9);
    }

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
            usuario.setPets(listarPetsDoUsuario(id));
        }

        return usuario;
    }

    private static List<Pet> listarPetsDoUsuario(Integer idUsuario) {
        return listaPets.stream()
                .filter(pet -> idUsuario.equals(pet.getTutor_id()))
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
                .filter(pet -> pet.getTutor_id().equals(tutor_id))
                .collect(Collectors.toList());
    }

    public static Pet buscarPet(Integer id) {
        return listaPets.stream()
                .filter(pet -> pet.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
