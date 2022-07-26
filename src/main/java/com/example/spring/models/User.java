package com.example.spring.models;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String name;
    private String surname;
    private int age;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(el -> new SimpleGrantedAuthority("ROLE_" + el.getAuthority())).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setRoles(String roles) {
        if (roles != null && !roles.isEmpty()) {
            String[] arrayRoles = roles.split("[^A-Za-zФ-Яа-я0-9]");
            if (arrayRoles.length != 0) {
                Set<Role> roleSet = new HashSet<>();
                Arrays.stream(arrayRoles).forEach(el -> roleSet.add(new Role(el)));
                this.roles = roleSet;
            } else {
                this.roles = new HashSet<>();
                this.roles.add(new Role(roles));
            }
        }
    }

    public String showPrettyViewOfRoles() {
        return roles.stream().map(Role::getRole).collect(Collectors.joining(" | "));
    }

}