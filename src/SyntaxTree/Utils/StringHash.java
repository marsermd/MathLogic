package SyntaxTree.Utils;

/**
 * Created by marsermd on 31.01.2017.
 */
public class StringHash
{
    public static int calculate(String str, int prime)
    {
        int result = 0;
        for (int i = 0; i < str.length(); i++)
        {
            result *= prime;
            result += str.charAt(i);
        }
        return result;
    }
}
