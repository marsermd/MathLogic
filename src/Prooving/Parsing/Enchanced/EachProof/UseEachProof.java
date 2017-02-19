package Prooving.Parsing.Enchanced.EachProof;

import Prooving.Parsing.Enchanced.EnchancedProofParser;
import Prooving.Proof;
import SyntaxTree.Parser.Builders.ExpressionBuilder;
import SyntaxTree.Parser.Matchers.ConcreteFormulaMatcher;
import SyntaxTree.Parser.Matchers.StandardMatchers.ExpressionMatcher;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Parser.StringWithPointer;
import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.BinaryOperators.Equals;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.BinaryOperators.Plus;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Predicate;
import SyntaxTree.Structure.UnaryOperators.Each;
import SyntaxTree.Structure.Variable;
import jdk.nashorn.internal.runtime.ParserException;

import java.io.*;
import java.util.*;


/**
 * Created by marsermd on 17.02.2017.
 */
public class UseEachProof
{
    private File file;
    private Proof proof;
    private Map<Variable, AnyFormula> quantified;

    private Expression matcher;
    private boolean noHash = false;

    public UseEachProof(File file, EnchancedProofParser enchancedParser, Parser parser) throws FileNotFoundException
    {
        this.file = file.getAbsoluteFile();

        BufferedReader in = new BufferedReader(new FileReader(file));

        String header;
        try
        {
            while ((header = in.readLine()) != null && (!header.contains("|-")))
            {
                if (header.startsWith("#noHash"))
                {
                    noHash = true;
                }
            }
        } catch (IOException e)
        {
            throw new EnchancedProofParser.ParseException("No header in file");
        }

        createEachProof(enchancedParser, parser);
    }

    public File getFile()
    {
        return file;
    }

    public Expression getEachGoal()
    {
        return proof.getGoal();
    }

    public List<Expression> prove(Expression target)
    {
        for (AnyFormula formula: quantified.values())
        {
            formula.setEqualExpression(null);
        }

        if (!matcher.fairEquals(target))
        {
            throw new EnchancedProofParser.ParseException("#use failed target = " + target + " matcher = " + getEachGoal());
        }
        List<Expression> lines = new ArrayList<Expression>();
        Expression last = proof.getGoal();

        while (last instanceof Each)
        {
            Each each = (Each) last;

            Expression phi = each.getExpression();
            phi = phi.replace((Variable)each.getQuantified(), quantified.get(each.getQuantified()).getEqualExpression());

            lines.add(each);
            lines.add(new Implication(each, phi));
            lines.add(phi);
            last = phi;
        }
        return lines;
    }

    public Proof getProof()
    {
        return proof;
    }
    public Expression getMatcher()
    {
        for (AnyFormula formula: quantified.values())
        {
            formula.setEqualExpression(null);
        }
        return matcher;
    }


    private void createEachProof(EnchancedProofParser enchancedParser, Parser parser) throws FileNotFoundException
    {
        if (!noHash)
        {
            parser = createHashVariableParser(parser);
        }
        proof = enchancedParser.parseProofInternal(file, parser, null, false);
        Expression last = proof.getGoal();

        matcher = last;
        quantified = new HashMap<Variable, AnyFormula>();
        for (Variable variable:proof.getGoal().getFreeAndCache())
        {
            AnyFormula anyFormula = new AnyFormula();
            quantified.put(variable, anyFormula);
            matcher = matcher.replace(variable, anyFormula);
        }

        for (Variable variable:proof.getGoal().getFreeAndCache())
        {
            last = addEachRule(proof, last, variable);
        }

        proof.setGoal(proof.getProofLines().get(proof.getProofLines().size() - 1));
    }

    private Parser createHashVariableParser(Parser oldParser)
    {
        Parser parser = new Parser();
        parser.addMatcher(
            new ExpressionMatcher()
            {
                @Override
                protected String getRegexp()
                {
                    return Variable.VARIABLE_REGEX;
                }

                @Override
                protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
                {
                    return new ExpressionBuilder(matched, 0)
                    {
                        @Override
                        public boolean shouldBuildImediately()
                        {
                            return true;
                        }

                        @Override
                        public Expression createExpression(Stack<Expression> expressions, Parser parser)
                        {
                            if (!"0".equals(matched))
                            {
                                return new Variable(matched + Math.abs(hash));
                            }
                            else
                            {
                                return new Variable("0");
                            }
                        }

                        private int hash;

                        public ExpressionBuilder withHash(int hash)
                        {
                            this.hash = hash;
                            return this;
                        }
                    }.withHash(hash);
                }
                private int hash;

                public ExpressionMatcher withHash(int hash)
                {
                    this.hash = hash;
                    return this;
                }
            }.withHash(file.hashCode())
        );
        System.out.println(file.getPath() + " " + file.hashCode());
        parser.addMatchers(oldParser.getMatchers());
        return parser;
    }

    //returns last added expression
    private Expression addEachRule(Proof proof, Expression psi, Variable x)
    {
        Expression truth = new Implication(
            new Predicate("T1234999"),
            new Implication(
                new Predicate("T1234999"),
                new Predicate("T1234999")
            )
        );
        Expression target = new Each(
            x,
            psi
        );
        proof.addLine(
            new Implication(
                psi,
                new Implication(
                    truth,
                    psi
                )
            )
        );
        proof.addLine(
            new Implication(
                truth,
                psi
            )
        );
        proof.addLine(
            new Implication(
                truth,
                target
            )
        );
        proof.addLine(truth);
        proof.addLine(target);
        return target;
    }

}
