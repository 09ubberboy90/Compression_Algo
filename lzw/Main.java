import java.io.*;
import java.util.*;
 
/** program to find compression ratio using LZW compression
 */
public class Main {

	public static void main(String[] args) throws IOException {

		long start = System.currentTimeMillis();
		String inputFileName = args[0];
        Trie t = new Trie();
        int codeword = 8;
        int dicSize = 256;
        String str = "";

        int oldSize = 0, newSize = 0, occupiedSpace = 0;
        // add the normal ascii character to the trie
        for (int i = 0; i < 128; i++) {
            t.insert(String.valueOf((char)i));
            occupiedSpace++;
        }
        BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));

        int c;
        Node actualNode = t.getRoot();
        int actualIndex = 0;
        while ((c = buffer.read()) != -1) {
            oldSize += 8; // each character is 8 bit in the orignal file
            String tmp = str + (char)c; //add the character to the actual string
            Node searchNode = t.searchNode(tmp, actualNode, actualIndex);
            if (searchNode != null) { // if its present get the next character
                str = tmp;
                actualNode = searchNode;
                actualIndex++;
            } else {
                if (dicSize < occupiedSpace) { //increase dicsize if the the dictionarry is full
                    codeword++; // increase the codeword
                    dicSize *= 2; // double the dicsize equivqlent to dicsize^(codeword)
                }
                t.insertAt(tmp, actualNode, actualIndex); // add to the trie at that node
                str = String.valueOf((char) c); // reset the string
                newSize += codeword; // virtually add the codeword lenght to the size
                occupiedSpace++;// increase the available size
                actualNode = t.getRoot(); // Reset the node for search
                actualIndex = 0;
            }
        }
        newSize += codeword; //  add the final character that is not added
		buffer.close();

		System.out.println("Input file " + inputFileName + "  LZW algorithm\n");		
		System.out.println("Original file length in bits = " + oldSize);
        System.out.println("Compressed file length in bits = " + newSize);
        System.out.println("Compression ratio = " + (float)newSize/oldSize);
		long end = System.currentTimeMillis();
		System.out.println("\nElapsed time: " + (end - start) + " milliseconds");
	}

}
