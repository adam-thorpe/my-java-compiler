package com.adamthorpe.javacompiler;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

public class App {

  public static void main( String[] args ) {
    try {
      // Parse arguments
      Path path = parseArguments(args);

      // Set up static parser to recognise symbols
      CombinedTypeSolver ts = new CombinedTypeSolver();
      ts.add(new ReflectionTypeSolver());
      JavaSymbolSolver jss = new JavaSymbolSolver(ts);
      StaticJavaParser.getConfiguration().setSymbolResolver(jss);

      // Create the AST
      CompilationUnit cu = StaticJavaParser.parse(new File(path.toString()));
      
      // Compile
      new CompilerCore(cu, path.getParent().toString()).parse();

    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }
  }

  /**
   * Parses the input arguments of the executable
   * 
   * @param args - List of arguments including flags and a single path to the input file
   * @return - The input file
   * @throws Exception - Input errors
   */
  protected static Path parseArguments(String[] args) throws Exception {
    if (args.length == 0) {
      throw new Exception("No input file detected");
    }

    // Currently just returns the first argument
    return Paths.get(args[0]);
  }
}