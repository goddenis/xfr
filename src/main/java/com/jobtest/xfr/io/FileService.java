package com.jobtest.xfr.io;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.io.File;

public class FileService {

    private File inputDir;
    private File outputDir;
    private File errorDir;

    @Inject
    public FileService(@Named("input.dir") String inputDir,
                       @Named("output.dir") String outputDir,
                       @Named("error.dir") String errorDir) {

        this.inputDir = new File(inputDir);
        this.outputDir = new File(outputDir);
        this.errorDir = new File(errorDir);

        if (!this.inputDir.exists()){
            this.inputDir.mkdirs();
        }
        if (!this.outputDir.exists()){
            this.outputDir.mkdirs();
        }
        if (!this.errorDir.exists()){
            this.errorDir.mkdirs();
        }
    }

    public File getInputDir() {
        return inputDir;
    }

    public File getOutputDir() {
        return outputDir;
    }

    public File getErrorDir() {
        return errorDir;
    }
}
