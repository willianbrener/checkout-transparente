package br.com.sga;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.sga.core.model.Country;
import br.com.sga.core.model.ExampleMask;
import br.com.sga.core.model.ExampleMaskChildren;
import br.com.sga.core.model.Location;
import br.com.sga.core.model.Region;
import br.com.sga.core.model.Usuario;
import br.com.sga.core.model.dominio.AtivoInativoEnum;
import br.com.sga.core.model.dominio.OpcaoDominio;
import br.com.sga.core.model.dominio.ZeroUmEnum;
import br.com.sga.core.repository.jpa.CountryRepository;
import br.com.sga.core.repository.jpa.ExampleMaskChildrenRepository;
import br.com.sga.core.repository.jpa.ExampleMaskRepository;
import br.com.sga.core.repository.jpa.LocationRepository;
import br.com.sga.core.repository.jpa.RegionRepository;
import br.com.sga.core.repository.jpa.UsuarioRepository;
import br.com.sga.core.util.DateUtil;


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = { "br.com.sga" })
@EnableJpaRepositories(basePackages = "br.com.sga.core.repository")
@EnableTransactionManagement
@EntityScan(basePackages = "br.com.sga.core.model")
public class SpringBootRun extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRun.class, args);
	}

    @Bean
    CommandLineRunner initDatabase(
			PasswordEncoder passwordEncoder,
			UsuarioRepository usuarioRepositorio,
    		RegionRepository regionRepository, 
    		CountryRepository countryRepository, 
    		LocationRepository locationRepository,
    		ExampleMaskRepository emaskRepository,
    		ExampleMaskChildrenRepository exampleMaskChildrenRepository
		) {
    	
        return args -> {
        	
        	//INSERT USUARIOS
        	Usuario admin = new Usuario();
        	admin.setNome("admin");
        	admin.setSenha(passwordEncoder.encode("123456"));
        	admin.setAtivo(ZeroUmEnum.UM);
        	usuarioRepositorio.save(admin);
			
        	Usuario user = new Usuario();
        	user.setNome("user");
        	user.setSenha(passwordEncoder.encode("123456"));
        	user.setAtivo(ZeroUmEnum.UM);
        	usuarioRepositorio.save(user);
        	
        	Usuario user1 = new Usuario();
        	user1.setNome("1");
        	user1.setSenha(passwordEncoder.encode("1"));
        	user1.setAtivo(ZeroUmEnum.UM);
        	usuarioRepositorio.save(user1);
        	
        	//INSERT REGIONS
        	Region america = new Region();
        	america.setName("America");
        	regionRepository.save(america);

        	Region europa = new Region();
        	europa.setName("Europa");
        	regionRepository.save(europa);

        	Region africa = new Region();
        	africa.setName("Africa");
        	regionRepository.save(africa);
        	
        	//INSERT COUNTRIES
        	Country brasil = new Country();
        	brasil.setName("Brasil");
        	brasil.setRegion(america);
        	countryRepository.save(brasil);
        	
        	Country argentina = new Country();
        	argentina.setName("Argentina");
        	argentina.setRegion(america);
        	countryRepository.save(argentina);
        	
        	Country chile = new Country();
        	chile.setName("Chile");
        	chile.setRegion(america);
        	countryRepository.save(chile);
        	
        	Country colombia = new Country();
        	colombia.setName("Colômbia");
        	colombia.setRegion(america);
        	countryRepository.save(colombia);
        	
        	Country eua = new Country();
        	eua.setName("Estados Unidos");
        	eua.setRegion(america);
        	countryRepository.save(eua);
        	
        	Country canada = new Country();
        	canada.setName("Canadá");
        	canada.setRegion(america);
        	countryRepository.save(canada);
        	
        	Country mexico = new Country();
        	mexico.setName("México");
        	mexico.setRegion(america);
        	countryRepository.save(mexico);
        	
        	Country alemanha = new Country();
        	alemanha.setName("Alemanha");
        	alemanha.setRegion(europa);
        	countryRepository.save(alemanha);
        	
        	Country inglaterra = new Country();
        	inglaterra.setName("Inglaterra");
        	inglaterra.setRegion(europa);
        	countryRepository.save(inglaterra);
        	
        	Country italia = new Country();
        	italia.setName("Itália");
        	italia.setRegion(europa);
        	countryRepository.save(italia);
        	
        	Country franca = new Country();
        	franca.setName("França");
        	franca.setRegion(europa);
        	countryRepository.save(franca);
        	
        	Country portugal = new Country();
        	portugal.setName("Portugal");
        	portugal.setRegion(europa);
        	countryRepository.save(portugal);
        	
        	Country espanha = new Country();
        	espanha.setName("Espanha");
        	espanha.setRegion(europa);
        	countryRepository.save(espanha);
        	
        	Country irlanda = new Country();
        	irlanda.setName("Irlanda");
        	irlanda.setRegion(europa);
        	countryRepository.save(irlanda);
        	
        	Country gana = new Country();
        	gana.setName("Gana");
        	gana.setRegion(africa);
        	countryRepository.save(gana);
        	
        	Country luanda = new Country();
        	luanda.setName("Luanda");
        	luanda.setRegion(africa);
        	countryRepository.save(luanda);
        	
        	Country africaDoSul = new Country();
        	africaDoSul.setName("África do Sul");
        	africaDoSul.setRegion(africa);
        	countryRepository.save(africaDoSul);
        	
        	Country senegal = new Country();
        	senegal.setName("Senegal");
        	senegal.setRegion(africa);
        	countryRepository.save(senegal);
        	
        	//INSERT LOCATIONS
        	Location locationBrasil = new Location();
        	locationBrasil.setStreetAddress("Endereço no Brasil");
        	locationBrasil.setCountry(brasil);
        	locationBrasil.setCity("Goiânia");
        	locationBrasil.setStateProvince("Goiás");
        	locationBrasil.setPostalCode("74000-000");
        	locationRepository.save(locationBrasil);
        	
        	for(int i=0; i < 55; i++) {
            	Location location = new Location();
            	location.setCountry(brasil);
            	location.setStreetAddress("Endereço "+i);
            	location.setCity("Cidade "+i);
            	location.setStateProvince("Estado "+i);
            	location.setPostalCode("CEP "+i);
            	locationRepository.save(location);
        	}

        	//INSERT EMasks
        	ExampleMask exampleMask = new ExampleMask();
        	exampleMask.setOpcaoDominio(OpcaoDominio.OPCAO_1);
        	exampleMask.setAtivoInativo(AtivoInativoEnum.ATIVO);
        	exampleMask.setCpf("00000000191");
        	exampleMask.setCnpj("00000000000191");
        	exampleMask.setDataHora(DateUtil.generateRandomDate());
        	exampleMask.setDataInicio(DateUtil.generateRandomDate());
        	exampleMask.setDataFim(DateUtil.generateRandomDate());
        	exampleMask.setAnoMesReferencia(201001l);
        	exampleMask.setSalario(100000d);
        	exampleMask.setPorcentagem(100d);
        	emaskRepository.save(exampleMask);
        	
        	ExampleMaskChildren exampleMaskChildren = new ExampleMaskChildren();
        	exampleMaskChildren.setExampleMask(exampleMask);
        	exampleMaskChildren.setDescricao("Descrição Children");
        	exampleMaskChildrenRepository.save(exampleMaskChildren);
        };
    }

}
