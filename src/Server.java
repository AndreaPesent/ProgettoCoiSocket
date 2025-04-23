import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{

    private ServerSocket serverSocket;
    ObjectMapper mapper = new ObjectMapper();
    Prodotto prodotto = new Prodotto("Elden Ring", "Videogioco", 12, 50.00);
    int quantita = prodotto.getQuantita();

    public static void main(String[] args)
    {
        new Server().startServer();
    }

    public void startServer()
    {
        try
        {
            serverSocket = new ServerSocket(5000);
            System.out.println("Server in ascolto su porta locale 5000");
            System.out.println(prodotto.toString());
            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                synchronized (this)
                {
                    if (quantita <= 0)
                    {
                        System.out.println("Prodotto esaurito. Non si accettano nuove connessioni.");
                        clientSocket.close();
                        break;
                    }
                }
                new Thread(new GestioneClient(clientSocket)).start();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    class GestioneClient implements Runnable
    {

        private final Socket clientSocket;

        GestioneClient(Socket socket) {
            this.clientSocket = socket;
        }

        public void run()
        {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true))
            {
                System.out.println("Connessione ricevuta da: " + clientSocket.getInetAddress().getHostAddress());
                Command serverCommand = new Command();
                serverCommand.setCommandName("connessione confermata");
                out.println(mapper.writeValueAsString(serverCommand));
                String msg = in.readLine();
                Command clientCommand = mapper.readValue(msg, Command.class);
                System.out.println("Client: " + clientCommand.getCommandName());
                if ("Compro il prodotto".equals(clientCommand.getCommandName()))
                {
                    synchronized (Server.this)
                    {
                        if (quantita>0)
                        {
                            quantita--;
                            System.out.println("Prodotto acquistato. Quantità rimanente: " + quantita);
                        } else
                        {
                            System.out.println("Prodotto esaurito durante la richiesta.");
                        }
                    }
                } else
                {
                    System.out.println("Il client ha deciso di non acquistare.");
                }
                Command fineCommand = new Command();
                fineCommand.setCommandName("Fine");
                out.println(mapper.writeValueAsString(fineCommand));
                clientSocket.close();
            } catch (IOException e)
            {
                System.err.println("Errore con il client: " + e.getMessage());
            }
        }
    }
}