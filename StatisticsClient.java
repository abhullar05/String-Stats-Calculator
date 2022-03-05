import javax.swing.JOptionPane;
import java.io.*;
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
public class StatisticsClient {
    public static void main(String[] args) {
        try {
            JOptionPane.showMessageDialog(null,
                    "Welcome to the STRING ANALYSER", "Client GUI",
                    JOptionPane.PLAIN_MESSAGE);
            String hostName = JOptionPane.showInputDialog(null,
                    "Please enter the host name",
                    "Client GUI", JOptionPane.QUESTION_MESSAGE);
            if (hostName == null) {
                JOptionPane.showMessageDialog(null, "Thanks for using the STRING ANALYSER");
                return;
            }

            String portNumberString = JOptionPane.showInputDialog(null,
                    "Please enter the port number",
                    "Client GUI", JOptionPane.QUESTION_MESSAGE);
            if (portNumberString == null) {
                JOptionPane.showMessageDialog(null, "Thanks for using the STRING ANALYSER");
                return;
            }
            Socket socket = new Socket();
            try {
                int portNumber = Integer.parseInt(portNumberString);
                // Port number : 8000, DNS : "localhost"

                socket = new Socket(hostName, portNumber);
            } catch (Exception e) {
                System.out.println("Error establishing connection");
                JOptionPane.showMessageDialog(null,
                        "Error establishing connection",
                        "Client GUI", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null, "Connection established ! ",
                    "Client GUI", JOptionPane.INFORMATION_MESSAGE);
            BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            String string = "";
            int value;

            do {
                do {
                    string = JOptionPane.showInputDialog(null,
                            "Please enter the string", "Client GUI", JOptionPane.QUESTION_MESSAGE);
                    if (string == null) {
                        JOptionPane.showMessageDialog(null,
                                "Thanks for using the STRING ANALYSER");
                        return;
                    }
                    if (string.equals(""))
                        JOptionPane.showMessageDialog(null,
                                "Empty string!", "Client GUI", JOptionPane.ERROR_MESSAGE);

                } while (string.equals(""));
                pw.write(string);
                pw.println();
                pw.flush();
                System.out.printf("Sent to server:\n%s\n", string);

                String statsToDisplay = bfr.readLine();

                System.out.printf("Received from server:\n%s\n", statsToDisplay);
                String[] thingsToDisplay = statsToDisplay.split("_");
                for (String item : thingsToDisplay) {
                    JOptionPane.showMessageDialog(null,
                            item, "Client GUI", JOptionPane.INFORMATION_MESSAGE);
                }
                value = JOptionPane.showConfirmDialog(null,
                        "Do you wish to enter another string ?",
                        "Client GUI", JOptionPane.YES_NO_OPTION);
            } while (value == JOptionPane.YES_OPTION);
            pw.close();
            bfr.close();
            JOptionPane.showMessageDialog(null,
                    "Thanks for using the STRING ANALYSER");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
