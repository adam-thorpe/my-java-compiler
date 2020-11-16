package com.adamthorpe.javacompiler;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.printer.XmlPrinter;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.Log;
import com.github.javaparser.utils.SourceRoot;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

public class App {

  public static void main( String[] args ) {
    try {
      // Parse arguments
      String fileToCompile = parseArguments(args);

      CompilationUnit cu = StaticJavaParser.parse(new File(fileToCompile));
      ClassFileCreator classFileCreator = new ClassFileCreator(cu);
      classFileCreator.parse();

    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }
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