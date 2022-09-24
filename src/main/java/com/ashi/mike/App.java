package com.ashi.mike;


public class App 
{
    public static void main( String[] args ) throws Exception
    {
        //EngineRunner.generateDomain("basic");
        System.out.println(
            EngineRunner.solve("(defproblem problem basic ((have kiwi)) ((swap banjo kiwi)))")
        );
    }
}
