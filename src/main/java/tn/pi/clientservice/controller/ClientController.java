package tn.pi.clientservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.pi.clientservice.dto.ClientDTO;
import tn.pi.clientservice.entities.Client;
import tn.pi.clientservice.service.ClientService;

import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void create(@RequestBody Client client) {
        this.clientService.create(client);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Stream<ClientDTO> find() {
        return this.clientService.findAll();
    }

    @GetMapping(path="{id}", produces = APPLICATION_JSON_VALUE)
    public Client getClient(@PathVariable int id) {
        return  this.clientService.getClient(id);
    }

    @ResponseStatus(NO_CONTENT)
    @PutMapping(path = "{id}", consumes = APPLICATION_JSON_VALUE)
    public void update(@PathVariable int id, @RequestBody Client client) {

        this.clientService.update(id, client);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(path = "{id}")
    public void delete(@PathVariable int id) {
        this.clientService.delete(id);
    }
}
