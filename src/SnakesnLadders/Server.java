package SnakesnLadders;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements all the server related methods viz. starting and stopping a server,
 * displaying server status and creating new I/O streams for new clients.
 *
 * @author Animesh Mishra & Ali Fazel
 */
public class Server {
    
    private int clientCount = 0 ;
    private static int PORT ;
    private ServerSocket serverSocket;
    private boolean started;
    private Thread serverThread;

    public Server(int port) throws IOException {
        PORT = port ;
        serverSocket = new ServerSocket(port);
    }

    // This server starts on a seperate thread so you can still do other things on the server program
    public void startServer() {
        if (!started) {
            started = true;
            serverThread = new Thread() {
                public void run() {
                    while (Server.this.started) {
                        Socket clientSocket = null;
                        try {
                            clientSocket = serverSocket.accept();
                            openClient(clientSocket);
                        } catch (SocketException e) {
                            System.err.println("Server closed.");
                        } catch (IOException e) {
                            System.err.println("Accept failed.");
                        }
                    }
                }
            };
            serverThread.start();
            this.status();
        }
    }

    public void stopServer() {
        this.started = false;
        serverThread.interrupt();
        try {
            serverSocket.close();
        } catch (IOException ex) {
            System.err.println("Server stop failed.");
            System.exit(1);
          }
        this.status() ;
    }
    
    public void status() {
        if (this.started == true) System.out.println("Server running");
        else System.out.println("Server stopped");
    }
    
    public void openClient(final Socket socket) {
        clientCount++;
        new Thread() {
            public void run() {
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    String inputLine, outputLine, challenge;

                    challenge = "Hello World";
                    // This server sends out a challenge to a connecting client
                    out.println(challenge);
                    System.out.println("\nSent challenge: " + challenge);

                    while ((inputLine = in.readLine()) != null) {
                        System.out.println("Received response: " + inputLine);
                    }
                } catch (IOException ex) {
                    System.err.println("Client communication failure.");
                }
            }
        }.start();
    }
}

