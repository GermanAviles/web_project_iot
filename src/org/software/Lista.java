package org.software;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.DB;
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
	    MongoClient mongoClientLocal = new MongoClient("localhost",27017);
	    //MongoDatabase databaseLocal = mongoClientLocal.getDatabase("Medi_flores");
	    //obtiene la coleccion local y si no la tiene la crea
	    
	    DB db = mongoClientLocal.getDB("Medi_flores");
	    DBCollection coleccion = db.getCollection("Datos");
	    
	    for(String n : db.getCollectionNames()){
	    	System.out.println(n);
	    }
	    
	    DBCursor cursor = coleccion.find();
	    List<Datos> datos = new ArrayList<>();
	    
	    while(cursor.hasNext()){
	    	Datos dato = new Datos();
	    	DBObject dbob = cursor.next();
	    	
	    	dato.setSl((double)dbob.get("Sepal_length"));
	    	dato.setSw((double)dbob.get("Sepal_Width"));
	    	dato.setPl((double)dbob.get("Petal_length"));
	    	dato.setPw((double)dbob.get("Petal_Width"));
	    	dato.setCategoria((String)dbob.get("Categoria"));
	    	
	    	datos.add(dato);
	    }
	    return datos;
	}
}
