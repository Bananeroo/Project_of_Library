package com.example.demo.Client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository
        extends JpaRepository<Client,Long> {

    Optional<Client> findClientByEmail(String email);
    @Query("SELECT u FROM Client u WHERE u.email = ?1 ")
    Client findClientByEmailNotOptional(String email);
    Optional<Client> findClientById(Long id);
    Optional<Client> findAllByName(String name);
    @Modifying
    @Query("update Client u set u.adminRole = ?1 where u.id = ?2")
    void setClient(String adminRole, Long id);

    @Modifying
    @Query("update Client u set u.adminRole = ?1 ,u.name = ?2 ,u.password = ?3 ,u.email = ?4 , u.dob = ?5 , u.age = ?6 where u.id = ?7")
    void setClientAll(String adminRole, String name,String password,String email,LocalDate dob,int age,Long id);
    @Modifying
    @Query("delete Client u  where u.id = ?1")
    void deleteClient( Long id);
}
