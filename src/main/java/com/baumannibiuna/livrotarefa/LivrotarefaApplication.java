package com.baumannibiuna.livrotarefa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories(repositoryFactoryBeanClass = QuerydslJpaRepositoryFactoryBean.class)
public class LivrotarefaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LivrotarefaApplication.class, args);
	}

}
