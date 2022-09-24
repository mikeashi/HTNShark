package com.ashi.mike;


public class App 
{
    public static void main( String[] args ) throws Exception
    {
       //EngineRunner.generateDomain("basic");
       //System.exit(0);

        String problem = "(defproblem problem basic ((have kiwi)) ((swap banjo kiwi)))";

        
        System.out.println(
            EngineRunner.solve(problem)
        );
        
    }
}
