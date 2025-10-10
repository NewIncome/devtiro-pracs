package com.devtiro.database.config;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

import java.time.LocalDateTime;

public class CustomTestListener implements TestExecutionListener {

  @Override
  public void beforeTestClass(TestContext testContext) throws Exception {
    String testMethodClass = testContext.getTestClass().getName();
    String timestamp = LocalDateTime.now().toString();

    String banner = """
        \u001B[31m=============================
        ░█▀▀░█▀█░█▀▄░▀█▀░█▀█░█▀▀░▀█▀░█▀▀░█▀▀░▀█▀     \s
        ░▀▀█░█▀▀░█▀▄░░█░░█░█░█░█░░█░░█▀▀░▀▀█░░█░     \s
        ░▀▀▀░▀░░░▀░▀░▀▀▀░▀░▀░▀▀▀░░▀░░▀▀▀░▀▀▀░░▀░     \s
        ░█▀▀░█░░░█▀█░█▀▀░█▀▀░░░█░░░█▀█░█▀█░█▀▄░█▀▀░█▀▄
        ░█░░░█░░░█▀█░▀▀█░▀▀█░░░█░░░█░█░█▀█░█░█░█▀▀░█░█
        ░▀▀▀░▀▀▀░▀░▀░▀▀▀░▀▀▀░░░▀▀▀░▀▀▀░▀░▀░▀▀░░▀▀▀░▀▀░
        \u001B[0m=============================
        Class: %s
        Executed At: %s
        \u001B[31m=============================\u001B[0m
        """.formatted(testMethodClass, timestamp);
    //reset code (\u001B[0m) ensures subsequent output reverts to the default color

    System.out.println(banner);
  }

  @Override
  public void beforeTestMethod(TestContext testContext) throws Exception {
    String testMethodClass = testContext.getTestClass().getName();
    String testMethodName = testContext.getTestMethod().getName();
    String timestamp = LocalDateTime.now().toString();

    String banner = """
        \u001B[32m=============================
        ▄▄▄▄▄▄▄▄ ..▄▄ · ▄▄▄▄▄    • ▌ ▄ ·. ▄▄▄ .▄▄▄▄▄ ▄ .▄      ·▄▄▄▄      ▄▄▄▄·  ▄▄▄·  ▐ ▄  ▐ ▄ ▄▄▄ .▄▄▄ \s
        •██  ▀▄.▀·▐█ ▀. •██      ·██ ▐███▪▀▄.▀·•██  ██▪▐█▪     ██▪ ██     ▐█ ▀█▪▐█ ▀█ •█▌▐█•█▌▐█▀▄.▀·▀▄ █·
         ▐█.▪▐▀▀▪▄▄▀▀▀█▄ ▐█.▪    ▐█ ▌▐▌▐█·▐▀▀▪▄ ▐█.▪██▀▐█ ▄█▀▄ ▐█· ▐█▌    ▐█▀▀█▄▄█▀▀█ ▐█▐▐▌▐█▐▐▌▐▀▀▪▄▐▀▀▄\s
         ▐█▌·▐█▄▄▌▐█▄▪▐█ ▐█▌·    ██ ██▌▐█▌▐█▄▄▌ ▐█▌·██▌▐▀▐█▌.▐▌██. ██     ██▄▪▐█▐█ ▪▐▌██▐█▌██▐█▌▐█▄▄▌▐█•█▌
         ▀▀▀  ▀▀▀  ▀▀▀▀  ▀▀▀     ▀▀  █▪▀▀▀ ▀▀▀  ▀▀▀ ▀▀▀ · ▀█▄▀▪▀▀▀▀▀•     ·▀▀▀▀  ▀  ▀ ▀▀ █▪▀▀ █▪ ▀▀▀ .▀  ▀
        =============================\u001B[0m
        Class: %s
        \u001B[32mMethod: %s
        \u001B[0mExecuted At: %s
        =============================
        """.formatted(testMethodClass, testMethodName, timestamp);
        //reset code (\u001B[0m) ensures subsequent output reverts to the default color

    System.out.println(banner);
  }

}
