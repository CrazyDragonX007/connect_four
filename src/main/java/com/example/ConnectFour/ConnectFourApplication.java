package com.example.ConnectFour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages={"com.example.security","com.example.rest","com.example.ConnectFour"})
@EntityScan(basePackages = "com.example.entity" )
@EnableJpaRepositories(basePackages = {"com.example.rest.repo"})
public class ConnectFourApplication {
	
//	@Bean
//	public HibernateJpaSessionFactoryBean sessionFactory() {
//	    return new HibernateJpaSessionFactoryBean();
//	}

	public static void main(String[] args) {

		SpringApplication.run(ConnectFourApplication.class, args);
	}

}
