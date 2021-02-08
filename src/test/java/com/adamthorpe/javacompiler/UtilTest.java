package com.adamthorpe.javacompiler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.type.PrimitiveType.Primitive;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import org.junit.BeforeClass;
import org.junit.Test;

public class UtilTest {

  static Type stringReferenceType;
  static Type objectArrReferenceType;
  static Type intType;
  static Type intArrType;
  static Type voidType;

  @BeforeClass
  public static void loadTypes() {
    String code = "public class ClassA {" + 
      "public String A; " +
      "public Object[] B; " +
      "public int[] C; " +
      "public void D() {} " +
    "}";

    // Set up static parser to recognise symbols
    CombinedTypeSolver ts = new CombinedTypeSolver();
    ts.add(new ReflectionTypeSolver());
    JavaSymbolSolver jss = new JavaSymbolSolver(ts);
    StaticJavaParser.getConfiguration().setSymbolResolver(jss);

    CompilationUnit cu = StaticJavaParser.parse(code);

    for (Type type : cu.findAll(Type.class)) {
      if (type.toString().equals("String")) {
        stringReferenceType = type;
      } else if (type.toString().equals("Object[]")) {
        objectArrReferenceType = type;
      } else if (type.toString().equals("int")) {
        intType = type;
      } else if (type.toString().equals("int[]")) {
        intArrType = type;
      } else if (type.toString().equals("void")) {
        voidType = type;
      }
    }
  }

  @Test
  public void testGenerateType() {
    assertEquals("V", Util.generateType(voidType));
    assertEquals("I", Util.generateType(intType));
    assertEquals("Ljava/lang/String;", Util.generateType(stringReferenceType));
    assertEquals("[I", Util.generateType(intArrType));
    assertEquals("[Ljava/lang/Object;", Util.generateType(objectArrReferenceType));
  }
  
}
