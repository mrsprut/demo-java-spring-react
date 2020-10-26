package org.tyaa.demo.java.spring.springbootreactjs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.tyaa.demo.java.spring.springbootreactjs.entities.Role;
import org.tyaa.demo.java.spring.springbootreactjs.entities.User;
import org.tyaa.demo.java.spring.springbootreactjs.repositories.RoleHibernateDAO;
import org.tyaa.demo.java.spring.springbootreactjs.repositories.UserHibernateDAO;

import java.math.BigDecimal;

@SpringBootApplication
public class SpringbootReactjsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootReactjsApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(
			RoleHibernateDAO roleDAO,
			UserHibernateDAO userDAO,
			PasswordEncoder passwordEncoder
	) {
		return args -> {
			roleDAO.save(Role.builder().name("ROLE_ADMIN").build());
			roleDAO.save(Role.builder().name("ROLE_USER").build());
			Role adminRole = roleDAO.findRoleByName("ROLE_ADMIN");
			Role userRole = roleDAO.findRoleByName("ROLE_USER");
			userDAO.save(
					User.builder()
							.name("admin")
							.password(passwordEncoder.encode("AdminPassword1"))
							.role(adminRole)
							.build()
			);
			userDAO.save(
					User.builder()
							.name("one")
							.password(passwordEncoder.encode("UserPassword1"))
							.role(userRole)
							.build()
			);
			userDAO.save(
					User.builder()
							.name("two")
							.password(passwordEncoder.encode("UserPassword2"))
							.role(userRole)
							.build()
			);
			userDAO.save(
					User.builder()
							.name("three")
							.password(passwordEncoder.encode("UserPassword3"))
							.role(userRole)
							.build()
			);
		};
	}
}
