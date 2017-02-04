package SyntaxTree.Parser.Matchers;

import SyntaxTree.Parser.Builders.ExpressionBuilder;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Parser.StringWithPointer;
import SyntaxTree.Structure.BinaryOperators.*;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Function;
import SyntaxTree.Structure.Predicate;
import SyntaxTree.Structure.UnaryOperators.Each;
import SyntaxTree.Structure.UnaryOperators.Increment;
import SyntaxTree.Structure.UnaryOperators.Negation;
import SyntaxTree.Structure.UnaryOperators.Some;
import SyntaxTree.Structure.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Created by marsermd on 11.01.2017.
 */
public abstract class ExpressionMatcher
{
    protected abstract String getRegexp();
    protected abstract ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser);

    private Pattern pattern;

    public ExpressionMatcher()
    {
        pattern = Pattern.compile(getRegexp());
    }

    protected ExpressionMatcher(String regexp)
    {
        pattern = Pattern.compile(regexp);
    }

    public ExpressionBuilder createBiulder(StringWithPointer unparsed, Parser parser)
    {
        String matched = unparsed.getNextToken(pattern);
        if (matched != null)
        {
            return getBuilder(matched, unparsed, parser);
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
        protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
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
        protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
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
        protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
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
        protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
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
        protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
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
        protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
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
        protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
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
                    return new Each(new Variable(matched.substring(1)), expressions.pop());
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
        protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
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
                    return new Some(new Variable(matched.substring(1)), expressions.pop());
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
        protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
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
        protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
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
            return Variable.VARIABLE_REGEX + "(?![0-9]*\\()";
        }

        @Override
        protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
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
            return Predicate.PREDICATE_NAME_REGEX;
        }

        @Override
        protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
        {
            List<Expression> arguments = new ArrayList<Expression>();
            if (unparsed.getNextToken("\\(") != null)
            {
                arguments = parser.parseToList(unparsed, Pattern.compile(","));
            }

            return new ExpressionBuilder(matched, 1)
            {
                private List<Expression> arguments;

                @Override
                public boolean shouldBuildImediately()
                {
                    return true;
                }

                @Override
                public Expression createExpression(Stack<Expression> expressions, Parser parser)
                {
                    return new Predicate(matched, arguments);
                }

                public ExpressionBuilder withInner(List<Expression> arguments)
                {
                    this.arguments = arguments;
                    return this;
                }
            }.withInner(arguments);
        }
    }

    public static class FunctionMatcher extends ExpressionMatcher
    {
        @Override
        protected String getRegexp()
        {
            return Function.FUNCTION_NAME_REGEX;
        }

        @Override
        protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
        {
            List<Expression> arguments = parser.parseToList(unparsed, Pattern.compile(","));

            return new ExpressionBuilder(matched, 1)
            {
                private List<Expression> arguments;

                @Override
                public boolean shouldBuildImediately()
                {
                    return true;
                }

                @Override
                public Expression createExpression(Stack<Expression> expressions, Parser parser)
                {
                    return new Function(matched, arguments);
                }

                public ExpressionBuilder withInner(List<Expression> arguments)
                {
                    this.arguments = arguments;
                    return this;
                }
            }.withInner(arguments);
        }
    }
    //endregion
}
