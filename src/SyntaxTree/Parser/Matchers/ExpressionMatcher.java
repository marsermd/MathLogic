package SyntaxTree.Parser.Matchers;

import SyntaxTree.Parser.Builders.ExpressionBuilder;
import SyntaxTree.Parser.StringWithPointer;
import SyntaxTree.Structure.BinaryOperators.BinaryOperator;
import SyntaxTree.Structure.BinaryOperators.Conjunction;
import SyntaxTree.Structure.BinaryOperators.Disjunction;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.UnaryOperators.Each;
import SyntaxTree.Structure.UnaryOperators.Negation;
import SyntaxTree.Structure.UnaryOperators.Some;
import SyntaxTree.Structure.Variable;

import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Created by marsermd on 11.01.2017.
 */
public abstract class ExpressionMatcher
{
    protected abstract String getRegexp();
    protected abstract ExpressionBuilder getBuilder(String matched);

    private Pattern pattern;

    public ExpressionMatcher()
    {
        pattern = Pattern.compile(getRegexp());
    }

    public ExpressionBuilder createBiulder(StringWithPointer string)
    {
        String matched = string.getNextToken(pattern);
        if (matched != null)
        {
            return getBuilder(matched);
        }
        return null;
    }

    //region BinaryOperator Matchers

    public static class ImplicationMatcher extends ExpressionMatcher
    {
        @Override
        protected String getRegexp()
        {
            return "->";
        }

        @Override
        protected ExpressionBuilder getBuilder(String matched)
        {
            return new ExpressionBuilder(matched, 10)
            {
                @Override
                public boolean shouldBuildImediately()
                {
                    return false;
                }

                @Override
                public Expression createExpression(Stack<Expression> expressions)
                {
                    Expression right = expressions.pop();
                    Expression left = expressions.pop();
                    return new Implication(left, right);
                }
            };
        }
    }

    public static class DisjunctionMatcher extends ExpressionMatcher
    {
        @Override
        protected String getRegexp()
        {
            return "\\|";
        }

        @Override
        protected ExpressionBuilder getBuilder(String matched)
        {
            return new ExpressionBuilder(matched, 9)
            {
                @Override
                public boolean shouldBuildImediately()
                {
                    return false;
                }

                @Override
                public Expression createExpression(Stack<Expression> expressions)
                {
                    Expression right = expressions.pop();
                    Expression left = expressions.pop();
                    return new Disjunction(left, right);
                }
            };
        }
    }

    public static class ConjunctionMatcher extends ExpressionMatcher
    {
        @Override
        protected String getRegexp()
        {
            return "&";
        }

        @Override
        protected ExpressionBuilder getBuilder(String matched)
        {
            return new ExpressionBuilder(matched, 8)
            {
                @Override
                public boolean shouldBuildImediately()
                {
                    return false;
                }

                @Override
                public Expression createExpression(Stack<Expression> expressions)
                {
                    Expression right = expressions.pop();
                    Expression left = expressions.pop();
                    return new Conjunction(left, right);
                }
            };
        }
    }
    //endregion

    private final static String VARIABLE_REGEX = "[A-Z|a-z][0-9]*";

    //region unary operators matchers
    public static class EachMatcher extends ExpressionMatcher
    {
        @Override
        protected String getRegexp()
        {
            return "@" + VARIABLE_REGEX;
        }

        @Override
        protected ExpressionBuilder getBuilder(String matched)
        {
            return new ExpressionBuilder(matched, 7)
            {
                @Override
                public boolean shouldBuildImediately()
                {
                    return false;
                }

                @Override
                public Expression createExpression(Stack<Expression> expressions)
                {
                    return new Each(matched.substring(1), expressions.pop());
                }
            };
        }
    }

    public static class SomeMatcher extends ExpressionMatcher
    {
        @Override
        protected String getRegexp()
        {
            return "\\?" + VARIABLE_REGEX;
        }

        @Override
        protected ExpressionBuilder getBuilder(String matched)
        {
            return new ExpressionBuilder(matched, 7)
            {
                @Override
                public boolean shouldBuildImediately()
                {
                    return false;
                }

                @Override
                public Expression createExpression(Stack<Expression> expressions)
                {
                    return new Some(matched.substring(1), expressions.pop());
                }
            };
        }
    }

    public static class NegationMatcher extends ExpressionMatcher
    {
        @Override
        protected String getRegexp()
        {
            return "!";
        }

        @Override
        protected ExpressionBuilder getBuilder(String matched)
        {
            return new ExpressionBuilder(matched, 7)
            {
                @Override
                public boolean shouldBuildImediately()
                {
                    return false;
                }

                @Override
                public Expression createExpression(Stack<Expression> expressions)
                {
                    return new Negation(expressions.pop());
                }
            };
        }
    }
    //endregion

    public static class VariableMatcher extends ExpressionMatcher
    {
        @Override
        protected String getRegexp()
        {
            return  VARIABLE_REGEX;
        }

        @Override
        protected ExpressionBuilder getBuilder(String matched)
        {
            return new ExpressionBuilder(matched, 1)
            {
                @Override
                public boolean shouldBuildImediately()
                {
                    return false;
                }

                @Override
                public Expression createExpression(Stack<Expression> expressions)
                {
                    return new Variable(matched);
                }
            };
        }
    }
}
