package com.devtiro.database.testutils;

import java.util.ArrayList;
import java.util.List;

public class AssertionTracker {
  private static final ThreadLocal<List<String>> passedAssertions = ThreadLocal.withInitial(ArrayList::new);
  private static final ThreadLocal<List<String>> failedAssertions = ThreadLocal.withInitial(ArrayList::new);

  public static void recordAssertion(boolean condition, String description) {
    if (condition) {
      passedAssertions.get().add(description);
    } else {
      failedAssertions.get().add(description);
    }
  }

  public static List<String> getPassedAssertions() {
    return new ArrayList<>(passedAssertions.get());
  }

  public static List<String> getFailedAssertions() {
    return new ArrayList<>(failedAssertions.get());
  }

  public static void clear() {
    passedAssertions.get().clear();
    failedAssertions.get().clear();
  }
}