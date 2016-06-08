package com.ote.test;

import lombok.NonNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.model.Statement;

@RunWith(JUnit4.class)
public class TestJUnitRule {

    @Rule
    public TestRule testRule = RuleChain.outerRule(new LoggingRule("Message")).around(new MyRule());

    @Rule
    public TestName testNameRule = new TestName();

    private void testParameter(@NonNull String test){
    //    System.out.println(test);
    }

    @Test
    public void test() {
        System.out.println("INNER TEST : " + testNameRule.getMethodName());

        //testParameter(null);
    }

    public class LoggingRule implements TestRule {

        private String message;

        public LoggingRule(String message) {
            this.message = message;
        }

        @Override
        public Statement apply(Statement statement, Description description) {

            return new Statement() {

                @Override
                public void evaluate() throws Throwable {
                    System.out.println("LoggingRule : " + message);
                    statement.evaluate();
                }
            };
        }
    }

    public static class MyRule implements TestRule {

        @Override
        public Statement apply(Statement statement, Description description) {

            return new Statement() {

                @Override
                public void evaluate() throws Throwable {
                    System.out.println("BEFORE : " + description);
                    statement.evaluate();
                    System.out.println("AFTER : " + description);
                }
            };
        }
    }
}
