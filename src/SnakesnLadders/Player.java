package SnakesnLadders;

import java.io.*;
import java.net.*;

/**
 * Implements methods for initializing players and utility methods like connecting to the server, 
 * sending and receiving message to and from the server, getter and setters.
 *
 * @author Animesh Mishra & Ali Fazel
 */
 
public class Player {
    
    private int position ;
    private String name ;
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    private static String host;
    private static int port;
    private boolean connected ;
    
    public Player(int i, String host, int port) {
        this.name = "Player_" + String.valueOf(i) ;
        this.position = 1 ;
        this.host = host;
        this.port = port;
    }

   /**
    * Sets player position based on the snakes & ladders squares
    * If the player lands in one of the ladder or snake squares,
    * the switch function makes sure that the final position is 
    * altered accordingly.
    *
    *@param pos the square where the player is to be put 
    */
    public void setPosition (int pos) {
		switch (pos) {
			case 6 	:	position = 16 ;
					break ;
			case 11	:	position = 49 ;
					break ;
			case 14 :	position = 4 ;
					break ;
			case 21	:	position = 60 ;
					break ;
			case 24	:	position = 87 ;
					break ;
			case 31	: 	position = 9 ;
					break ;
			case 35	:	position = 54 ;
					break ;
			case 44	:	position = 26 ;
					break ;
			case 51	:	position = 67 ;
					break ;
			case 56	:	position = 53 ;
					break ;
			case 62	:	position = 19 ;
					break ;
			case 64	:	position = 42 ;
					break ;
			case 73	:	position = 92 ;
					break ;
			case 78	:	position = 100 ;
					break ;
			case 84	:	position = 28 ;
					break ;
			case 91	:	position = 71 ;
					break ;
			case 95 :	position = 75 ;
					break ;
			case 99	:	position = 8 ;
					break ;
			default :	position = pos ;
					break ;
		}
	}
    
    public int getPosition() {
		return position ;
	}
    
    public int throwDie() {
		return (int) ((Math.random() * 5)+1) ;
	}
    
    public void setName(String pname) {
            name = pname ;
        }
    
    public String getName() {
            return name ;
        }
    
    public void connectServer() throws UnknownHostException, IOException {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String receiveMessage() {
        try {
            return in.readLine();
        } catch (IOException ex) {
            return null;
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void disconnect() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("Failed to disconnect");
            System.exit(1);
        }
    }
    
   /**
    * Initializes the player instance, connects the new player to the server and 
    * returns the said Player instance
    *
    *@param PlayerCount The player number as in the 2 in Player_2
    *@return thePlayers[] Returns the player instance initialized
    */
    public static Player InitializePlayer(int PlayerCount, String address) throws UnknownHostException, IOException {                                                                  
        if (PlayerCount > 1) {
            Interface.thePlayers[PlayerCount-1] = new Player(PlayerCount, address, port);
            Interface.thePlayers[PlayerCount-1].connectServer() ;
            return Interface.thePlayers[PlayerCount-1];
        }
        else {
            Interface.thePlayers[PlayerCount-1] = new Player(PlayerCount, "127.0.0.1", port);
            Interface.thePlayers[PlayerCount-1].connectServer() ;
            return Interface.thePlayers[PlayerCount-1];
        }
    }
}