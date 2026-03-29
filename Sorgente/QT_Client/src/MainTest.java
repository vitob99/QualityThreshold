import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;


import keyboardinput.Keyboard;


/**
 * Classe client che si occupa di instaurare una connessione con il server di clustering.
 * 
 * Il client invia richieste al server per:
 * - Caricare cluster da file.
 * - Caricare dati da database.
 * - Eseguire clustering a raggio.
 * - Salvare i cluster su file.
 * 
 * 
 * L'input da tastiera viene gestito tramite la classe Keyboard.
 */
public class MainTest {
	/** Flusso di output di richieste verso il server */
	private ObjectOutputStream out;
	/** Flusso di input risposta dal server */
	private ObjectInputStream in ; 
	
	
	/**
	 * Costruttore parametrizzato della classe MainTest.
	 * 
	 * Costruisce il client e apre la connessione con il server.
	 * 
     * @param ip   indirizzo IP del server.
     * @param port porta del server.
     * @throws IOException se si verifica un errore di connessione.
	 */
	public MainTest(String ip, int port) throws IOException{
		InetAddress addr = InetAddress.getByName(ip); 
		Socket socket = new Socket(addr, port); 
		System.out.println(socket);
		
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());	; 
	}
	
    /**
     * Mostra il menu principale e legge la scelta dell’utente.
     * In base alla scelta verra' mandata la richiesta corrispondente al server.
     *
     * @return l'opzione scelta dall’utente (1 o 2)
     */
	private int menu(){
		int answer;
			System.out.println("(1) Load clusters from file");
			System.out.println("(2) Load data from db");
			System.out.print("(1/2):");
			answer=Keyboard.readInt();
		return answer;
		
	}
	
    /**
     * Esegue una richiesta al server per caricare i cluster da un file.
     *
     * @return stringa contenente i cluster caricati.
     * @throws SocketException       se si verifica un errore di rete.
     * @throws ServerException       se il server restituisce un errore.
     * @throws IOException           se avviene un errore di I/O.
     * @throws ClassNotFoundException se la risposta del server non e' riconosciuta.
     */
	private String learningFromFile() throws SocketException,ServerException,IOException,ClassNotFoundException{
		out.writeObject(3);
		
		System.out.print("File Name:");
		String fileName=Keyboard.readString();
		out.writeObject(fileName);
		String file_clusters = (String)in.readObject();
		
		String result = (String)in.readObject();
		if(result.equals("OK"))
			return file_clusters;
		else throw new ServerException(result);
		
	}
	

    /**
     * Richiede al server di caricare una tabella dal database.
     *
     * @throws SocketException       se si verifica un errore di rete.
     * @throws ServerException       se il server restituisce un errore.
     * @throws IOException           se avviene un errore di I/O.
     * @throws ClassNotFoundException se la risposta del server non e' riconosciuta.
     */
	private void storeTableFromDb() throws SocketException,ServerException,IOException,ClassNotFoundException{
		out.writeObject(0);
		System.out.print("Table name:");
		String tabName=Keyboard.readString();
		out.writeObject(tabName);
		String result = (String)in.readObject();
		if(!result.equals("OK"))
			throw new ServerException(result);
		
	}
	
    /**
     * Richiede al server di eseguire il clustering su una tabella del database con uno specifico raggio.
     *
     * @return stringa contenente i cluster trovati
     * @throws SocketException       se si verifica un errore di rete.
     * @throws ServerException       se il server restituisce un errore.
     * @throws IOException           se avviene un errore di I/O.
     * @throws ClassNotFoundException se la risposta del server non e' riconosciuta.
     */
	private String learningFromDbTable() throws SocketException,ServerException,IOException,ClassNotFoundException{
		out.writeObject(1);
		double r=1.0;
		do{
			System.out.print("Radius:");
			r=Keyboard.readDouble();
		} while(r<=0 || Double.isNaN(r));
		out.writeObject(r);
		String result = (String)in.readObject();
		if(result.equals("OK")){
			System.out.println("Number of Clusters:"+in.readObject());
			return (String)in.readObject();
		}
		else throw new ServerException(result);
		
		
	}
	
    /**
     * Chiede al server di salvare i cluster correnti su un file.
     *
     * @throws SocketException       se si verifica un errore di rete.
     * @throws ServerException       se il server restituisce un errore.
     * @throws IOException           se avviene un errore di I/O.
     * @throws ClassNotFoundException se la risposta del server non e' riconosciuta.
     */
	private void storeClusterInFile() throws SocketException,ServerException,IOException,ClassNotFoundException{
		out.writeObject(2);
		
		System.out.print("Backup file name: ");
		out.writeObject(Keyboard.readString());
		
		
		System.out.println((String)in.readObject());
		String result = (String)in.readObject();
		if(!result.equals("OK"))
			 throw new ServerException(result);
		
	}
	
    /**
     * Metodo principale che avvia il client e gestisce il flusso di richiesta/risposta.
     *
     * @param args argomenti da linea di comando: args[0] = ip del server e args[1] = porta del server.
     */
	public static void main(String[] args) {
		String ip=args[0];
		int port=new Integer(args[1]).intValue();
		MainTest main=null;
		try{
			main=new MainTest(ip,port);
		}
		catch (ConnectException ex) {
			System.err.println("Errore: impossibile connettersi al server!");
			return;
		}
		catch (IOException e){
			System.out.println(e);
			return;
		}
		
		
		
		do{
			int menuAnswer=main.menu();
			switch(menuAnswer)
			{
				case 1:
					try {
						String kmeans=main.learningFromFile();
						System.out.println(kmeans);
					}
					catch (SocketException e) {
						System.out.println(e);
						return;
					}
					catch (FileNotFoundException e) {
						System.out.println(e);
						return ;
					} catch (IOException e) {
						System.out.println(e);
						return;
					} catch (ClassNotFoundException e) {
						System.out.println(e);
						return;
					}
					catch (ServerException e) {
						System.out.println(e.getMessage());
					}
					break;
				case 2: 
				
					while(true){
						try{
							main.storeTableFromDb();
							break; 
						}
						
						catch (SocketException e) {
							System.out.println(e);
							return;
						}
						catch (FileNotFoundException e) {
							System.out.println(e);
							return;
							
						} catch (IOException e) {
							System.out.println(e);
							return;
						} catch (ClassNotFoundException e) {
							System.out.println(e);
							return;
						}
						catch (ServerException e) {
							System.out.println(e.getMessage());
						}
					} 
						
					char answer='y';
					do{
						try
						{
							String clusterSet=main.learningFromDbTable();
							System.out.println(clusterSet);
							
							main.storeClusterInFile();
									
						}
						catch (SocketException e) {
							System.out.println(e);
							return;
						}
						catch (FileNotFoundException e) {
							System.out.println(e);
							return;
						} 
						catch (ClassNotFoundException e) {
							System.out.println(e);
							return;
						}catch (IOException e) {
							System.out.println(e);
							return;
						}
						catch (ServerException e) {
							System.out.println(e.getMessage());
							return;
						}
						System.out.print("Would you repeat?(y/n)");
						answer=Keyboard.readChar();
					}
					while(Character.toLowerCase(answer)=='y');
					break; 
					default:
					System.out.println("Invalid option!");
			}
			
			System.out.print("would you choose a new operation from menu?(y/n)");
			if(Keyboard.readChar()!='y')
				break;
			}
		while(true);
		}
	}



