package Prooving.Parsing;

import Prooving.Proof;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Parser.StringWithPointer;
import SyntaxTree.Structure.Expression;

import java.io.*;

/**
 * Created by marsermd on 03.02.2017.
 */
public class ProofParser
{
    public static class ParseException extends RuntimeException
    {
        public ParseException(String reason)
        {
            super(reason);
        }
    }


    public static Proof parseProof(File file, Parser parser) throws FileNotFoundException
    {
        BufferedReader in = new BufferedReader(new FileReader(file));


        Proof proof = new Proof();

        String header;
        try
        {
            header = in.readLine();
        }
        catch (IOException e)
        {
            throw new ParseException("No header in file");
        }

        parseHeader(proof, header, parser);
        parseLines(proof, in, parser);

        return proof;
    }

    private static void parseHeader(Proof proof, String header, Parser parser)
    {
        if (!header.contains("|-"))
        {
            throw new ParseException("No provable sign \"|-\"");
        }

        String[] headerParts = header.split("\\|-");

        if (headerParts.length > 2)
        {
            throw new ParseException("Too much provable signs \"|-\"");
        }

        try
        {
            proof.assumeThat(parser.parseToList(headerParts[0]));
            proof.setGoal(parser.parse(headerParts[1]));
        }
        catch (Parser.BadInputException e)
        {
            System.err.println("bad input at proof header");
            throw e;
        }
    }

    private static void parseLines(Proof proof, BufferedReader in, Parser parser)
    {
        String line;
        try
        {
            while ((line = in.readLine()) != null)
            {
                if (line.trim().length() == 0)
                {
                    return;
                }

                try
                {
                    proof.addLine(parser.parse(line));
                }
                catch (Parser.BadInputException e)
                {
                    System.err.println("bad input at proof line" + proof.getProofLines().size());
                    throw e;
                }
            }
        }
        catch (IOException e)
        {
            throw new ParseException("Reading lines failed");
        }
    }
}
