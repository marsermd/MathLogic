package SyntaxTree.Parser.Matchers;

import SyntaxTree.Parser.Builders.ExpressionBuilder;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Parser.StringWithPointer;
import SyntaxTree.Structure.BinaryOperators.*;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Predicate;
import SyntaxTree.Structure.UnaryOperators.Each;
import SyntaxTree.Structure.UnaryOperators.Increment;
import SyntaxTree.Structure.UnaryOperators.Negation;
import SyntaxTree.Structure.UnaryOperators.Some;
import SyntaxTree.Structure.Variable;
import sun.misc.Regexp;

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
                public Expression createExpression(Stack<Expression> expressions, Parser parser)
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
                public Expression createExpression(Stack<Expression> expressions, Parser parser)
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
                public Expression createExpression(Stack<Expression> expressions, Parser parser)
                {
                    Expression right = expressions.pop();
                    Expression left = expressions.pop();
                    return new Conjunction(left, right);
                }
            };
        }
    }

    public static class EqualsMatcher extends ExpressionMatcher
    {
        @Override
        protected String getRegexp()
        {
            return "=";
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
                public Expression createExpression(Stack<Expression> expressions, Parser parser)
                {
                    Expression right = expressions.pop();
                    Expression left = expressions.pop();
                    return new Equals(left, right);
                }
            };
        }
    }

    public static class PlusMatcher extends ExpressionMatcher
    {
        @Override
        protected String getRegexp()
        {
            return "\\+";
        }

        @Override
        protected ExpressionBuilder getBuilder(String matched)
        {
            return new ExpressionBuilder(matched, 6)
            {
                @Override
                public boolean shouldBuildImediately()
                {
                    return false;
                }

                @Override
                public Expression createExpression(Stack<Expression> expressions, Parser parser)
                {
                    Expression right = expressions.pop();
                    Expression left = expressions.pop();
                    return new Plus(left, right);
                }
            };
        }
    }

    public static class MultiplyMatcher extends ExpressionMatcher
    {
        @Override
        protected String getRegexp()
        {
            return "\\*";
        }

        @Override
        protected ExpressionBuilder getBuilder(String matched)
        {
            return new ExpressionBuilder(matched, 5)
            {
                @Override
                public boolean shouldBuildImediately()
                {
                    return false;
                }

                @Override
                public Expression createExpression(Stack<Expression> expressions, Parser parser)
                {
                    Expression right = expressions.pop();
                    Expression left = expressions.pop();
                    return new Multiply(left, right);
                }
            };
        }
    }
    //endregion

    //region unary operators matchers
    public static class EachMatcher extends ExpressionMatcher
    {
        @Override
        protected String getRegexp()
        {
            return "@" + Variable.VARIABLE_REGEX;
        }

        @Override
        protected ExpressionBuilder getBuilder(String matched)
        {
            return new ExpressionBuilder(matched, 4)
            {
                @Override
                public boolean shouldBuildImediately()
                {
                    return false;
                }

                @Override
                public Expression createExpression(Stack<Expression> expressions, Parser parser)
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
            return "\\?" + Variable.VARIABLE_REGEX;
        }

        @Override
        protected ExpressionBuilder getBuilder(String matched)
        {
            return new ExpressionBuilder(matched, 4)
            {
                @Override
                public boolean shouldBuildImediately()
                {
                    return false;
                }

                @Override
                public Expression createExpression(Stack<Expression> expressions, Parser parser)
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
            return new ExpressionBuilder(matched, 4)
            {
                @Override
                public boolean shouldBuildImediately()
                {
                    return false;
                }

                @Override
                public Expression createExpression(Stack<Expression> expressions, Parser parser)
                {
                    return new Negation(expressions.pop());
                }
            };
        }
    }

    public static class IncrementMatcher extends ExpressionMatcher
    {
        @Override
        protected String getRegexp()
        {
            return "'";
        }

        @Override
        protected ExpressionBuilder getBuilder(String matched)
        {
            return new ExpressionBuilder(matched, 4)
            {
                @Override
                public boolean shouldBuildImediately()
                {
                    return true;
                }

                @Override
                public Expression createExpression(Stack<Expression> expressions, Parser parser)
                {
                    return new Increment(expressions.pop());
                }
            };
        }
    }
    //endregion

    //region values
    public static class VariableMatcher extends ExpressionMatcher
    {
        @Override
        protected String getRegexp()
        {
            return Variable.VARIABLE_REGEX;
        }

        @Override
        protected ExpressionBuilder getBuilder(String matched)
        {
            return new ExpressionBuilder(matched, 1)
            {
                @Override
                public boolean shouldBuildImediately()
                {
                    return true;
                }

                @Override
                public Expression createExpression(Stack<Expression> expressions, Parser parser)
                {
                    return new Variable(matched);
                }
            };
        }
    }

    public static class PredicateMatcher extends ExpressionMatcher
    {
        @Override
        protected String getRegexp()
        {
            return Predicate.PREDICATE_NAME_REGEX + Predicate.PREDICATE_ARGUMENTS_REGEX;
        }

        @Override
        protected ExpressionBuilder getBuilder(String matched)
        {

            return new ExpressionBuilder(matched, 1)
            {
                @Override
                public boolean shouldBuildImediately()
                {
                    return true;
                }

                @Override
                public Expression createExpression(Stack<Expression> expressions, Parser parser)
                {
                    if (matched.contains("("))
                    {
                        String[] parts = matched.split("\\(", 2);
                        String name = parts[0];

                        String[] rawArguments = parts[1].substring(0, parts[1].length() - 1).split(",");
                        Expression[] arguments = new Expression[rawArguments.length];

                        for (int i = 0; i < rawArguments.length; i++)
                        {
                            arguments[i] = parser.parse(rawArguments[i]);
                        }
                        return new Predicate(name, arguments);
                    }
                    else
                    {
                        return new Predicate(matched, new Expression[0]);
                    }
                }
            };
        }
    }
    //endregion
}
