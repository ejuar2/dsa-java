package edu.emory.cs.algebraic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongIntegerQuizTest {
    @Test
    public void test0() {
        LongInteger a = new LongIntegerQuiz(new LongInteger());
        a.add(new LongIntegerQuiz("-123"));
        assertEquals("-123", a.toString());
    }
    @Test
    public void test1() {
        LongInteger a = new LongIntegerQuiz("123");
        a.add(new LongIntegerQuiz("0"));
        assertEquals("123", a.toString());
    }

    @Test
    public void test2() {
        LongInteger a = new LongIntegerQuiz("123");
        a.add(new LongIntegerQuiz("-0"));
        assertEquals("123", a.toString());
    }

    @Test
    public void test3() {
        LongInteger a = new LongIntegerQuiz("123");
        a.add(new LongIntegerQuiz("-123"));
        assertEquals("0", a.toString());
    }

    @Test
    public void test4() {
        LongInteger a = new LongIntegerQuiz("-123");
        a.add(new LongIntegerQuiz("123"));
        assertEquals("-0", a.toString());
    }

    @Test
    public void test5() {
        LongInteger a = new LongIntegerQuiz("123");
        a.add(new LongIntegerQuiz("-122"));
        assertEquals("1", a.toString());
    }

    @Test
    public void test6() {
        LongInteger a = new LongIntegerQuiz("-123");
        a.add(new LongIntegerQuiz("122"));
        assertEquals("-1", a.toString());
    }

    @Test
    public void test7() {
        LongInteger a = new LongIntegerQuiz("123");
        a.add(new LongIntegerQuiz("-124"));
        assertEquals("-1", a.toString());
    }

    @Test
    public void test8() {
        LongInteger a = new LongIntegerQuiz("-123");
        a.add(new LongIntegerQuiz("124"));
        assertEquals("1", a.toString());
    }

    @Test
    public void test9() {
        LongInteger a = new LongIntegerQuiz("123");
        a.add(new LongIntegerQuiz("-45678"));
        assertEquals("-45555", a.toString());
    }

    @Test
    public void test10() {
        LongInteger a = new LongIntegerQuiz("-12345");
        a.add(new LongIntegerQuiz("678"));
        assertEquals("-11667", a.toString());
    }

    @Test
    public void test11() {
        LongInteger li = new LongInteger("123");
        LongInteger a = new LongIntegerQuiz(li);
        a.add(new LongIntegerQuiz("0"));
        assertEquals("123", a.toString());
    }

    @Test
    public void test12() {
        LongInteger li = new LongInteger("123");
        LongInteger a = new LongIntegerQuiz(li);
        a.add(new LongIntegerQuiz("-0"));
        assertEquals("123", a.toString());
    }

    @Test
    public void test13() {
        LongInteger li = new LongInteger("123");
        LongInteger a = new LongIntegerQuiz(li);
        a.add(new LongIntegerQuiz("-123"));
        assertEquals("0", a.toString());
    }

    @Test
    public void test14() {
        LongInteger li = new LongInteger("-123");
        LongInteger a = new LongIntegerQuiz(li);
        a.add(new LongIntegerQuiz("123"));
        assertEquals("-0", a.toString());
    }

    @Test
    public void test15() {
        LongInteger li = new LongInteger("123");
        LongInteger a = new LongIntegerQuiz(li);
        a.add(new LongIntegerQuiz("-122"));
        assertEquals("1", a.toString());
    }

    @Test
    public void test16() {
        LongInteger li = new LongInteger("-123");
        LongInteger a = new LongIntegerQuiz(li);
        a.add(new LongIntegerQuiz("122"));
        assertEquals("-1", a.toString());
    }

    @Test
    public void test17() {
        LongInteger li = new LongInteger("123");
        LongInteger a = new LongIntegerQuiz(li);
        a.add(new LongIntegerQuiz("-124"));
        assertEquals("-1", a.toString());
    }

    @Test
    public void test18() {
        LongInteger li = new LongInteger("-123");
        LongInteger a = new LongIntegerQuiz(li);
        a.add(new LongIntegerQuiz("124"));
        assertEquals("1", a.toString());
    }

    @Test
    public void test19() {
        LongInteger li = new LongInteger("123");
        LongInteger a = new LongIntegerQuiz(li);
        a.add(new LongIntegerQuiz("-45678"));
        assertEquals("-45555", a.toString());
    }

    @Test
    public void test20() {
        LongInteger li = new LongInteger("-12345");
        LongInteger a = new LongIntegerQuiz(li);
        a.add(new LongIntegerQuiz("678"));
        assertEquals("-11667", a.toString());
    }
    @Test
    public void test21() {
        LongInteger a = new LongIntegerQuiz("132");
        a.add(new LongIntegerQuiz("-122"));
        assertEquals("10", a.toString());
    }

    @Test
    public void test22() {
        LongInteger a = new LongIntegerQuiz("-132");
        a.add(new LongIntegerQuiz("122"));
        assertEquals("-10", a.toString());
    }

    @Test
    public void test23() {
        LongInteger a = new LongIntegerQuiz("114");
        a.add(new LongIntegerQuiz("-124"));
        assertEquals("-10", a.toString());
    }

    @Test
    public void test24() {
        LongInteger a = new LongIntegerQuiz("-114");
        a.add(new LongIntegerQuiz("124"));
        assertEquals("10", a.toString());
    }
    @Test
    public void test25() {
        LongInteger a = new LongIntegerQuiz("1");
        a.add(new LongIntegerQuiz("-1"));
        assertEquals("0", a.toString());
    }

    @Test
    public void test26() {
        LongInteger a = new LongIntegerQuiz("-1");
        a.add(new LongIntegerQuiz("1"));
        assertEquals("-0", a.toString());
    }
    @Test
    public void test27() {
        LongInteger a = new LongIntegerQuiz("0");
        a.add(new LongIntegerQuiz("-0"));
        assertEquals("0", a.toString());
    }

    @Test
    public void test28() {
        LongInteger a = new LongIntegerQuiz("-0");
        a.add(new LongIntegerQuiz("0"));
        assertEquals("-0", a.toString());
    }
    @Test
    public void test29() {
        LongInteger a = new LongIntegerQuiz("0");
        a.add(new LongIntegerQuiz("0"));
        assertEquals("0", a.toString());
    }

    @Test
    public void test30() {
        LongInteger a = new LongIntegerQuiz("9");
        a.add(new LongIntegerQuiz("-100"));
        assertEquals("-91", a.toString());
    }

    @Test
    public void test31() {
        LongInteger a = new LongIntegerQuiz("-100");
        a.add(new LongIntegerQuiz("9"));
        assertEquals("-91", a.toString());
    }
    @Test
    public void test32() {
        LongInteger a = new LongIntegerQuiz("-1000");
        a.add(new LongIntegerQuiz("9"));
        assertEquals("-991", a.toString());
    }
    @Test
    public void test33() {
        LongInteger a = new LongIntegerQuiz("-10000");
        a.add(new LongIntegerQuiz("9"));
        assertEquals("-9991", a.toString());
    }

    @Test
    public void test34() {
        LongInteger a = new LongIntegerQuiz("-11000");
        a.add(new LongIntegerQuiz("9"));
        assertEquals("-10991", a.toString());
    }

}