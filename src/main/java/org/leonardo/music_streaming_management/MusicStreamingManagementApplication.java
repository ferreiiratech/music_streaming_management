package org.leonardo.music_streaming_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MusicStreamingManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicStreamingManagementApplication.class, args);
    }

}
