package com.example.demo.Client;

import com.example.demo.Book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("management/api/v1/student")
public class ClientManagementController {
    private final ClientService clientService;


    @Autowired
    public ClientManagementController(ClientService clientService) {
        this.clientService = clientService;
    }
    @GetMapping
//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public List<Client> getClients(){
        return clientService.getClients();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public void registerNewClient(@RequestBody Client client){
        System.out.println("Zarejestrowano"+client);
    }
    @DeleteMapping(path="{studentId}")
    @PreAuthorize("hasAuthority('admin:write')")
    public void deleteClient(@PathVariable("studentId") Long studentId){
        System.out.println("Usunieto"+studentId);
    }

    @PutMapping(path="{studentId}")
    @PreAuthorize("hasAuthority('admin:write')")
    public void updateClient(@PathVariable("studentId")Long studentId,@RequestBody Client client){
        System.out.println("Uaktualniono: "+studentId+" o : ");
        System.out.println(client);
    }


}
