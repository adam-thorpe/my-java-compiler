package com.adamthorpe.javacompiler;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.Log;
import com.github.javaparser.utils.SourceRoot;

import java.nio.file.Paths;
import java.util.Optional;

public class App {

  public static void main( String[] args ) {
    try {
      String fileToCompile = parseArguments(args);
      SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(App.class));

      CompilationUnit cu = sourceRoot.parse("", fileToCompile);
      Optional<ClassOrInterfaceDeclaration> helloWorldClass = cu.getClassByName("HelloWorld");

      System.out.println(helloWorldClass);

    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }
  }

  protected static String parseArguments(String[] args) throws Exception {
    if (args.length == 0) {
      throw new Exception("No input file detected");
    }

    // Currently just returns the first argument
    return args[0];
  }
}