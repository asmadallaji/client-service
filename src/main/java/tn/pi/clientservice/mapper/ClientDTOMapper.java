package tn.pi.clientservice.mapper;

import org.springframework.stereotype.Component;
import tn.pi.clientservice.dto.ClientDTO;
import tn.pi.clientservice.entities.Client;

import java.util.function.Function;

@Component
public class ClientDTOMapper implements Function<Client, ClientDTO> {
    @Override
    public ClientDTO apply(Client client) {
        return new ClientDTO(
                client.getId(),
                client.getEmail(),
                client.getFirstName(),
                client.getLastName(),
                client.getTelephone()
        );
    }
}