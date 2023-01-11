package com.venkat.Controller;

import com.venkat.Model.Client;
import com.venkat.Repository.ClientRepository;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientsController {
    private final ClientRepository clientRepository;

    public ClientsController(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public List<Client> getClient(){
        return clientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable Long id){
        return clientRepository.findById(id).orElseThrow(RuntimeException::new);
    }

//    @PostMapping( produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) throws URISyntaxException{
        Client savedClient = clientRepository.save(client);
        return ResponseEntity.created(new URI("/clientsIamJustTesting/" + savedClient.getId())).body(savedClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client){
        Client currentClient = clientRepository.findById(id).orElseThrow(RuntimeException::new);
        currentClient.setId(id);
        currentClient.setName(client.getName());
        currentClient.setEmail(client.getEmail());
        currentClient = clientRepository.save(client);
        return ResponseEntity.ok(currentClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteClient(@PathVariable Long id) {
        clientRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
