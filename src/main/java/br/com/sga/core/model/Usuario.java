package br.com.sga.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import br.com.sga.core.constantes.Constantes;
import br.com.sga.core.model.dominio.ZeroUmEnum;

@Entity
@Table(	name = "USUARIO")
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 4357132009229152366L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_USUARIO")
	private Long id;

	@Column(name = "NOME_USUARIO")
	private String nome;

	@Column(name = "SENHA_USUARIO")
	private String senha;

	@Type(type = Constantes.ASSOCIACAO_TYPE, parameters = {@Parameter(name = Constantes.ENUM_CLASS_NAME, value = "ZeroUmEnum")})
	@Column(name = "ATIVO_INATIVO")
	private ZeroUmEnum ativo;
	
	@Column(name = "DATA_ACESSO_USUARIO")
	private Date dataUltimoAcesso;
	
	@Column(name = "DATA_ALTERACAO_SENHA_USUARIO")
	private Date dataUltimaAlteracaoSenha;

	@Column(name = "IP_USUARIO")
	private String numrIp;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public ZeroUmEnum getAtivo() {
		return ativo;
	}

	public void setAtivo(ZeroUmEnum ativo) {
		this.ativo = ativo;
	}

	public Date getDataUltimoAcesso() {
		return dataUltimoAcesso;
	}

	public void setDataUltimoAcesso(Date dataUltimoAcesso) {
		this.dataUltimoAcesso = dataUltimoAcesso;
	}

	public Date getDataUltimaAlteracaoSenha() {
		return dataUltimaAlteracaoSenha;
	}

	public void setDataUltimaAlteracaoSenha(Date dataUltimaAlteracaoSenha) {
		this.dataUltimaAlteracaoSenha = dataUltimaAlteracaoSenha;
	}

	public String getNumrIp() {
		return numrIp;
	}

	public void setNumrIp(String numrIp) {
		this.numrIp = numrIp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
