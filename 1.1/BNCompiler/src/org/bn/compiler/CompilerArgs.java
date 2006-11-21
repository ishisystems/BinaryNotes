package org.bn.compiler;

import org.lineargs.*;
import org.lineargs.constraints.*;

public class CompilerArgs {
    @Option (name = "--modulesPath", shortName = "-mp", description="Path to directory with available modules", isOptional = true)
    @RegexConstraint ( mask = ".+" )    
    private String modulesPath     = "modules/";
    
    @Option(name = "--moduleName", shortName = "-m", 
                  description = "Binding module name")
    @RegexConstraint ( mask = ".+" )                  
    private String moduleName = null;
    
    @Option(name = "--outputDir", shortName = "-o", 
                  description = "Output directory name",
                  isOptional = true)
    @RegexConstraint ( mask = ".+" )                  
    private String outputDir = "output/";
    
    @Option(name = "--fileName", shortName = "-f", 
                  description = "Input ASN.1 filename")
    @RegexConstraint ( mask = ".+" )                  
    private String inputFileName = null;
    
    @Option(name = "--namespace", shortName = "-ns", 
                  description = "Generate classes with specified namespace/package name", 
                  isOptional = true)
    @RegexConstraint ( mask = ".+" )
    private String namespace = null;

    public String getModulesPath() {
        return modulesPath;
    }

    public void setModulesPath(String modulesPath) {
        this.modulesPath = modulesPath;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
