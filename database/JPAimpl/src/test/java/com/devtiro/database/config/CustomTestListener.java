package com.devtiro.database.config;

import com.devtiro.database.testutils.AssertJListener;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;

public class CustomTestListener implements TestExecutionListener, TestWatcher, BeforeTestExecutionCallback, AfterTestExecutionCallback {

  private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create("com.devtiro.database");

  @Override
  public void beforeTestClass(TestContext testContext) throws Exception {
    String testMethodClass = testContext.getTestClass().getName();
    String timestamp = LocalDateTime.now().toString();

    String banner = """
            \u001B[31m=============================
            ░█▀▀░█▀█░█▀▄░▀█▀░█▀█░█▀▀░▀█▀░█▀▀░█▀▀░▀█▀
            ░▀▀█░█▀▀░█▀▄░░█░░█░█░█░█░░█░░█▀▀░▀▀█░░█░
            ░▀▀▀░▀░░░▀░▀░▀▀▀░▀░▀░▀▀▀░░▀░░▀▀▀░▀▀▀░░▀░
            ░█▀▀░█░░░█▀█░█▀▀░█▀▀░░░█░░░█▀█░█▀█░█▀▄░█▀▀░█▀▄
            ░█░░░█░░░█▀█░▀▀█░▀▀█░░░█░░░█░█░█▀█░█░█░█▀▀░█░█
            ░▀▀▀░▀▀▀░▀░▀░▀▀▀░▀▀▀░░░▀▀▀░▀▀▀░▀░▀░▀▀░░▀▀▀░▀▀░
            \u001B[0m=============================
            Class: %s
            Executed At: %s
            \u001B[31m=============================\u001B[0m
            """.formatted(testMethodClass, timestamp);

    System.out.println(banner);
  }

  @Override
  public void beforeTestMethod(TestContext testContext) throws Exception {
    String testMethodClass = testContext.getTestClass().getName();
    String testMethodName = testContext.getTestMethod().getName();
    String timestamp = LocalDateTime.now().toString();

    String banner = """
            \u001B[32m=============================
            ▄▄▄▄▄▄▄▄ ..▄▄ · ▄▄▄▄▄    • ▌ ▄ ·. ▄▄▄ .▄▄▄▄▄ ▄ .▄      ·▄▄▄▄      ▄▄▄▄·  ▄▄▄·  ▐ ▄  ▐ ▄ ▄▄▄ .▄▄▄
            •██  ▀▄.▀·▐█ ▀. •██      ·██ ▐███▪▀▄.▀·•██  ██▪▐█▪     ██▪ ██     ▐█ ▀█▪▐█ ▀█ •█▌▐█•█▌▐█▀▄.▀·▀▄ █·
             ▐█.▪▐▀▀▪▄▄▀▀▀█▄ ▐█.▪    ▐█ ▌▐▌▐█·▐▀▀▪▄ ▐█.▪██▀▐█ ▄█▀▄ ▐█· ▐█▌    ▐█▀▀█▄▄█▀▀█ ▐█▐▐▌▐█▐▐▌▐▀▀▪▄▐▀▀▄
             ▐█▌·▐█▄▄▌▐█▄▪▐█ ▐█▌·    ██ ██▌▐█▌▐█▄▄▌ ▐█▌·██▌▐▀▐█▌.▐▌██. ██     ██▄▪▐█▐█ ▪▐▌██▐█▌██▐█▌▐█▄▄▌▐█•█▌
             ▀▀▀  ▀▀▀  ▀▀▀▀  ▀▀▀     ▀▀  █▪▀▀▀ ▀▀▀  ▀▀▀ ▀▀▀ · ▀█▄▀▪▀▀▀▀▀•     ·▀▀▀▀  ▀  ▀ ▀▀ █▪▀▀ █▪ ▀▀▀ .▀  ▀
            =============================\u001B[0m
            Class: %s
            \u001B[32mMethod: %s
            \u001B[0mExecuted At: %s
            =============================
            """.formatted(testMethodClass, testMethodName, timestamp);

    System.out.println(banner);

    // Clear assertions for JUnit extension compatibility
    AssertJListener.clear();
  }

  @Override
  public void beforeTestExecution(ExtensionContext context) {
    // Only clear assertions if not already done by beforeTestMethod to avoid duplicate clearing
    if (context.getStore(NAMESPACE).get("bannerPrinted") == null) {
      AssertJListener.clear();
      context.getStore(NAMESPACE).put("bannerPrinted", true);
    }
  }

  @Override
  public void afterTestExecution(ExtensionContext context) {
    boolean passed = !context.getExecutionException().isPresent();
    printAssertionBanner(context, passed);
    context.getStore(NAMESPACE).remove("bannerPrinted"); // Clean up
  }

  @Override
  public void testSuccessful(ExtensionContext context) {
    // Handled by afterTestExecution
  }

  @Override
  public void testFailed(ExtensionContext context, Throwable cause) {
    // Handled by afterTestExecution
  }

  private void printAssertionBanner(ExtensionContext context, boolean passed) {
    String testMethodName = context.getTestMethod().map(Method::getName).orElse("Unknown");
    String status = passed ? "PASSED" : "FAILED";
    String color = passed ? "\u001B[32m" : "\u001B[31m"; // Green for passed, red for failed
    String timestamp = LocalDateTime.now().toString();
    List<String> passedAssertions = AssertJListener.getPassedAssertions();
    List<String> failedAssertions = AssertJListener.getFailedAssertions();

    StringBuilder banner = new StringBuilder();
    banner.append(color)
        .append("=============================\n")
        .append("Assertion Results Banner\n")
        .append("=============================\n")
        .append("Method: ").append(testMethodName).append("\n")
        .append("Status: ").append(status).append("\n")
        .append("Completed At: ").append(timestamp).append("\n");

    if (!passedAssertions.isEmpty()) {
      banner.append("Passed Assertions:\n");
      for (String assertion : passedAssertions) {
        banner.append("  - ").append(assertion).append("\n");
      }
    } else {
      banner.append("Passed Assertions: None\n");
    }

    if (!failedAssertions.isEmpty()) {
      banner.append("Failed Assertions:\n");
      for (String assertion : failedAssertions) {
        banner.append("  - ").append(assertion).append("\n");
      }
    } else {
      banner.append("Failed Assertions: None\n");
    }

    banner.append("=============================\n")
        .append("\u001B[0m");

    System.out.println(banner.toString());
  }
}
