package com.example.ConnectFour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.example")
public class ConnectFourApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectFourApplication.class, args);
	}

}
