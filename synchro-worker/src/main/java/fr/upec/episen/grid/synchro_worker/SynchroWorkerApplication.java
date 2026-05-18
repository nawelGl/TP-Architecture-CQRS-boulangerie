package fr.upec.episen.grid.synchro_worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication(scanBasePackages = { "fr.upec.episen.grid.synchro_worker" })
@EnableJpaRepositories(basePackages = { "fr.upec.episen.grid.synchro_worker.repository" })
@EnableKafkaStreams
public class SynchroWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SynchroWorkerApplication.class, args);
	}

}