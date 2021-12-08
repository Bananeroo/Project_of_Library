package com.example.demo.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public void addNewClient(Client client) throws IllegalStateException{
        Optional<Client> studentOptional=null;
        studentOptional=clientRepository
                .findClientByEmail(client.getEmail().toLowerCase());
        if(studentOptional.isPresent()){
            System.out.println(studentOptional.toString());
            throw new IllegalStateException("Email used");
        }
        client.setAdminRole("user");
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
    }
    public List<Client> getClients(){
        return clientRepository.findAll();
    }



    public Client findClientByEmailNotOptional(String email){
        Client studentOptional=null;
        studentOptional=clientRepository.findClientByEmailNotOptional(email);
        if(studentOptional == null) {
            throw new IllegalStateException("Klient o takim Id nie istnieje");
        }
        return studentOptional;

    }

    public Optional<Client> findClientById(Long id){
        Optional<Client> studentOptional=null;
        studentOptional=clientRepository.findClientById(id);
        if(!studentOptional.isPresent()) {
            throw new IllegalStateException("Klient o takim Id nie istnieje");
        }
        return studentOptional;

    }

    public void SetUserRole(String role,Long id){
        clientRepository.setClient(role,id);
    }
    public void UpdateClient(String adminRole, String name, String password, String email, LocalDate dob, int age, Long id){
        clientRepository.setClientAll(adminRole,name,password,email,dob,age,id);
    }
    public void DeleteClient(Long id){
        clientRepository.deleteClient(id);
    }

}
