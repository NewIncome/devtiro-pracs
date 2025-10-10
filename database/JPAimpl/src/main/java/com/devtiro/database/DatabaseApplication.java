package com.devtiro.database;

import com.devtiro.database.config.CustomContextBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.java.Log;

@SpringBootApplication
@Log
public class DatabaseApplication {

  //Main method
	public static void main(String[] args) {
    SpringApplication app = new SpringApplication(DatabaseApplication.class);
    app.setBanner(new CustomContextBanner());
    app.run(args);
  }

}
