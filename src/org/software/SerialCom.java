package org.software;

import java.io.BufferedReader; 
import java.io.InputStreamReader; 
import java.io.OutputStream; 
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort; 
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Enumeration;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bson.Document;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class SerialCom implements SerialPortEventListener { 
	SerialPort serialPort; 
	/** The port we're normally going to use. */ 
	/** 
	 * A BufferedReader which will be fed by a InputStreamReader converting the 
	 * bytes into characters making the displayed results codepage independent 
    */
	
	private BufferedReader input; 
	/** The output stream to the port */ 	
	private static OutputStream output; 
	/** Milliseconds to block while waiting for port open */ 
	private static final int TIME_OUT = 2000; 
	/** Default bits per second for COM port. */ 
	private static final int DATA_RATE = 9600; 
	
	public void initialize(String puerto) { String PORT_NAMES[] = { puerto }; 
	// the next line is for Raspberry Pi and 
	// gets us into the while loop and was suggested here was suggested 
	// http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186 
	
	System.setProperty("gnu.io.rxtx.SerialPorts", puerto); 
	
	CommPortIdentifier portId = null; 
	Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
	
	// First, Find an instance of serial port as set in PORT_NAMES. 
	while (portEnum.hasMoreElements()) {
		CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement(); 
		for (String portName : PORT_NAMES) { 
			if (currPortId.getName().equals(portName)) {
			portId = currPortId; 
			break; 
			}
		}
	} 
	if (portId == null) { 
		System.out.println("Could not find COM port."); 
		return; 
		}
	
	try { 
		// open serial port, and use class name for the appName. 
		serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT); 
		
		// set port parameters 
		serialPort.setSerialPortParams(DATA_RATE, 
				SerialPort.DATABITS_8, 
				SerialPort.STOPBITS_1, 
				SerialPort.PARITY_NONE); 
		
		// open the streams 
		input = new BufferedReader( 
				new InputStreamReader(serialPort.getInputStream())); 
		
		output = serialPort.getOutputStream(); 
		
		// add event listeners 
		serialPort.addEventListener(this); 
		serialPort.notifyOnDataAvailable(true); 
		
		} 
		catch (Exception e) {
			System.err.println(e.toString()); 
		} 
	   }
		/** * This should be called when you stop using the port. This will prevent * port locking on platforms like Linux. */ 
		public synchronized void close() { 
			if (serialPort != null) { 
				serialPort.removeEventListener(); 
				serialPort.close(); 
				} 
			}
		/** * Handle an event on the serial port. Read the data and print it. */
		public synchronized void serialEvent(SerialPortEvent oEvent) { 
			if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
				try { 
					String inputLine = input.readLine(); 
					if (inputLine.length() > 0) { 
						System.out.println(inputLine);
						
						JsonParser parser = new JsonParser();
		                JsonObject json = parser.parse(inputLine.toString()).getAsJsonObject();
		                
		                
		                double sl = json.get("sl").getAsDouble();
						double sw = json.get("sw").getAsDouble();
						double pl = json.get("pl").getAsDouble();
						double pw = json.get("pw").getAsDouble();
		                
		                String categoria = cat(sl,sw,pl,pw);
		                
		                //crear coneccion local
		                MongoClient mongoClientLocal = new MongoClient( "localhost" , 27017 );
		                MongoDatabase databaseLocal = mongoClientLocal.getDatabase("Medi_flores");
		                //obtiene la coleccion local y si no la tiene la crea
		                MongoCollection<Document> collectionLocal = databaseLocal.getCollection("Datos");
		                
		                //crear coneccion a la nube
		               /* MongoClientURI stringConnect = new MongoClientURI("mongodb://german:german123456@ds159200.mlab.com:59200/con_ard_with_java");
		        		MongoClient mongoClientMlab = new MongoClient(stringConnect);
		        		MongoDatabase databaseMlab = mongoClientMlab.getDatabase("Medi_flores");
		                //obtiene la coleccion para Mlab y si no la tiene la crea
		                MongoCollection<Document> collectionMlab = databaseMlab.getCollection("Datos");*/
		                
		              //crear docuemnto
		                Document doc = new Document("Categoria",categoria)
		                		.append("Sepal_length", sl)
		                        .append("Sepal_Width", sw)
		                        .append("Petal_length", pl)
		                        .append("Petal_Width", pw);
		                
		                //insertar documento a base datos mongodb local
		                collectionLocal.insertOne(doc);
		                	
		                //insertar documento a base de datos en la nube
		               // collectionMlab.insertOne(doc);
				} 
			} catch (Exception e) { 
				// System.err.println("Error (serialEvent): " + e.toString()); 
			} 
		} 
		   // Ignore all the other eventTypes, but you should consider the other 
		  // ones. 
	} 
	public OutputStream getOutput() { 
		return output; 
	}
	private static String cat(double sl, double sw, double pl, double pw) {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response1 = null;
		String cat = "";
		String responseBody = "";

		try {
			String uri = "http://127.0.0.1:5000/" + sl + "/" + sw + "/" + pl + "/" + pw;

			HttpGet httpGet = new HttpGet(uri);
			response1 = httpclient.execute(httpGet);

			// System.out.println(response1.getStatusLine());
			HttpEntity entity1 = response1.getEntity();

			// System.out.println("Response Code : " +
			// response1.getStatusLine().getStatusCode());

			responseBody = EntityUtils.toString(entity1);
			// System.out.println(responseBody.toString());

			// Gson gson = new Gson();

			/*JsonParser parser = new JsonParser();

			JsonObject json = parser.parse(responseBody.toString()).getAsJsonObject();

			cat = json.get("categoria").getAsString();*/

			EntityUtils.consume(entity1);

		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		} finally {

			try {
				response1.close();
				httpclient.close();
			} catch (Exception ei) {
				System.out.println("Error: " + ei.toString());
			}
		}

		return responseBody;
	}
	
  }