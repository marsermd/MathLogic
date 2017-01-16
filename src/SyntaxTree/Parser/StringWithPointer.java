package SyntaxTree.Parser;
import org.junit.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by marsermd on 09.01.2017.
 */
public class StringWithPointer
{
    private String string;

    private int position = 0;

    public StringWithPointer(String string)
    {
        this.string = string;
    }

    public boolean isFinished()
    {
        return position == string.length();
    }

    public void moveToNextPosition()
    {
        if (position < string.length())
        {
            position++;
        }
        else
        {
            throw new IllegalStateException();
        }
    }

    public String getNextToken(Pattern regexp)
    {
        if (isFinished())
        {
            return null;
        }

        Matcher matcher = regexp.matcher(string);
        matcher.region(position, string.length());

        if (!matcher.lookingAt())
        {
            return null;
        }

        position += matcher.group().length();
        return matcher.group();
    }
}
