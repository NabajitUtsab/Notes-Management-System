package com.example.Notes_Management_Api.service;

import com.example.Notes_Management_Api.entity.AppUser;
import com.example.Notes_Management_Api.repository.AppUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepo appUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = appUserRepo.findByUsername(username).orElseThrow();

        return User.builder().
                username(username).
                password(appUser.getPassword()).
                authorities(appUser.getRoles().
                        stream().
                        map(role -> new SimpleGrantedAuthority(role.toUpperCase())).toList()).
                build();
    }

}

