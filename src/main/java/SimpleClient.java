import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int PORT = 8100;
        String stopMessage = "exit";
        String serverStopMessage = "stop";
        while (true) {
            try (Socket socket = new Socket(serverAddress, PORT);
                 PrintWriter out =
                         new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(socket.getInputStream()))) {
                Scanner scan = new Scanner(System.in);
                boolean connected = true;
                while(connected) {
                    System.out.println("Enter command: ");
                    String request = scan.nextLine();
                    List<String> splitRequest = Arrays.asList(request.split(" ").clone());
                    if(!splitRequest.get(0).contains("login")) {
                        connected = false;
                    }
                    if (request.toLowerCase().contains(stopMessage)) {
                        break;
                    }
                    out.println(request);
                    if (request.toLowerCase().contains(serverStopMessage)) {
                        break;
                    }
                    String response = in.readLine();
                    System.out.println(response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("No server listening: " + e);
                break;
            }
        }
    }
}