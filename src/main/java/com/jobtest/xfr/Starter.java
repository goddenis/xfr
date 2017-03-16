package com.jobtest.xfr;


import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.jobtest.xfr.domain.Entry;
import com.jobtest.xfr.domain.EntryRepository;
import com.jobtest.xfr.io.FileService;
import com.jobtest.xfr.io.XmlParser;
import com.jobtest.xfr.modules.DBModule;
import com.jobtest.xfr.modules.FileSystemModule;
import com.jobtest.xfr.modules.MainModule;
import com.jobtest.xfr.utils.ArgsParserUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Stream;

public class Starter {


    public static final Logger log = Logger.getLogger(Starter.class);

    private static final String INPUT_DIR_KEY = "i";
    private static final String OUTPUT_DIR_KEY = "o";
    private static final String ERROR_DIR_KEY = "e";
    private static long delay;

    @Inject
    FileService fileService;
    @Inject
    XmlParser xmlParser;

    @Inject
    EntryRepository entryRepository;

    public static void main(String[] args) {
        log.info("Starting app");

        Map<String, String> parse = ArgsParserUtil.parse(args);
        try {
            delay = Long.parseLong(parse.get("t"))*1000l;
        } catch (NumberFormatException e){
            delay= 600 *1000l;
        }
        Injector injector
                = Guice.createInjector(
                new MainModule(),
                new FileSystemModule(parse.get(INPUT_DIR_KEY), parse.get(OUTPUT_DIR_KEY), parse.get(ERROR_DIR_KEY)),
                new DBModule());


        Starter application = injector.getInstance(Starter.class);

        while (true) {
            try {
                application.start();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                application.close();
            }
        }
    }

    private void close() {
        log.info("Closing ....");


    }

    private void start() throws Exception {
        log.info("Started");

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                readInput();
            }
        },0,delay);

    }

    private void readInput() {
        try (Stream<Path> walk = Files.walk(fileService.getInputDir().toPath())) {
            walk
                    .parallel()
                    .filter(f -> f.toFile().isFile())
                    .forEach(f -> {
                        log.debug("Start parsing file " + f);

                        try {
                            Object object = xmlParser.getObject(f.toFile());

                            entryRepository.saveEntry((Entry) object);
                            Files.move(f, fileService.getOutputDir().toPath().resolve(f.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                            log.debug("Object parsed " + object.toString());
                        } catch (Exception e){
                            log.error("File not parsed:" + f+'\n'+e.getMessage());

                            try {
                                Files.move(f, fileService.getErrorDir().toPath().resolve(f.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                        }
                    });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
