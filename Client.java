// A Java program for a Client 
import java.net.*; 
import java.io.*; 
  
public class Client 
{ 
    // initialize socket and input output streams 
    private Socket socket            = null; 
    private DataInputStream  input   = null; 
    private DataInputStream  socket_in = null;
    private DataOutputStream socket_out     = null;
    private byte[] 	     buffer = new byte[512];
    // constructor to put ip address and port 
    public Client(String address, int port) 
    { 
        // establish a connection 
        try
        { 
            socket = new Socket(address, port); 
            System.out.println("Connected"); 
  
            // takes input from terminal 
            input  = new DataInputStream(System.in); 
  	    // take input from socket
	    socket_in     = new DataInputStream(socket.getInputStream());
            // sends output to the socket 
            socket_out    = new DataOutputStream(socket.getOutputStream()); 
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println(u); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
  
        // string to read message from input 
        String line = ""; 
  
        // keep reading until "Over" is input 
        while (!line.equals("@logout")) 
        { 
            try
            { 
                line = input.readLine(); 
		buffer = line.getBytes();
                socket_out.write(buffer);
		if ( line.equals("@list") )
		{
			String fl = "";
			//fl = socket_in.readUTF();
			socket_in.read(buffer, 0, buffer.length);				
			fl += new String(buffer);
			System.out.println(fl);
		}
            } 
            catch(IOException i) 
            { 
                System.out.println(i); 
            } 
        } 
  
        // close the connection 
        try
        { 
            input.close(); 
            socket_out.close(); 
            socket.close(); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
    } 
  
    public static void main(String args[]) 
    { 
        Client client = new Client("127.0.0.1", 5000); 
    } 
} 

