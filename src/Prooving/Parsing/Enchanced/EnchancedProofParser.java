package Prooving.Parsing.Enchanced;

import Prooving.Parsing.Enchanced.EachProof.UseEachCollector;
import Prooving.Parsing.Enchanced.EachProof.UseEachProof;
import Prooving.Proof;
import Prooving.ProofCheckers.Rewriter.RewriterProofChecker;
import Prooving.ProofCheckers.Rewriter.RewriterProofResult;
import SyntaxTree.Parser.Matchers.ConcreteFormulaMatcher;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Parser.StringWithPointer;
import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;
import jdk.nashorn.internal.runtime.ParserException;
import org.omg.CORBA.Any;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by marsermd on 11.02.2017.
 */
public class EnchancedProofParser
{
    private Parser initialExpressionParser;
    private final List<File> eachFiles = new ArrayList<File>();
    private final List<UseEachProof> axiomEachFiles = new ArrayList<UseEachProof>();
    private final Map<File, UseEachProof> eachProofs = new HashMap<File, UseEachProof>();

    private EnchancedProofParser(Parser parser) throws FileNotFoundException
    {
        initialExpressionParser = parser;
    }

    public static Proof parseProof(File file, Parser parser) throws FileNotFoundException
    {
        EnchancedProofParser enchancedProofParser = new EnchancedProofParser(parser);
        return enchancedProofParser.parseProofInternal(file, parser);
    }

    public Proof parseProofInternal(File file, Parser parser) throws FileNotFoundException
    {
        return parseProofInternal(file, parser, null, true);
    }

    public Proof parseProofInternal(File file, Parser parser, Expression expectedGoal, boolean shouldCollectEaches) throws FileNotFoundException
    {
        BufferedReader in = new BufferedReader(new FileReader(file));

        Proof proof = new Proof();

        int deduct = 0;
        String header;
        try
        {
            while ((header = in.readLine()) != null && (!header.contains("|-")))
            {
                if (header.startsWith("#deduct"))
                {
                    deduct = Integer.parseInt(header.split(" ")[1]);
                    continue;
                }
                else if (header.startsWith("#match"))
                {
                    if (expectedGoal != null)
                    {
                        String formula = header.substring("#match".length());
                        parser = matchParser(parser, formula, expectedGoal);
                    }
                }
                else if (header.startsWith("#each"))
                {
                    continue;
                }
                else if (header.startsWith("#noHash"))
                {
                    continue;
                }
                else if (header.startsWith("##"))
                {
                    //comment
                    continue;
                }
                else
                {
                    throw new ParserException("bad pre-header line " + header);
                }
            }
        }
        catch (IOException e)
        {
            throw new ParseException("No header in file");
        }

        if (shouldCollectEaches)
        {
            collectEaches(file);
        }

        parseHeader(proof, header, parser);
        parseLines(proof, in, parser, file);

        proof = deductProof(proof, deduct, file);

        if (shouldCollectEaches)
        {
            proof = copyEachProofs(proof);
        }

        return proof;
    }

    private void collectEaches(File currentFile) throws FileNotFoundException
    {
        for (int i = 1; i <= 8; i++)
        {
            UseEachProof proof = new UseEachProof(new File("arithmeticalAxioms/" + i), this, initialExpressionParser);
            axiomEachFiles.add(proof);
            eachProofs.put(proof.getFile(), proof);
            eachFiles.add(proof.getFile());
        }
        eachFiles.addAll(new UseEachCollector().collect(currentFile));
        for (File eachFile: eachFiles)
        {
            eachProofs.put(eachFile.getAbsoluteFile(), new UseEachProof(eachFile, this, initialExpressionParser));
        }
    }

    private Proof copyEachProofs(Proof proof)
    {
        Proof newProof = new Proof();
        newProof.assumeThat(proof.getAssumptions());

        // eachFiles are already in right order!
        for (File eachFile: eachFiles)
        {
            UseEachProof eachProof = eachProofs.get(eachFile);
            newProof.getAssumptions().removeAll(eachProof.getProof().getAssumptions());
            newProof.addLines(eachProof.getProof().getProofLines());
        }
        newProof.addLines(proof.getProofLines());

        return newProof;
    }

