package tn.pi.clientservice.mapper;

import org.springframework.stereotype.Component;
import tn.pi.clientservice.dto.ClientDTO;
import tn.pi.clientservice.entities.Client;
import tn.pi.clientservice.enums.UserRole;


@Component
public class ClientDTOMapper {

    /**
     * Convert Client entity to ClientDTO
     */
    public ClientDTO mapToDTO(Client client) {
        if (client == null) {
            return null;
        }

        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setFirstName(client.getFirstName());
        dto.setLastName(client.getLastName());
        dto.setEmail(client.getEmail());
        dto.setPhone(client.getPhone());
        dto.setUsername(client.getUsername());
        dto.setAddress(client.getAddress());
        dto.setRole(client.getRole());
        dto.setStatus(client.getStatus());
        dto.setCreatedAt(client.getCreatedAt());
        dto.setUpdatedAt(client.getUpdatedAt());

        return dto;
    }

    /**
     * Convert ClientDTO to Client entity
     */
    public Client mapToEntity(ClientDTO dto) {
        if (dto == null) {
            return null;
        }

        Client client = new Client();
        client.setId(dto.getId());
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setEmail(dto.getEmail());
        client.setPhone(dto.getPhone());
        client.setUsername(dto.getUsername());
        client.setAddress(dto.getAddress());

        if (dto.getRole() != null) {
            client.setRole(dto.getRole());
        }
        if (dto.getStatus() != null) {
            client.setStatus(dto.getStatus());
        }

        return client;
    }

    /**
     * Update existing Client entity with DTO values
     */
    public Client updateClientFromDTO(ClientDTO dto, Client client) {
        if (dto.getFirstName() != null) {
            client.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            client.setLastName(dto.getLastName());
        }
        if (dto.getPhone() != null) {
            client.setPhone(dto.getPhone());
        }
        if (dto.getAddress() != null) {
            client.setAddress(dto.getAddress());
        }
        if (dto.getRole() != null) {
            client.setRole(dto.getRole());
        }
        if (dto.getStatus() != null) {
            client.setStatus(dto.getStatus());
        }

        return client;
    }
}