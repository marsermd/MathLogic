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

    public boolean isNextToken(String regexp)
    {
        return isNextToken(Pattern.compile(regexp));
    }

    public boolean isNextToken(Pattern regexp)
    {
        Matcher matcher = regexp.matcher(string);
        matcher.region(position, string.length());

        return matcher.lookingAt();
    }

    public String getNextTokenNonRegexp(String prefix)
    {
        if (isFinished())
        {
            return null;
        }

        if (!string.startsWith(prefix, position))
        {
            return null;
        }

        position += prefix.length();

        return prefix;
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

    @Override
    public String toString()
    {
        return "StringWithPointer{" +
            "string='" + string + '\'' +
            ", position=" + position +
            '}';
    }
}
