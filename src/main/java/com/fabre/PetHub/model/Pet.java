package com.fabre.PetHub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

    private Integer id;
    private String tipoPet;
    private String nome;
    private Integer idade;
    private String raca;
    private String corPredominante;
    private Integer tutor_id;
}
