package ma.rest.spring;

import ma.rest.spring.entities.Client;
import ma.rest.spring.entities.Compte;
import ma.rest.spring.entities.TypeCompte;
import ma.rest.spring.repositories.ClientRepository;
import ma.rest.spring.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.Date;

@SpringBootApplication
public class MsBanqueApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsBanqueApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(CompteRepository compteRepository,
                               ClientRepository clientRepository,
                               RepositoryRestConfiguration restConfiguration) {
        return args -> {
            // show IDs in Spring Data REST responses
            restConfiguration.exposeIdsFor(Compte.class, Client.class);

            // create clients using setters (works without Lombok)
            Client c1 = new Client();
            c1.setNom("Amal");
            c1.setEmail("amal@test.com");
            c1 = clientRepository.save(c1);

            Client c2 = new Client();
            c2.setNom("Ali");
            c2.setEmail("ali@test.com");
            c2 = clientRepository.save(c2);

            // seed comptes
            compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, c1));
            compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.COURANT, c1));
            compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, c2));

            // verify
            compteRepository.findAll().forEach(System.out::println);
        };
    }
}