package SyntaxTree.Structure;

import SyntaxTree.Parser.Parser;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by marsermd on 05.02.2017.
 */
public class ToParsableStringTest
{
    @Test
    public void EqualsToParsed() throws IOException
    {
        BufferedReader in = new BufferedReader(new FileReader("testResources/correct13.in"));

        in.readLine();

        String line;
        Parser parser = Parser.createDefault();
        while ((line = in.readLine()) != null)
        {
            if (line.trim().length() == 0)
            {
                break;
            }
            Expression initial = parser.parse(line);

            StringBuilder builder = new StringBuilder();
            initial.toParsableString(builder);

            Assert.assertEquals(initial, (parser.parse(builder.toString())));
        }
    }
}
