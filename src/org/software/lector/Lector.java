package org.software.lector;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class Lector
 */
@WebServlet("/Lector")
public class Lector extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Lector() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
		
		/*
		String pl = request.getParameter("petal-length");
		String pw = request.getParameter("petal-width");
		String sl = request.getParameter("sepal-length");
		String sw = request.getParameter("sepal-width");
		*/
		
		PrintWriter out = response.getWriter();
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
        try {
        	String uri = "http://127.0.0.1:5000/json2"; // + sl + "/" + sw + "/" + pl + "/" + pw;
        	        	
        	System.out.println(uri);
        	
            HttpGet httpGet = new HttpGet(uri);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            
            try {
                System.out.println(response1.getStatusLine());
                HttpEntity entity1 = response1.getEntity();
                
                System.out.println("Response Code : "
                        + response1.getStatusLine().getStatusCode());
                
                String responseBody = EntityUtils.toString(entity1);
                System.out.println(responseBody.toString());
                
                //Gson gson = new Gson();
                
                JsonParser parser = new JsonParser();
                
                JsonObject json = parser.parse(responseBody.toString()).getAsJsonObject();
                
                /*
                String clase = json.get("clase").getAsString();
                
                out.println("<p>Clase: " + clase + "</p>");
                */
                
                
                
                JsonArray mediciones = json.get("mediciones").getAsJsonArray();
                
                System.out.println(mediciones.size());
            
                out.println("<table border='1'>");
                for(int i=0; i<mediciones.size(); i=i+1){
                	JsonObject obj = mediciones.get(i).getAsJsonObject();
                	
                	
                	out.println("<tr>");
                	out.print("<td>" + i + "</td>");
                	out.print("<td>" + obj.get("class").getAsString() + "</td>");
                	out.print("<td>" + obj.get("petal-length").getAsString() + "</td>");
                	out.print("<td>" + obj.get("petal-width").getAsString() + "</td>");
                	out.print("<td>" + obj.get("sepal-length").getAsString() + "</td>");
                	out.println("<td>" + obj.get("sepal-width").getAsString() + "</td>");
                	out.println("</tr>");
                	
                	
                }
                out.println("</table>");
                EntityUtils.consume(entity1);

            } 
            finally {
                response1.close();
            }

        } 
        catch (Exception e){
        	System.out.println("Error: " + e.toString());
        }    
        finally {
        	try{
            httpclient.close();
        	}
        	catch(Exception ei){
        		System.out.println("Error: " + ei.toString());
        	}
        }
        
        
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

