package com.example.uspokajamlekbackend.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient getByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);

    Patient getByEmail(String email);

}
