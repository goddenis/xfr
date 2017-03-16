package com.jobtest.xfr.utils;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArgsParserUtil {

    public static Pattern pattern = Pattern.compile("^(?<k>-?-(i|o|e|t)(nput|utput|rror|ime)?)=(?<v>.*)");


    public static Map<String, String> parse(String... args) {


        return Stream.of(args)
                .sequential()
                .filter(pattern.asPredicate())

                .map(s -> s.split("="))
                .filter(m -> m.length == 2)
                .collect(Collectors.toMap(k -> k[0].replace("-", "").substring(0, 1), k -> k[1]));


    }

}
