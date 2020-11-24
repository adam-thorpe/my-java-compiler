package com.adamthorpe.javacompiler;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.File;

public class App {

  public static void main( String[] args ) {
    try {
      // Parse arguments
      String fileToCompile = parseArguments(args);
      // configureJavaParser();

      //test
      CombinedTypeSolver ts = new CombinedTypeSolver();
      ts.add(new ReflectionTypeSolver());
      JavaSymbolSolver jss = new JavaSymbolSolver(ts);
      StaticJavaParser.getConfiguration().setSymbolResolver(jss);
      //

      CompilationUnit cu = StaticJavaParser.parse(new File(fileToCompile));

      // cu.findAll(AssignExpr.class).forEach(ae -> {
      //   ResolvedType resolvedType = ae.calculateResolvedType();
      //   System.out.println(ae.toString() + " is a: " + resolvedType.describe());
      // });

      // for (TypeDeclaration typeDeclaration : cu.getTypes()) {
      //   JavaParserFacade.get(ts).
			// }

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