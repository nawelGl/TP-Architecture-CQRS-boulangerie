package fr.upec.episen.grid.synchro_worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "fr.upec.episen.grid.synchro_worker" })
@EnableJpaRepositories(basePackages = { "fr.upec.episen.grid.synchro_worker.repository" })
public class SynchroWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SynchroWorkerApplication.class, args);
	}

}