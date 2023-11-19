package com.example.uspokajamlekbackend.doctor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByInvitationCode(String invitationCode);

    boolean existsByEmail(String email);
    boolean existsByEmailAndPassword(String email, String password);

    Doctor getById(long id);

    Doctor getByEmail(String email);
}
