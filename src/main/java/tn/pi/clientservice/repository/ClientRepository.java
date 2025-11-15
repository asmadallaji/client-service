package tn.pi.clientservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.pi.clientservice.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    /**
     * Find client by email
     */
    Optional<Client> findByEmail(String email);

    /**
     * Find client by username
     */
    Optional<Client> findByUsername(String username);

    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);

    /**
     * Check if username exists
     */
    boolean existsByUsername(String username);

    /**
     * Find all clients by role
     */
    List<Client> findByRole(String role);

    /**
     * Find all clients by status
     */
    List<Client> findByStatus(String status);

    /**
     * Check if both email and username exist (for validation)
     */
    boolean existsByEmailOrUsername(String email, String username);
}
