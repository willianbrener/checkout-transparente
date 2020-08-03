package br.com.sga.web.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import br.com.sga.core.model.Usuario;
import br.com.sga.core.model.dominio.ZeroUmEnum;
import br.com.sga.core.repository.jpa.UsuarioRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String nome = auth.getName();
        String senhaPlana = auth.getCredentials().toString();
        
        Usuario usuario = usuarioRepository.findByNome(nome);
        
        if(usuario == null || usuario.getSenha() == null || !BCrypt.checkpw(senhaPlana, usuario.getSenha())) {
            throw new UsernameNotFoundException("Usuário e/ou Senha inválidos.");
        } else if(usuario.getAtivo() == null || usuario.getAtivo().equals(ZeroUmEnum.ZERO)) {
            throw new BadCredentialsException("O usuário está desativado.");
        }
        
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(nome, auth.getCredentials().toString(), grantedAuthorities);
        
        return authentication;
        
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}