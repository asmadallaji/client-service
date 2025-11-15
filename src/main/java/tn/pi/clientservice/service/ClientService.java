package tn.pi.clientservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.pi.clientservice.dto.ClientDTO;
import tn.pi.clientservice.entities.Client;
import tn.pi.clientservice.enums.UserRole;
import tn.pi.clientservice.enums.UserStatus;
import tn.pi.clientservice.exception.ResourceAlreadyExistsException;
import tn.pi.clientservice.exception.ResourceNotFoundException;
import tn.pi.clientservice.mapper.ClientDTOMapper;
import tn.pi.clientservice.repository.ClientRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientDTOMapper clientDTOMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register a new client
     */
    public ClientDTO registerClient(ClientDTO clientDTO) {
        // Validate email uniqueness
        if (clientRepository.existsByEmail(clientDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists: " + clientDTO.getEmail());
        }

        // Validate username uniqueness
        if (clientRepository.existsByUsername(clientDTO.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already exists: " + clientDTO.getUsername());
        }

        // Create new client entity
        Client client = new Client();
        client.setFirstName(clientDTO.getFirstName().trim());
        client.setLastName(clientDTO.getLastName().trim());
        client.setEmail(clientDTO.getEmail().trim().toLowerCase());
        client.setPhone(clientDTO.getPhone() != null ? clientDTO.getPhone().trim() : null);
        client.setUsername(clientDTO.getUsername().trim());
        client.setAddress(clientDTO.getAddress() != null ? clientDTO.getAddress().trim() : null);

        // Hash password using BCrypt
        client.setPassword(passwordEncoder.encode(clientDTO.getPassword()));

        // Set default role if not provided
        if (clientDTO.getRole() != null) {
            client.setRole(clientDTO.getRole());
        } else {
            client.setRole(UserRole.CLIENT);
        }

        // Set default status to ACTIVE
        client.setStatus(UserStatus.ACTIVE);

        // Save client to database
        Client savedClient = clientRepository.save(client);

        return clientDTOMapper.mapToDTO(savedClient);
    }

    /**
     * Get client by ID
     */
    public ClientDTO getClientById(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        return clientDTOMapper.mapToDTO(client);
    }

    /**
     * Get client by email
     */
    public ClientDTO getClientByEmail(String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with email: " + email));
        return clientDTOMapper.mapToDTO(client);
    }

    /**
     * Get client by username
     */
    public ClientDTO getClientByUsername(String username) {
        Client client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with username: " + username));
        return clientDTOMapper.mapToDTO(client);
    }

    /**
     * Get all clients
     */
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(clientDTOMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update client
     */
    public ClientDTO updateClient(Integer id, ClientDTO clientDTO) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));

        // Check if email is being changed and if new email already exists
        if (clientDTO.getEmail() != null && !clientDTO.getEmail().equals(client.getEmail())) {
            if (clientRepository.existsByEmail(clientDTO.getEmail())) {
                throw new ResourceAlreadyExistsException("Email already exists: " + clientDTO.getEmail());
            }
        }

        // Check if username is being changed and if new username already exists
        if (clientDTO.getUsername() != null && !clientDTO.getUsername().equals(client.getUsername())) {
            if (clientRepository.existsByUsername(clientDTO.getUsername())) {
                throw new ResourceAlreadyExistsException("Username already exists: " + clientDTO.getUsername());
            }
        }

        // Update client from DTO
        clientDTOMapper.updateClientFromDTO(clientDTO, client);

        Client updatedClient = clientRepository.save(client);
        return clientDTOMapper.mapToDTO(updatedClient);
    }

    /**
     * Delete client
     */
    public void deleteClient(Integer id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client not found with id: " + id);
        }
        clientRepository.deleteById(id);
    }

    /**
     * Change password
     */
    public void changePassword(Integer id, String oldPassword, String newPassword) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));

        if (!passwordEncoder.matches(oldPassword, client.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        client.setPassword(passwordEncoder.encode(newPassword));
        clientRepository.save(client);
    }

    /**
     * Get all clients by role
     */
    public List<ClientDTO> getClientsByRole(UserRole role) {
        return clientRepository.findByRole(role.name())
                .stream()
                .map(clientDTOMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get all clients by status
     */
    public List<ClientDTO> getClientsByStatus(UserStatus status) {
        return clientRepository.findByStatus(status.name())
                .stream()
                .map(clientDTOMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Check if email exists
     */
    public boolean emailExists(String email) {
        return clientRepository.existsByEmail(email);
    }

    /**
     * Check if username exists
     */
    public boolean usernameExists(String username) {
        return clientRepository.existsByUsername(username);
    }
}
