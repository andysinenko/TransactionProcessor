package com.ukrsib.processor;

import com.ukrsib.processor.config.AccountsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@SpringBootApplication
@EnableScheduling
public class TransactionsProcessorApplication {
	@Autowired
	private AccountsProperties accountsProperties;
	public static void main(String[] args) {
		SpringApplication.run(TransactionsProcessorApplication.class, args);
	}

	@PostConstruct
	private void init() {
		System.out.println("*********************AppInitializator initialization logic************************");
		System.out.println(accountsProperties.getFolder());
		clearDirectory(accountsProperties.getFolder());
	}

	private void clearDirectory(String path) {
		File dir = new File(path);
		File[] files = dir.listFiles();
		if (files.length != 0) {
			Stream.of(files).forEach(e -> {
				try {
					Files.deleteIfExists(Paths.get(e.getPath()));
				} catch (IOException ex) {}
			});

		}
	}

}
