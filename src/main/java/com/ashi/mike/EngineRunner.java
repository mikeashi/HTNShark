package com.ashi.mike;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.LinkedList;
import JSHOP2.InternalDomain;
import JSHOP2.Plan;


public class EngineRunner {
    
    /**
     * Generate Domain class from domain file.
     * note that the generated class will not be compiled
     * so a recompile is required.
     * 
     * @param domainFileName domain file name 
     * 
     * @throws Exception
     */
    public static void generateDomain(String domainFileName) throws Exception {
        String domainFilePath = "htn/"+domainFileName;
        File domainFile = new File(domainFilePath);
        FileInputStream domainStream = new FileInputStream(domainFile);
        InternalDomain internalDomain = new InternalDomain(domainStream,-1);
        internalDomain.getParser().domain();

        File domainJavaFile = new File(internalDomain.getName()+".java");
        domainJavaFile.renameTo(new File("src/main/java/com/ashi/mike/domain/"+ internalDomain.getName()+".java"));
        
        File domainTextFile = new File(internalDomain.getName()+".txt");
        domainTextFile.renameTo(new File("htn/"+ internalDomain.getName()+".txt"));

    }

    /**
     * Generate Problem class from problem string.
     * 
     * @param problem problem string in jshop2 format
     * 
     * @throws Exception
     */
    private static void generateProblem(String problem) throws Exception {
        String problemFilePath = "htn/problem";
        BufferedWriter dest = new BufferedWriter(new FileWriter(problemFilePath));
        dest.write(problem);
        dest.close();
        File problemFile = new File(problemFilePath);
        FileInputStream problemStream = new FileInputStream(problemFile);
        InternalDomain domain = new InternalDomain(problemStream,1);
        domain.getParser().command();
        File problemJavaFile = new File("problem.java");
        problemJavaFile.renameTo(new File("htn/problem.java"));
    }

    /**
     * Generate Problem class from problem string and 
     * load the class dynamically at runtime to get the plans.
     * 
     * @param problem
     * @throws Exception
     * @return LinkedList<Plan> plan
     */
    public static LinkedList<Plan> solve(String problem) throws Exception{
        generateProblem(problem);
        File parent = new File(System.getProperty("user.dir")+"/htn");
        File sourceFile = new File(parent, "problem.java");
        File classFile = new File(parent, "problem.class");
        sourceFile.deleteOnExit();
        classFile.deleteOnExit();

        // dynamically compile the problem.java file https://stackoverflow.com/a/60665101
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(null, null, null);
        File parentDir = sourceFile.getParentFile();
        standardJavaFileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(parentDir));
        Iterable<? extends JavaFileObject> compilationUnits = standardJavaFileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile));
        javaCompiler.getTask(null, standardJavaFileManager, null, null, null, compilationUnits).call();
        standardJavaFileManager.close();

        // load class
        URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[] {parentDir.toURI().toURL()});
        Class<?> dynamicClass = urlClassLoader.loadClass("problem");

        // call method and return plans
        Method getPlans = dynamicClass.getDeclaredMethod("getPlans");
        return (LinkedList<Plan>) getPlans.invoke(null);
    } 
}
