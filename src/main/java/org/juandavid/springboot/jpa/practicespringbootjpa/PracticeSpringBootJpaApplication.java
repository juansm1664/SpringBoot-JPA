package org.juandavid.springboot.jpa.practicespringbootjpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.juandavid.springboot.jpa.practicespringbootjpa.entity.Customer;
import org.juandavid.springboot.jpa.practicespringbootjpa.repository.CustomerCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@SpringBootApplication
public class PracticeSpringBootJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticeSpringBootJpaApplication.class, args);
    }

    @Autowired
    private CustomerCrudRepository customerCrudRepository;

    @Bean
    public CommandLineRunner validateDSCommand(DataSource ds) {
        return args -> {
            System.out.println("\n probando la conexión y DS ");

            Connection conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from characters");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String mensaje = rs.getInt("id") + '-' + rs.getString("name");
                System.out.println(mensaje);
            }
        };
    }

    @Bean
    public CommandLineRunner validateEntityManagerFact(EntityManagerFactory emf) {
        return args -> {

            System.out.println("\n probando entityManager fact");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            List<Object[]> characters = em.createNativeQuery("select * from characters").getResultList();

            characters.forEach(character -> {
                String mensaje = character[0] + "-" + character[1];
                System.out.println(mensaje);
            });
            em.getTransaction().commit();
        };

    }

    @Bean
    public CommandLineRunner testCustomerRepository(CustomerCrudRepository customerCrudRepository) {
        return args -> {
            Customer juan = new Customer();
            juan.setName("Juan");
            juan.setPassword("12345");

            customerCrudRepository.save(juan);
            System.out.println("Se guardo la entidad ");

            System.out.println("\n imprimiendo los clientes ");
            customerCrudRepository.findAll()
                    .forEach(System.out::println);


        };
    };
};

