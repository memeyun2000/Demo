package com.sec.joinery;

import joinery.DataFrame;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        DataFrame<Object> df = DataFrame.readCsv("F:\\demo\\Demo\\asset\\user-data.csv");
        System.out.println(df.head());
    }
}
