package tn.pi.clientservice.dto;

public record ClientDTO(
        int id,
        String email,
        String firstName,
        String lastName,
        String telephone
) {
}