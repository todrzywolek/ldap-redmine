package pl.edu.agh.ldap.controllers;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;

@RestController
public class HomeController {

    @GetMapping("/")
    public String index(Principal principal) {
        System.out.println(principal.getName());
        SecurityContext context = SecurityContextHolder.getContext();
        Collection<? extends GrantedAuthority> authorities = context.getAuthentication().getAuthorities();
        for (GrantedAuthority a : authorities) {
            System.out.println(a.getAuthority());
        }
        return "Welcome to the home page!";
    }
}
