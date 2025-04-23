import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

class Command
{
    private String commandName;
    public String getCommandName()
    {
        return commandName;
    }
    public void setCommandName(String commandName)
    {
        this.commandName = commandName;
    }
}

public class Client
{
    private Socket requestSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String message;
    ObjectMapper mapper = new ObjectMapper();
    static String scelta="";

    Client() {
    }

    void run()
    {
        try
        {
            requestSocket = new Socket("localhost", 5000);
            System.out.println("Connesso al server su porta locale 5000");
            out = new PrintWriter(requestSocket.getOutputStream());
            out.flush();
            in = new BufferedReader(new InputStreamReader(requestSocket.getInputStream()));
            Command objClient;
            Command objServer=null;
            try
            {
                message = in.readLine();
                objServer = mapper.readValue(message, Command.class);
                objClient=new Command();
                objClient.setCommandName(saldo(scelta));
                sendMessage(mapper.writeValueAsString(objClient));
            } catch (Exception classNot)
            {
                System.err.println("");
            }
        } catch (UnknownHostException HostSconosciuto)
        {
            System.err.println("Connessione a host sconosciuto");
        } catch (Exception ioException)
        {
            ioException.printStackTrace();
        } finally
        {
            try
            {
                in.close();
                out.close();
                requestSocket.close();
            } catch (Exception ioException)
            {
                ioException.printStackTrace();
            }
        }
    }

    void sendMessage(String msg)
    {
        try
        {
            out.println(msg);
            out.flush();
        } catch (Exception ioException)
        {
            ioException.printStackTrace();
        }
    }
    public String saldo(String scelta)
    {
        Random r = new Random();
        if (r.nextInt(2)==1)
        {
            scelta="Compro il prodotto";
        }
        else
        {
            scelta="Fine";
        }
        System.out.println(scelta);
        return scelta;
    }

    public static void main(String args[])
    {
        Client client = new Client();
        client.run();
    }
}