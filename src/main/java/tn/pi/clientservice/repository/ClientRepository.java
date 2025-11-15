package tn.pi.clientservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.pi.clientservice.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByEmail(String email);
    Client findByFirstNameAndLastName(String firstName, String lastName);
}
