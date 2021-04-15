package com.telerikfinalproject.photocontest.security;

import com.telerikfinalproject.photocontest.models.Credential;
import com.telerikfinalproject.photocontest.services.contracts.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final CredentialService credentialService;

    @Autowired
    public MyUserDetailsService(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Credential> user = credentialService.getCredentialByUsername(username);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

        return user.map(MyUserDetails::new).get();
    }
}
