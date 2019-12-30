package com.example.consumingwebservice.config;

import java.util.ArrayList;
import java.util.List;

import com.example.consumingwebservice.soap.CountryClient;
import com.example.consumingwebservice.wsdl.Country;
import com.example.consumingwebservice.wsdl.GetCountryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    CountryClient countryClient;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            GetCountryResponse response = countryClient.getCountry(username);
            Country myCountry = response.getCountry();
            System.out.println(myCountry.getName());
            String pass = bcryptEncoder.encode(myCountry.getCapital());
            return new User(myCountry.getName(), pass,
                    new ArrayList<>());
        }
        catch (NullPointerException ex)
        {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}