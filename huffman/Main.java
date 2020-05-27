import java.io.*;
import java.util.*;

/** program to find compression ratio using Huffman coding
 */
public class Main {

	public static void main(String[] args) throws IOException {

		long start = System.currentTimeMillis();
		String inputFileName = args[0];
        BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));
		int oldSize = 0, newSize = 0;
        PriorityQueue<Node> minQueue = new PriorityQueue<Node>();
        Map<Character, Integer> freqMap = new HashMap<>();
		// read in the data and do the work here
		// read a line at a time to enable newlines to be detected and included in the compression
        int c;
        while ((c = buffer.read()) != -1) {
            oldSize += 8; // each character is 8bit
            char letter = (char)c;
            freqMap.put(letter, freqMap.getOrDefault(letter, 0)+1);
        }
        buffer.close();
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            Node tmpNode = new Node(entry.getKey(),entry.getValue());
            minQueue.add(tmpNode);
        }
        while (minQueue.size() > 1) {
            Node x = minQueue.peek();
            minQueue.poll();
            Node y = minQueue.peek();
            minQueue.poll();
            int tmpsize = x.GetCount()+y.GetCount();
            Node tmpNode = new Node('-', tmpsize);
            newSize += tmpsize;
            minQueue.add(tmpNode);
        }
        
		// output the results here
		System.out.println("Input file " + inputFileName + "  Huffman algorithm\n");
		System.out.println("Original file length in bits = " + oldSize);
        System.out.println("Compressed file length in bits = " + newSize);
        System.out.println("Compression ratio = " + (float)newSize/oldSize);

		// end timer and print elapsed time as last line of output
		long end = System.currentTimeMillis();
        System.out.println("\nElapsed time: " + (end - start) + " milliseconds");
    }
    
}
