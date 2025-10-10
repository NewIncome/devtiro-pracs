package com.devtiro.database.testutils;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class AssertJListener extends Assertions {

  private static final ThreadLocal<List<String>> passedAssertions = ThreadLocal.withInitial(ArrayList::new);
  private static final ThreadLocal<List<String>> failedAssertions = ThreadLocal.withInitial(ArrayList::new);

  public static <T> AbstractAssert<?, T> assertThat(T actual, String description) {
    try {
      AbstractAssert<?, T> assertion = assertThat(actual);
      passedAssertions.get().add(description);
      return assertion;
    } catch (AssertionError e) {
      failedAssertions.get().add(description);
      throw e;
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