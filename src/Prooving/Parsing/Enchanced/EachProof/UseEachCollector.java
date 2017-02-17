package Prooving.Parsing.Enchanced.EachProof;

import Prooving.Parsing.Enchanced.EnchancedProofParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marsermd on 16.02.2017.
 */
public class UseEachCollector
{
    private List<File> dependencies = new ArrayList<File>();

    public List<File> collect(File current) throws FileNotFoundException
    {
        UseEachCollector collector = new UseEachCollector();
        collector.collectInternal(current);
        collector.dependencies.remove(current);
        return collector.dependencies;
    }

    private void collectInternal(File current) throws FileNotFoundException
    {
        if (dependencies.contains(current))
        {
            return;
        }

        BufferedReader in = new BufferedReader(new FileReader(current));

        String line = null;
        boolean isDependency = false;
        try
        {
            while ((line = in.readLine()) != null)
            {
                System.out.println(line);
                if (line.startsWith("#use"))
                {
                    String rest = line.substring(4).trim();
                    String nextFileName = rest.split(";", 2)[0];
                    File nextFile = new File(current.getParentFile(), nextFileName);
                    collectInternal(nextFile);
                }
                else if (line.startsWith("#each"))
                {
                    isDependency = true;
                }
            }
        }
        catch (FileNotFoundException e)
        {
            throw e;
        }
        catch (IOException e)
        {
            throw new EnchancedProofParser.ParseException("failed parsing file " + current);
        }
        if (isDependency)
        {
            dependencies.add(current);
        }
    }
}
