import Prooving.Parsing.Enchanced.EnchancedProofParser;
import Prooving.Parsing.ProofParser;
import Prooving.Proof;
import Prooving.ProofCheckers.Boolean.BooleanProofChecker;
import Prooving.ProofCheckers.Boolean.BooleanProofResult;
import Prooving.ProofCheckers.Rewriter.RewriterProofChecker;
import Prooving.ProofCheckers.Rewriter.RewriterProofResult;
import SyntaxTree.Parser.Matchers.ConcreteFormulaMatcher;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.UnaryOperators.Increment;
import SyntaxTree.Structure.Variable;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by marsermd on 08.01.2017.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException
    {
        if (args.length == 0)
        {
            Scanner in = new Scanner(System.in);
            args = in.nextLine().split(" ");
        }
        if (args.length != 3)
        {
            System.err.println("usage: --%option% input output");
            return;
        }

        File inFile = new File(args[1]);
        File outFile = new File(args[2]);

        if ("--sqr".equals(args[0]))
        {
            sqr(inFile, outFile);
        }
        else if ("--compile".equals(args[0]))
        {
            File file;
            file = new File(args[1]);

            System.out.println("parsing");
            Proof proof = EnchancedProofParser.parseProof(file, Parser.createDefault());

            System.out.println("checking");
            BooleanProofChecker checker = BooleanProofChecker.getDefaultChecker(proof);
            System.out.println(checker.Check().lastExpressionResult.isRight());
            System.out.println(proof.getProofLines().size());
            if (checker.Check().lastExpressionResult.isWrong())
            {
                for (int i = 0; i < proof.getProofLines().size(); i++)
                {
                    System.err.println(i + ")" + proof.getProofLines().get(i).toParsableString());
                }
                System.err.println("Wrong!" + proof.getAssumptions().size() + proof.getAssumptions());
                return;
            }

            System.out.println("|-" + proof.getProofLines().get(proof.getProofLines().size() - 1));
            HashSet<Expression> usedExpressions = new HashSet<Expression>();
            for (Expression line : proof.getProofLines())
            {
                usedExpressions.add(line);
                System.out.println(line.toParsableString());
            }
            System.out.println(proof.getProofLines().size() + " " + usedExpressions.size());
        }
        else if ("--deduct".equals(args[0]))
        {
            deduct(inFile, outFile);
        }

//        RewriterProofChecker proofChecker = RewriterProofChecker.getDeductChecker(proof);
//        RewriterProofResult result = proofChecker.Check();
//
//        System.err.println(!result.failed);
//        System.err.println(result.generatedProof.getProofLines().size());
//
//        BooleanProofChecker generatedChecker = BooleanProofChecker.getDeductChecker(proof);
//        BooleanProofResult generatedResult = generatedChecker.Check();
//
//        System.out.println(generatedResult.lastExpressionResult.isRight());
    }

    private static void deduct(File inFile, File outFile)
    {

        System.out.println("parsing");
        Proof proof = null;
        try
        {
            proof = ProofParser.parseProof(inFile, Parser.createDefault());
        } catch (FileNotFoundException e)
        {
            System.err.println("file not found" + inFile);
        }

        System.out.println("checking");
        if (proof.getAssumptions().size() > 0)
        {
            deduct(proof, outFile);
        }
        else
        {
            check(proof, outFile);
        }

    }

    private static void check(Proof proof, File outFile)
    {
        BooleanProofChecker checker = BooleanProofChecker.getDefaultChecker(proof);
        BooleanProofResult result = checker.Check();
        try (BufferedWriter out = new BufferedWriter(new FileWriter(outFile)))
        {
            if (result.lastExpressionResult.isWrong())
            {
                System.out.println("Вывод некорректен начиная с формулы нмоер " + (result.lineId + 1));
                System.out.println("reason:" + result.lastExpressionResult.reason);
                return;
            }
            else
            {
                proof.printProof(out);
            }
        }
        catch (IOException e)
        {
            System.err.println("failed writing to file" + outFile);
        }
    }

    private static void deduct(Proof proof, File outFile)
    {
        RewriterProofChecker checker = RewriterProofChecker.getDeductChecker(proof);
        RewriterProofResult result = checker.Check();
        try (BufferedWriter out = new BufferedWriter(new FileWriter(outFile)))
        {
            if (result.failed)
            {
                out.write("Вывод некорректен начиная с формулы нмоер " + (result.lineId + 1));
                out.newLine();
                out.write("reason:" + result.failureReason);
                out.newLine();
            }
            else
            {
                result.generatedProof.printProof(out);
            }
        }
        catch (IOException e)
        {
            System.err.println("failed writing to file" + outFile);
        }
    }

    private static void sqr(File inFile, File outFile) throws FileNotFoundException
    {
        int a;
        try (Scanner in = new Scanner(inFile))
        {
            a = in.nextInt();
        }

        Expression aExpr = new Variable("0");
        for (int i = 0; i < a; i++)
        {
            aExpr = new Increment(aExpr);
        }

        File proofFile = new File("compiledProofs/z)(t+1)X(t+1)=tXt+2t+1");

        Parser parser = new Parser();
        parser.addMatcher(new ConcreteFormulaMatcher("t(?![0-9])", aExpr));
        parser.addMatchers(Parser.getDefaultMatchers());
        Proof proof = ProofParser.parseProof(proofFile, parser);

        try (BufferedWriter out = new BufferedWriter(new FileWriter(outFile)))
        {
            proof.printProof(out);
        }
        catch (IOException e)
        {
            System.err.println("failed writing to file" + outFile);
        }
    }
}
