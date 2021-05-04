package com.adamthorpe.javacompiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.adamthorpe.javacompiler.Compiler.CompilerCore;
import com.github.javaparser.ParseProblemException;
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
      if(path.getParent()==null) {
        new CompilerCore(cu, "").parse();
      } else {
        new CompilerCore(cu, path.getParent().toString()).parse();
      }

    } catch (ParseProblemException e) {
      System.out.println("Could not parse file: \"" + args[0] + "\"");
    } catch (FileNotFoundException e) {
      System.out.println("Could not find file: \"" + args[0] + "\"");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * <p>Parses the input arguments of the executable.<p>
   * 
   * @param args        List of arguments including flags and a single path to the input file
   * @return            Path to the input file
   */
  protected static Path parseArguments(String[] args) throws Exception {
    if (args.length == 0) {
      throw new Exception("No input file detected.");
    } else if (args.length > 1) {
      throw new Exception("Too many arguments supplied.");
    }

    // Parse first argument
    Path filePath;
    try {
      filePath = Paths.get(args[0]);
    } catch (InvalidPathException e) {
      throw new Exception("Could not resolve file path: \"" + args[0] + "\"");
    }

    return filePath;
  }
}