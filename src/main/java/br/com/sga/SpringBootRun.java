package br.com.sga;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import br.com.sga.core.model.Usuario;
import br.com.sga.core.model.dominio.ZeroUmEnum;
import br.com.sga.core.repository.jpa.UsuarioRepository;


@SpringBootApplication
/*@EnableAutoConfiguration*/
@ComponentScan(basePackages = { "br.com.sga" })
/*@EnableJpaRepositories(basePackages = "br.com.sga.core.repository")*/
/*@EnableTransactionManagement*/
@EntityScan(basePackages = "br.com.sga.core.model")
public class SpringBootRun extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootRun.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootRun.class, args);
	}

    @Bean
    CommandLineRunner initDatabase(
			//PasswordEncoder passwordEncoder,
			UsuarioRepository usuarioRepositorio
		) {
    	
        return args -> {
        	
        	//INSERT USUARIOS
        	Usuario admin = new Usuario();
        	admin.setNome("admin");
        	admin.setSenha("123456"/*passwordEncoder.encode("123456")*/);
        	admin.setAtivo(ZeroUmEnum.UM);
        	usuarioRepositorio.save(admin);
			
        	Usuario user = new Usuario();
        	user.setNome("user");
        	user.setSenha("123456"/*passwordEncoder.encode("123456")*/);
        	user.setAtivo(ZeroUmEnum.UM);
        	usuarioRepositorio.save(user);
        	
        	Usuario user1 = new Usuario();
        	user1.setNome("1");
        	user1.setSenha("1"/*passwordEncoder.encode("1")*/);
        	user1.setAtivo(ZeroUmEnum.UM);
        	usuarioRepositorio.save(user1);
        };
    }

}
