package server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AsyncSocketServerTest {
    private ExecutorService executor;
    private Thread serverThread;

    @BeforeEach
    void setUp() {
        executor = Executors.newSingleThreadExecutor();
        serverThread = new Thread(() -> AsyncSocketServer.main(new String[] {}));
        serverThread.start();

        try {
            Thread.sleep(500); // サーバーが起動するのを待つ
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @AfterEach
    void tearDown() {
        executor.shutdown();
        serverThread.interrupt();
    }

    @Test
    void testClientConnectionAndResponse() throws IOException {
        try (Socket socket = new Socket("localhost", 12345);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            writer.println("hello");
            String response = reader.readLine();

            assertNotNull(response);
            assertEquals("サーバーからの応答: HELLO", response);
        }
    }
}
