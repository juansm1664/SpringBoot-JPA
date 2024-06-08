package org.juandavid.springboot.jpa.practicespringbootjpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class PracticeSpringBootJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticeSpringBootJpaApplication.class, args);
	}

	@Bean
	public CommandLineRunner validateDSCommand(DataSource ds) {
		return args -> {
			System.out.println("\n probando la conexión y DS ");

			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from characters");
			ResultSet rs = ps.executeQuery();

			while (rs.next()){
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

			 characters.forEach( character -> {
				 String mensaje = character[0] + "-" + character[1];
				 System.out.println(mensaje);
			 });
			 em.getTransaction().commit();
		};
	}
}
