@echo off
set BN_HOME=%~dp0
java -classpath "%BN_HOME%/../depends/lineargs.jar;%BN_HOME%/../depends/antlr.jar;%BN_HOME%/../depends/activation.jar;%BN_HOME%/../depends/jaxb-api.jar;%BN_HOME%/../depends/jaxb-impl.jar;%BN_HOME%/../depends/jaxb1-impl.jar;%BN_HOME%/../depends/jsr173_1.0_api.jar;%BN_HOME%/../lib/java/binarynotes.jar;%BN_HOME%/../bin/bncompiler.jar" org.bn.compiler.Main %1 %2 %3 %4 %5 %6 %7 %8
