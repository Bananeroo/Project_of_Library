package com.example.demo.Auth;

import com.example.demo.Client.Client;
import com.example.demo.Client.ClientService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import static com.example.demo.Security.ApplicationUserRole.*;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

@Repository("real")
public class RealApplicationUserDaoService implements ApplicationUserDao{
    private final ClientService clientService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RealApplicationUserDaoService(ClientService clientService, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers(){
        List<ApplicationUser> applicationUsers= Lists.newArrayList();
        ApplicationUser tmp;
        Client tmpClient;
        List<Client>  userList= clientService.getClients();
        Enumeration<Client> enm = Collections.enumeration(userList);
        while(enm.hasMoreElements()){
            tmpClient=enm.nextElement();
            if(tmpClient.getAdminRole().equals("admin")) {
                tmp = new ApplicationUser(
                        tmpClient.getPassword(),
                        tmpClient.getEmail().toLowerCase(),
                        ADMIN.getGrantedAuthority(),
                        true,
                        true,
                        true,
                        true
                );
            }
            else{
                tmp = new ApplicationUser(
                        tmpClient.getPassword(),
                        tmpClient.getEmail().toLowerCase(),
                        USER.getGrantedAuthority(),
                        true,
                        true,
                        true,
                        true
                );
            }
            applicationUsers.add(tmp);

        }





        return applicationUsers;
    }


}
