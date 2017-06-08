package org.software;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import gnu.io.SerialPortEvent;

public class Lista{
	
	public List<Datos> list(){
		
		//crear coneccion local
	    MongoClient mongoClientLocal = new MongoClient();
	    MongoDatabase databaseLocal = mongoClientLocal.getDatabase("Medi_flores");
	    //obtiene la coleccion local y si no la tiene la crea
	    DBCollection db = (DBCollection) databaseLocal.getCollection("Datos");
	    
	    DBCursor cursor = db.find();
	    List<Datos> datos = new ArrayList<>();
	    
	    while(cursor.hasNext()){
	    	Datos dato = new Datos();
	    	DBObject dbob = cursor.next();
	    	
	    	dato.setSl((double)dbob.get("sl"));
	    	dato.setSw((double)dbob.get("sw"));
	    	dato.setPl((double)dbob.get("pl"));
	    	dato.setPw((double)dbob.get("pw"));
	    	dato.setCategoria((String)dbob.get("categoria"));
	    	
	    	datos.add(dato);
	    }
	    return datos;
	}
}
