package com.adamthorpe.javacompiler;

import java.io.File;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

public class App {

  public static void main( String[] args ) {
    try {
      // Parse arguments
      String fileToCompile = parseArguments(args);

      configureJavaParser();
      CompilationUnit cu = StaticJavaParser.parse(new File(fileToCompile));

      ClassFileCreator classFileCreator = new ClassFileCreator(cu);
      classFileCreator.parse();

    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }
  }

  protected static void configureJavaParser() {
    CombinedTypeSolver ts = new CombinedTypeSolver();
    ts.add(new ReflectionTypeSolver());

    JavaSymbolSolver jss = new JavaSymbolSolver(ts);
    StaticJavaParser.getConfiguration().setSymbolResolver(jss);
  }

  /**
   * Parses the input arguments to the executable
   * @param args - List of arguments including flags and a single path to the input file
   * @return - The input file
   * @throws Exception - Input errors
   */
  protected static String parseArguments(String[] args) throws Exception {
    if (args.length == 0) {
      throw new Exception("No input file detected");
    }

    // Currently just returns the first argument
    return args[0];
  }
}