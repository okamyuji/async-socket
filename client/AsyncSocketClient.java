package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncSocketClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                Scanner scanner = new Scanner(System.in)) {

            ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

            // サーバーからの応答を非同期で受信
            executor.submit(() -> {
                try {
                    String response;
                    while ((response = reader.readLine()) != null) {
                        System.out.println("サーバーからの応答: " + response);
                    }
                } catch (IOException e) {
                    System.err.println("サーバー応答受信エラー: " + e.getMessage());
                }
            });

            // ユーザーからの入力をサーバーに送信
            while (true) {
                System.out.print("送信メッセージ: ");
                String message = scanner.nextLine();
                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }
                writer.println(message);
            }
        } catch (IOException e) {
            System.err.println("クライアントエラー: " + e.getMessage());
        }
    }
}
