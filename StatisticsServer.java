import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * HW - 11 Challenge -- GUI and Network IO
 *
 * Calculates stats for a string by receiving input through simple GUIs.
 *
 * Port number : 8000
 *
 * @author Advit Bhullar, L-24
 *
 * @version November 6, 2021
 *
 */

public class StatisticsServer {
    public static void main(String[] args) {
        try {
            // portNumber = 8000
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Waiting for the client to connect...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");
            BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());

            String string = "";
            string = bfr.readLine();
            do {
                if (string == null)
                    break;
                System.out.printf("Received from client:\n%s\n", string);
                int length = string.length();
                int numberOfWords = string.split(" ").length;
                int numberOfPunctuations = 0;
                for (int i = 0; i < string.length(); i++) {
                    char c = string.charAt(i);
                    if (!Character.isLetterOrDigit(c) && c != ' ')
                        numberOfPunctuations++;
                }
                int charWithoutSpaces = string.replaceAll(" ", "").length();
                String frequencyOfDigits = "";
                int[] digits = new int[10];
                for (int i = 0; i < string.length(); i++) {
                    char c = string.charAt(i);
                    if (Character.isDigit(c)) {
                        for (int j = 0; j < 10; j++) {
                            if (Integer.parseInt(String.valueOf(c)) == j)
                                digits[j]++;

                        }
                    }
                }
                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0)
                        frequencyOfDigits = frequencyOfDigits + i + "-" + digits[i] + ", ";
                }
                int[] letters = new int[26];

                for (int i = 0; i < string.length(); i++) {
                    if (Character.isLetter(string.charAt(i))) {
                        char c = Character.toLowerCase(string.charAt(i));
                        for (int j = 0; j < 26; j++) {
                            if (c == 97 + j)
                                letters[j]++;
                        }
                    }
                }
                String frequencyOfLetters = "";
                for (int i = 0; i < letters.length; i++) {
                    if (letters[i] != 0)
                        frequencyOfLetters = frequencyOfLetters + (char) (i + 97) + "-" + letters[i] + ", ";
                }
                if (!frequencyOfLetters.equals(""))
                    frequencyOfLetters = frequencyOfLetters.substring(0 , frequencyOfLetters.length() - 2);
                if (!frequencyOfDigits.equals(""))
                    frequencyOfDigits = frequencyOfDigits.substring(0 , frequencyOfDigits.length() - 2);
                String statsToDisplay = "Length : " + length + "_" +
                        "Number of words : " + numberOfWords + "_"  +
                        "Number of punctuation marks : " + numberOfPunctuations + "_"  +
                        "Character count without spaces : " + charWithoutSpaces  + "_" +
                        "Frequency of digits : " + frequencyOfDigits + "_" +
                        "Frequency of each letter present : " + frequencyOfLetters;
                pw.write(statsToDisplay);
                pw.println();
                pw.flush();
                System.out.printf("Sent to client:\n%s\n", statsToDisplay);
                string = bfr.readLine();

            } while(string != null);
            pw.close();
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
