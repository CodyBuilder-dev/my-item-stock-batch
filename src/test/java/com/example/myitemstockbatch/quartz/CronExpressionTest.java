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
        Assertions.assertTrue(isValidExpression("* * * * * * "));
    }

    @Test
    public void testFor6DigitCronExpWithSlash() {
        Assertions.assertTrue(isValidExpression("*/5 * * * * *"));
    }

    @Test
    public void testFor6DigitCronExpWithQuestion() {
        Assertions.assertTrue(isValidExpression("*/5 * * ? * *"));
    }

    @Test
    public void testFor7DigitCronExp() {
        Assertions.assertTrue(isValidExpression("* * * * * * *"));
    }

    @Test
    public void testFor7DigitCronExpWithSlash() {
        Assertions.assertTrue(isValidExpression("*/5 * * * * * *"));
    }

    @Test
    public void testFor7DigitCronExpWithSpace() {
        Assertions.assertTrue(isValidExpression("*/5 * * * * * * "));
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
