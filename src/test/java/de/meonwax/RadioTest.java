package de.meonwax;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class RadioTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RadioTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(RadioTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue(true);
    }
}
