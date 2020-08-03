package br.com.sga.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import br.com.sga.core.constantes.Constantes;
import br.com.sga.core.dto.ExampleMaskChildrenInputDTO;
import br.com.sga.core.formatter.DateCustomFormatter;
import br.com.sga.core.formatter.DateLongFormatter;
import br.com.sga.core.formatter.MoedaFormatter;
import br.com.sga.core.formatter.annotations.DateCustomFormat;
import br.com.sga.core.formatter.annotations.DateLongFormat;
import br.com.sga.core.formatter.annotations.MoedaFormat;
import br.com.sga.core.model.dominio.AtivoInativoEnum;
import br.com.sga.core.model.dominio.OpcaoDominio;

@Entity
@Table(name = "EXAMPLE_MASK")
public class ExampleMask implements Serializable {
	
	private static final long serialVersionUID = 7575404176175821468L;

	@Id
	@Column(name = "EXAMPLE_MASKID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CPF")
	private String cpf;
	
	@Column(name = "CNPJ")
	private String cnpj;

	@Column(name = "DATA_HORA")
	@DateCustomFormat(formato = DateCustomFormatter.Formato.DIA_MES_ANO_HORA)
	private Date dataHora;

	@Column(name = "DATA_INICIO")
	@DateCustomFormat(formato = DateCustomFormatter.Formato.DIA_MES_ANO)
	private Date dataInicio;

	@Column(name = "DATA_FIM")
	@DateCustomFormat(formato = DateCustomFormatter.Formato.DIA_MES_ANO)
	private Date dataFim;

	@Column(name = "ANO_MES_REFERENCIA")
	@DateLongFormat(formato = DateLongFormatter.Formato.MES_ANO)
	private Long anoMesReferencia;

	@Column(name = "SALARIO")
	@MoedaFormat(moeda = MoedaFormatter.Moeda.REAL)
	private Double salario;
	
	@Column(name = "PORCENTAGEM")
	private Double porcentagem;
	
	@Type(type = Constantes.ASSOCIACAO_TYPE, parameters = {@Parameter(name = Constantes.ENUM_CLASS_NAME, value = "OpcaoDominio")})
	@Column(name = "OPCAO_DOMINIO")
	private OpcaoDominio opcaoDominio;
	
	@Type(type = Constantes.ASSOCIACAO_TYPE, parameters = {@Parameter(name = Constantes.ENUM_CLASS_NAME, value = "AtivoInativoEnum")})
	@Column(name = "ATIVO_INATIVO")
	private AtivoInativoEnum ativoInativo;
	
	@OneToMany(mappedBy="exampleMask", cascade ={CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@OrderBy("id ASC")
	private List<ExampleMaskChildren> listExampleMaskChildren = new ArrayList<ExampleMaskChildren>();
	
	@Transient
	private ExampleMaskChildrenInputDTO exampleMaskChildrenInputDTO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Long getAnoMesReferencia() {
		return anoMesReferencia;
	}

	public void setAnoMesReferencia(Long anoMesReferencia) {
		this.anoMesReferencia = anoMesReferencia;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}
	
	public Double getPorcentagem() {
		return porcentagem;
	}

	public void setPorcentagem(Double porcentagem) {
		this.porcentagem = porcentagem;
	}

	public OpcaoDominio getOpcaoDominio() {
		return opcaoDominio;
	}

	public void setOpcaoDominio(OpcaoDominio opcaoDominio) {
		this.opcaoDominio = opcaoDominio;
	}

	public AtivoInativoEnum getAtivoInativo() {
		return ativoInativo;
	}

	public void setAtivoInativo(AtivoInativoEnum ativoInativo) {
		this.ativoInativo = ativoInativo;
	}

	public List<ExampleMaskChildren> getListExampleMaskChildren() {
		return listExampleMaskChildren;
	}

	public void setListExampleMaskChildren(List<ExampleMaskChildren> listExampleMaskChildren) {
		this.listExampleMaskChildren = listExampleMaskChildren;
	}

	public ExampleMaskChildrenInputDTO getExampleMaskChildrenInputDTO() {
		return exampleMaskChildrenInputDTO;
	}

	public void setExampleMaskChildrenInputDTO(ExampleMaskChildrenInputDTO exampleMaskChildrenInputDTO) {
		this.exampleMaskChildrenInputDTO = exampleMaskChildrenInputDTO;
	}
}
