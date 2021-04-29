import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) throws IOException {
        String serverAddress = "127.0.0.1";
        int PORT = 8100;
        String stopMessage = "exit";
        while(true) {
            try (Socket socket = new Socket(serverAddress, PORT);
                 PrintWriter out =
                         new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(socket.getInputStream()))) {
                Scanner scan = new Scanner(System.in);
                System.out.println("Enter command: ");
                String request = scan.nextLine();
                if (request.toLowerCase().contains(stopMessage)) {
                    break;
                }
                out.println(request);
                String response = in.readLine();
                System.out.println(response);
            } catch (UnknownHostException e) {
                System.err.println("No server listening: " + e);
            }
        }
    }
}