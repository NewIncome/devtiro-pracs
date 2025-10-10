package com.devtiro.database.config;

import org.springframework.boot.Banner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.io.PrintStream;
import java.time.LocalDateTime;

public class CustomContextBanner implements Banner {

  @Override
  public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
    String appName = environment.getProperty("spring.application.name", "Database with JPA App");
    String bootVersion = org.springframework.boot.SpringBootVersion.getVersion();
    String timestamp = LocalDateTime.now().toString();

    String banner = """
        =============================
        \u001B[34m __                     ___                                           _               \s
        /   _ __ _|_ _    _|_    | __  o _|_ o  _  |  o  _  _ _|_ o  _ __    |_) _ __ __  _  __
        \\__(_)| | |_(/_><  |_   _|_| | |  |_ | (_| |  |  /_(_| |_ | (_)| |   |_)(_|| || |(/_ |\s\u001B[0m
        =============================
        App Name: %s
        Spring Boot Version: %s
        Initialized At: %s
        =============================
        \u001B[34m
        ███████╗██████╗ ██████╗ ██╗███╗   ██╗ ██████╗                                    \s
        ██╔════╝██╔══██╗██╔══██╗██║████╗  ██║██╔════╝                                    \s
        ███████╗██████╔╝██████╔╝██║██╔██╗ ██║██║  ███╗                                   \s
        ╚════██║██╔═══╝ ██╔══██╗██║██║╚██╗██║██║   ██║                                   \s
        ███████║██║     ██║  ██║██║██║ ╚████║╚██████╔╝                                   \s
        ╚══════╝╚═╝     ╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝                                    \s
        
         █████╗ ██████╗ ██████╗ ██╗     ██╗ ██████╗ █████╗ ████████╗██╗ ██████╗ ███╗   ██╗
        ██╔══██╗██╔══██╗██╔══██╗██║     ██║██╔════╝██╔══██╗╚══██╔══╝██║██╔═══██╗████╗  ██║
        ███████║██████╔╝██████╔╝██║     ██║██║     ███████║   ██║   ██║██║   ██║██╔██╗ ██║
        ██╔══██║██╔═══╝ ██╔═══╝ ██║     ██║██║     ██╔══██║   ██║   ██║██║   ██║██║╚██╗██║
        ██║  ██║██║     ██║     ███████╗██║╚██████╗██║  ██║   ██║   ██║╚██████╔╝██║ ╚████║
        ╚═╝  ╚═╝╚═╝     ╚═╝     ╚══════╝╚═╝ ╚═════╝╚═╝  ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
        
         ██████╗ ██████╗ ███╗   ██╗████████╗███████╗██╗  ██╗████████╗                    \s
        ██╔════╝██╔═══██╗████╗  ██║╚══██╔══╝██╔════╝╚██╗██╔╝╚══██╔══╝                    \s
        ██║     ██║   ██║██╔██╗ ██║   ██║   █████╗   ╚███╔╝    ██║                       \s
        ██║     ██║   ██║██║╚██╗██║   ██║   ██╔══╝   ██╔██╗    ██║                       \s
        ╚██████╗╚██████╔╝██║ ╚████║   ██║   ███████╗██╔╝ ██╗   ██║                       \s
         ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   ╚══════╝╚═╝  ╚═╝   ╚═╝                       \s
        \u001B[0m
        """.formatted(appName, bootVersion, timestamp);

    out.println(banner);
  }
}
