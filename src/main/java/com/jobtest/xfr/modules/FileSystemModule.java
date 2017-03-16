package com.jobtest.xfr.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import com.jobtest.xfr.io.FileService;

import static java.util.Optional.ofNullable;

/**
 * Created by goddenis on 07.02.2017.
 */
public class FileSystemModule extends AbstractModule {
    private String input;
    private String output;
    private String error;

    public FileSystemModule(String i, String o, String e) {

        input = ofNullable(i).orElse("input");
        output = ofNullable(o).orElse("output");
        error = ofNullable(e).orElse("error");


    }


    @Override
    protected void configure() {
        bindConstant().annotatedWith(Names.named("input.dir")).to(input);
        bindConstant().annotatedWith(Names.named("output.dir")).to(output);
        bindConstant().annotatedWith(Names.named("error.dir")).to(error);

        bind(FileService.class).in(Scopes.SINGLETON);

    }
}
