package com.example.demo.Client;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table
public class Client implements Serializable {
    @Id
    @SequenceGenerator(
            name = "client_sequence",
            sequenceName = "client_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "client_sequence"
    )
    private Long id;
    private String adminRole;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "Email is mandatory")
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    @NotNull(message = "Age is mandatory")
    @Min(value = 18,message = "Age can't be less than 18")
    private Integer age;

    public Client() {
    }

    public Client(Long id,
                  String adminRole,
                  String name,
                  String password,
                  String email,
                  LocalDate dob,
                  Integer age
                  ) {
        this.id = id;
        this.adminRole=adminRole;
        this.name = name;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.age = age;
    }

    public Client(String adminRole,
                  String name,
                  String password,
                  String email,
                  LocalDate dob,
                  Integer age
                  ) {
        this.adminRole=adminRole;
        this.name = name;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.age = age;
    }
    public Client(String name) {
        this.name = name;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Integer getAge() { return age; }

    public String getAdminRole() {
        return adminRole;
    }

    public void setAdminRole(String adminRole) {
        this.adminRole = adminRole;
    }

    public void setAge(Integer age) {
        this.age = age;
    }



    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + password + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", age=" + age +

                '}';
    }
}
