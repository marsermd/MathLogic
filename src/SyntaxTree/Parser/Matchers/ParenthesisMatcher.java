package SyntaxTree.Parser.Matchers;

import SyntaxTree.Parser.Builders.ParenthesisHolder;
import SyntaxTree.Parser.StringWithPointer;

import java.util.regex.Pattern;

/**
 * Created by marsermd on 16.01.2017.
 */
public class ParenthesisMatcher
{
    public Pattern openParenthesis = Pattern.compile("\\(");
    public Pattern closedParenthesis = Pattern.compile("\\)");

    public boolean matchOpenParenthesis(StringWithPointer unparsed)
    {
        return unparsed.getNextToken(openParenthesis) != null;
    }

    public boolean matchClosedParenthesis(StringWithPointer unparsed)
    {
        return unparsed.getNextToken(closedParenthesis) != null;
    }
}
