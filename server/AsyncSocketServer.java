package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncSocketServer {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        System.out.println("サーバーを起動中... ポート: " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("サーバーエラー: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (clientSocket;
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String clientMessage;
            while ((clientMessage = reader.readLine()) != null) {
                System.out.println("クライアントから受信: " + clientMessage);
                writer.println("サーバーからの応答: " + clientMessage.toUpperCase());
            }
        } catch (IOException e) {
            System.err.println("クライアント処理中にエラー発生: " + e.getMessage());
        }
    }
}
