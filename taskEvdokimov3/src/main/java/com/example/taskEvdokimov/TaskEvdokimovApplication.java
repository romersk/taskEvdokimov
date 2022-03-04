package com.example.taskEvdokimov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

@SpringBootApplication
public class TaskEvdokimovApplication {

	public static void main(String[] args) throws URISyntaxException {
		CSVReader csvReader = new CSVReader();
		csvReader.parseFromFileToDB();
		SpringApplication.run(TaskEvdokimovApplication.class, args);
	}

}
