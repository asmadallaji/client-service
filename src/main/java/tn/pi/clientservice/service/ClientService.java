package tn.pi.clientservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import tn.pi.clientservice.dto.ClientDTO;
import tn.pi.clientservice.entities.Client;
import tn.pi.clientservice.mapper.ClientDTOMapper;
import tn.pi.clientservice.repository.ClientRepository;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ClientService {
    private final ClientDTOMapper clientDTOMapper;
    private final ClientRepository clientRepository;

    public ClientService(ClientDTOMapper clientDTOMapper, ClientRepository clientRepository) {
        this.clientDTOMapper = clientDTOMapper;
        this.clientRepository = clientRepository;
    }
    public void create(Client client){
        Client clientDansLaBDD = this.clientRepository.findByEmail(client.getEmail());
        if(clientDansLaBDD == null) {
            this.clientRepository.save(client);
        }
    }
    public Stream<ClientDTO> findAll() {
        return this.clientRepository.findAll()
                .stream().map(clientDTOMapper);
    }
    public Client lireOuCreer(Client clientAcreer){
        Client clientDansLaBDD = this.clientRepository.findByEmail(clientAcreer.getEmail());
        if(clientDansLaBDD == null) {
            clientDansLaBDD = this.clientRepository.save(clientAcreer);
        }
        return clientDansLaBDD;
    }
    public void update(int id, Client client) {
        Client clientDansLaBDD = this.getClient(id);
        if(clientDansLaBDD.getId() == client.getId()) {
            clientDansLaBDD.setEmail(client.getEmail());
            clientDansLaBDD.setTelephone(client.getTelephone());
            this.clientRepository.save(clientDansLaBDD);
        }
    }
    public Client getClient(int id) {
        Optional<Client> optionalClient = this.clientRepository.findById(id);
        return optionalClient.orElseThrow(
                () -> new EntityNotFoundException("Aucun client n'existe avec cet id")
        );
    }
    public void delete(int id) {
        Client clientDansLaBDD = this.getClient(id); // vérifie que le client existe
        this.clientRepository.delete(clientDansLaBDD);
    }
}
