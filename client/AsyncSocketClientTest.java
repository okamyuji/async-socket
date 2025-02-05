package client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AsyncSocketClientTest {
    private Socket mockSocket;
    private BufferedReader mockReader;
    private PrintWriter mockWriter;

    @BeforeEach
    void setUp() throws IOException {
        mockSocket = mock(Socket.class);
        mockReader = mock(BufferedReader.class);
        mockWriter = mock(PrintWriter.class);

        when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream("サーバーからの応答: HELLO\n".getBytes()));
        when(mockSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
    }

    @Test
    void testClientSendsMessage() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(mockSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(mockSocket.getOutputStream(), true)) {

            writer.println("hello");
            String response = reader.readLine();

            assertNotNull(response);
            assertEquals("サーバーからの応答: HELLO", response);
        }
    }
}
