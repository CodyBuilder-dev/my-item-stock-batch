package com.example.myitemstockbatch.quartz;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.quartz.CronExpression.isValidExpression;

public class CronExpressionTest {
    @Test
    public void testFor6DigitCronExp() {
        Assertions.assertTrue(isValidExpression("*/5 * * * * ?"));
    }

    @Test
    public void testFor6DigitCronExpWithSpace() {
        Assertions.assertFalse(isValidExpression("* * * * * * "));
    }

    @Test
    public void testFor6DigitCronExpWithSlash() {
        Assertions.assertFalse(isValidExpression("*/5 * * * * *"));
    }

    @Test
    public void testFor6DigitCronExpWithQuestion() {
        Assertions.assertTrue(isValidExpression("*/5 * * ? * *"));
    }

    @Test
    public void testFor7DigitCronExp() {
        Assertions.assertFalse(isValidExpression("* * * * * * *"));
    }

    @Test
    public void testFor7DigitCronExpWithQuestion() {
        Assertions.assertTrue(isValidExpression("* * * * * ? *"));
    }

    @Test
    public void testFor7DigitCronExpWithQuestion2() {
        Assertions.assertTrue(isValidExpression("* * * ? * * *"));
    }

    @Test
    public void testFor7DigitCronExpWithSlash() {
        Assertions.assertFalse(isValidExpression("*/5 * * * * * *"));
    }

    @Test
    public void testFor7DigitCronExpWithSpace() {
        Assertions.assertFalse(isValidExpression("*/5 * * * * * * "));
    }


//    @Test
//    public void testForEmptyChar() {
//        Assertions.assertThrows(isValidExpression(''));
//        Assertions.assert
//    }


    @Test
    public void testForEmptyString() {
        Assertions.assertFalse(isValidExpression(""));
    }

//    @Test
//    public void testForNull() {
//        Assertions.assertThrows(isValidExpression());
//    }

}
