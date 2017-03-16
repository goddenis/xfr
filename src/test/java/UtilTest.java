import com.jobtest.xfr.utils.ArgsParserUtil;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by goddenis on 06.02.2017.
 */
public class UtilTest {
    @Test
    public void argsParserUtilTest() throws Exception {


        Map<String, String> parse = ArgsParserUtil.parse(new String[]{"-i=input", "-o=output", "-e=error", "-t=1000"});

        groupAssertions(parse);

        parse = ArgsParserUtil.parse(new String[]{"--input=input", "--output=output", "-error=error", "-time=1000"});

        groupAssertions(parse);

    }

    private static void groupAssertions(Map<String, String> parse) {
        assertThat(parse,notNullValue());

        assertThat(parse.get("i"),is("input"));
        assertThat(parse.get("o"),is("output"));
        assertThat(parse.get("e"),is("error"));
        assertThat(parse.get("t"),is("1000"));
    }
}
