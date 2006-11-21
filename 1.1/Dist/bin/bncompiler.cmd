@echo off
java -classpath "../depends/lineargs.jar;../depends/antlr.jar;../depends/activation.jar;../depends/jaxb-api.jar;../depends/jaxb-impl.jar;../depends/jaxb1-impl.jar;../depends/jsr173_1.0_api.jar;../lib/java/binarynotes.jar;../bin/bncompiler.jar" org.bn.compiler.Main %1 %2 %3 %4 %5 %6 %7 %8

