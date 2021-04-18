package com.sec.conf;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppConfigurationTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppConfigurationTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppConfigurationTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() throws Exception{
        AppConfiguration config = new AppConfiguration().create();
        String authorName = config.getString(AppConfiguration.ConfVars.AUTHOR_NAME);
        System.out.println(authorName);
    }
}
