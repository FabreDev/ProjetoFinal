package com.fabre.PetHub.repository;

import com.fabre.PetHub.model.Pet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

    List<Pet> findByTutor(Integer tutor);

}
