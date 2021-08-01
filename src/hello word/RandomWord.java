import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = null;
        int i = 0;
        while(!StdIn.isEmpty()) {
            String str = StdIn.readString();
            if(StdRandom.bernoulli((float)1.0f/++i))
                champion = str;
        }
        StdOut.println(champion);
    }
}
