package com.example.demo.Client;

import com.example.demo.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/student")
public class ClientController {

    private final ClientService clientService;


    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }



    @GetMapping(path="/{clientId}")
    public Optional<Client> getClient(@PathVariable("clientId") Long clientId) throws Exception{
        if(clientId<1){
            throw new IllegalStateException("Client ID < 1");

        }
        return clientService.findClientById(clientId);
    }





}

