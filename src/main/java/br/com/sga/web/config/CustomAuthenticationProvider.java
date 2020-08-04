package br.com.sga.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sga.core.repository.jpa.UsuarioRepository;

@Component
public class CustomAuthenticationProvider/* implements AuthenticationProvider*/ {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

  /*  @Override
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
    }*/

}