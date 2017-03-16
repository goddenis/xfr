package com.jobtest.xfr.modules;

import com.google.inject.AbstractModule;
import com.jobtest.xfr.Starter;
import com.jobtest.xfr.io.XmlParser;

public class MainModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Starter.class);
        bind(XmlParser.class);
    }



}

