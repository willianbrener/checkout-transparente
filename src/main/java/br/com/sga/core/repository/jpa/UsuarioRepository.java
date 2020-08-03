package br.com.sga.core.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sga.core.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public Usuario findByNome(String nome);
}
