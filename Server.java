// A Java program for a Server 
import java.net.*; 
import java.io.*; 
  
public class Server 
{ 
    //initialize socket and input stream 
    private Socket          	socket = null; 
    private ServerSocket    	server = null; 
    private DataInputStream 	socket_in = null; 
    private DataOutputStream 	socket_out = null;
    private File 		shared_folder = new File("./SharedFolder");
    private byte[]		buffer = new byte[512];
    
    // constructor with port 
    public Server(int port) 
    { 
        // starts server and waits for a connection 
        try
        { 
            server = new ServerSocket(port); 
            System.out.println("Server started"); 
  
            System.out.println("Waiting for a client ..."); 
  
            socket = server.accept(); 
            System.out.println("Client accepted"); 
  
            // takes input from the client socket 
            socket_in = new DataInputStream(socket.getInputStream());
	    socket_out = new DataOutputStream(socket.getOutputStream());
  	    
            String cmd = ""; 
  
            // reads message from client until "Over" is sent 
            while (!cmd.equals("@logout")) 
            { 
                try
                { 
                    int len = socket_in.read(buffer);
		    if (len != -1)
		    {
			cmd = new String(buffer, 0, len);
		    	System.out.println(cmd);
		    }
		    if ( cmd.equals("@list") )
		    {
			System.out.println("Client request list of files.");
			String fl = new String();
			File[] files_list = shared_folder.listFiles();
		    	for (int i=0; i < files_list.length; i++)
			{
				fl += files_list[i].getName();
				if( i % 4 == 3 || i == files_list.length-1 )
					fl += "\n";
				else fl += "\t";
			}
			System.out.println(fl);
			//socket_out.writeUTF(fl);
			buffer = fl.getBytes();
			System.out.println(buffer.length);
			socket_out.write(buffer, 0 , buffer.length);
		    }
  
                } 
                catch(IOException i) 
                { 
                    System.out.println(i); 
                } 
            } 
            System.out.println("Closing connection"); 
  
            // close connection 
            socket_in.close();
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
        Server server = new Server(5000); 
    } 
} 