    private Parser matchParser(Parser oldParser, String formula, Expression expectedGoal)
    {
        Pattern anyFormulaPattern = Pattern.compile("[A-Z|a-z][0-9]*");
        Matcher matcher = anyFormulaPattern.matcher(formula);

        Map<String, AnyFormula> variables = new HashMap<String, AnyFormula>();

        Parser parser = new Parser();

        while (matcher.find())
        {
            AnyFormula variable = new AnyFormula();
            variables.put(matcher.group(), variable);
        }

        for (String variableName: variables.keySet())
        {
            parser.addMatcher(new ConcreteFormulaMatcher(variableName, variables.get(variableName)));
        }
        parser.addMatchers(oldParser.getMatchers());

        Expression shouldMatchGoal = parser.parse(formula);
        if (!shouldMatchGoal.fairEquals(expectedGoal))
        {
            throw new ParseException(expectedGoal + " does not match " + shouldMatchGoal);
        }

        parser = new Parser();
        for (String variableName: variables.keySet())
        {
            parser.addMatcher(new ConcreteFormulaMatcher(variableName, variables.get(variableName).getEqualExpression()));
        }
        parser.addMatchers(oldParser.getMatchers());

        return parser;
    }


    private Proof deductProof(Proof proof, int deduct, File file)
    {
        for (int i = 0; i < deduct; i++)
        {
            RewriterProofChecker rewriter = RewriterProofChecker.getDefaultChecker(proof);
            RewriterProofResult result = rewriter.Check();
            if (result.failed)
            {
                throw new ParseException("failed deducting in " + file + "; " + i + " deducted");
            }
            proof = result.generatedProof;
        }
        return proof;
    }

    private void parseHeader(Proof proof, String header, Parser parser)
    {
        if (!header.contains("|-"))
        {
            throw new ParseException("No provable sign \"|-\" in " + header);
        }

        String[] headerParts = header.split("\\|-");

        if (headerParts.length > 2)
        {
            throw new ParseException("Too much provable signs \"|-\"");
        }

        proof.assumeThat(parser.parseToList(headerParts[0]));
        proof.setGoal(parser.parse(headerParts[1]));
    }

    private void parseLines(Proof proof, BufferedReader in, Parser parser, File file)
    {
        String line;
        try
        {
            while ((line = in.readLine()) != null)
            {
                if (line.trim().length() == 0)
                {
                    continue;
                }
                parseLine(proof, line, parser, file);

            }
        }
        catch (IOException e)
        {
            throw new ParseException("Reading lines failed");
        }
    }

    private void parseLine(Proof proof, String line, Parser parser, File file)
    {
        if (line.startsWith("#use"))
        {
            String rest = line.substring(4).trim();
            String[] arguments = rest.split(";", 3);
            if (arguments.length < 2 || arguments.length > 3)
            {
                throw new ParseException("invalid using " + line + " in file " + file);
            }
            String nextFileName = arguments[0];
            Expression goalExpression = parser.parse(arguments[1].trim());
            String replacements = arguments.length == 3 ? arguments[2] : "";
            File nextFile = new File(file.getParentFile().toPath().resolve(nextFileName).toString()).getAbsoluteFile();
            if (!eachProofs.keySet().contains(nextFile))
            {
                includeFile(proof, parser, nextFile, goalExpression, replacements);
            }
            else
            {
                useEachProof(proof, eachProofs.get(nextFile), goalExpression);
            }
        }
        else
        {
            Expression expression = parser.parse(line);
            for (UseEachProof axiom: axiomEachFiles)
            {
                if (axiom.getMatcher().fairEquals(expression) && !axiom.getFile().getAbsoluteFile().equals(file.getAbsoluteFile()))
                {
                    String localPath = axiom.getFile().getAbsolutePath();
                    line = "#use " + localPath + ";" + line;
                    parseLine(proof, line, parser, file);
                }
            }
            proof.addLine(expression);
        }
    }

    private void includeFile(Proof proof, Parser parser, File nextFile, Expression goalExpression, String replacements)
    {
        Parser newParser = new Parser();
        for (String replacement: replacements.split(";"))
        {
            if (replacement.trim().length() == 0)
            {
                continue;
            }
            String[] fromTo = replacement.split(" to ");
            if (fromTo.length != 2)
            {
                throw new ParseException("invalid to " + replacement);
            }

            Expression from = parser.parse(fromTo[1]);

            newParser.addMatcher(new ConcreteFormulaMatcher(fromTo[0].trim(), from));
        }
        newParser.addMatchers(initialExpressionParser.getMatchers());

        Proof newProof = null;
        try
        {
            newProof = parseProofInternal(nextFile, newParser, goalExpression, false);
        }
        catch (FileNotFoundException e)
        {
            throw new ParseException("file " + nextFile + " could not be read");
        }

        for (Expression line: newProof.getProofLines())
        {
            proof.addLine(line);
        }
    }

    private void useEachProof(Proof proof, UseEachProof eachProof, Expression goalExpression)
    {
        proof.addFirstAssumption(eachProof.getEachGoal());

        List<Expression> expressions = eachProof.prove(goalExpression);
        proof.addLines(expressions);
    }

    public static class ParseException extends RuntimeException
    {
        public ParseException(String reason)
        {
            super(reason);
        }
    }
}
