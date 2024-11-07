package tech.maou.patitasalrescate;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableJpaAuditing
@	EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class PatitasAlRescateApplication {
	@Value("${spring.cloud.azure.storage.blob.connection-string}")
	private String connectionString;

	public static void main(String[] args) {
		SpringApplication.run(PatitasAlRescateApplication.class, args);
	}

	@Bean
	public BlobServiceClient blobServiceClient() {
		return new BlobServiceClientBuilder()
				.connectionString(connectionString)
				.buildClient();
	}

}
