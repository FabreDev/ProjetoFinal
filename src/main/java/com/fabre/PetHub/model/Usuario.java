package com.fabre.PetHub.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private Integer id;
    private String nome;
    private String sobrenome;
    private String tipoUsuario;
    private String dataNascimento;
    private String cpf;
    private String cnpj;
    private String telefone;
    private String cep;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String email;
    private String senha;
    private List<Pet> pets;
}
