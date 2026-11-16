package br.com.adsuema.minifood.model;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "usersecurity")
public class Usuarios implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = false, length = 150)
    private String login;
    @Column(nullable = false, length = 255)
    private String senha;

    @Column(nullable = false)
    private String role;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cliente_id", unique = true)
    private Cliente cliente;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Garante que se a role salva no banco for "ADMIN" ou "ROLE_ADMIN", o Spring receba o formato correto
        String authority = this.role.startsWith("ROLE_") ? this.role : "ROLE_" + this.role;
        return List.of(new SimpleGrantedAuthority(authority));
    }

    @Override
    public @Nullable String getPassword() {
        return senha;
    }


    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

}
