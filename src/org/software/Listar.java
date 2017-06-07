package org.software;

import java.util.ArrayList;
import java.util.List;

public class Listar {
	
	public List<Datos> prom(){
		
		Lista list = new Lista();		
		List<Datos> datos = list.list();	


		int iset = 0;
    	int ivirg = 0;
    	int iversi = 0;
    	
    	double isetsl = 0.0;
    	double isetsw = 0.0;
    	double isetpl = 0.0;
    	double isetpw = 0.0;
    	
    	double ivirgsl = 0.0;
    	double ivirgsw = 0.0;
    	double ivirgpl = 0.0;
    	double ivirgpw = 0.0;
    	
    	double iversisl = 0.0;
    	double iversisw = 0.0;
    	double iversipl = 0.0;
    	double iversipw = 0.0;
    	
    	for(Datos dt: datos){
    		if(dt.getCategoria().equals("['Iris-setosa']")){
    			iset ++;
    			isetsl += dt.getSl();
    			isetsw += dt.getSw();
    			isetpl += dt.getPl();
    			isetpw += dt.getPw();
    		}
    		if(dt.getCategoria().equals("['Iris-virginica']")){
    			ivirg ++;
    			ivirgsl += dt.getSl();
    			ivirgsw += dt.getSw();
    			ivirgpl += dt.getPl();
    			ivirgpw += dt.getPw();
    		}
    		if(dt.getCategoria().equals("['Iris-versicolor']")){
    			iversi ++;
    			iversisl += dt.getSl();
    			iversisw += dt.getSw();
    			iversipl += dt.getPl();
    			iversipw += dt.getPw();
    		}
    	}
    	
    	double pisetsl = 0.0;
    	double pisetsw = 0.0;
    	double pisetpl = 0.0;
    	double pisetpw = 0.0;
    	
    	double pivirgsl = 0.0;
    	double pivirgsw = 0.0;
    	double pivirgpl = 0.0;
    	double pivirgpw = 0.0;
    	
    	double piversisl = 0.0;
    	double piversisw = 0.0;
    	double piversipl = 0.0;
    	double piversipw = 0.0;
    	
    	if(iset !=0){
    		pisetsl = isetsl /iset;
    		pisetsw = isetsw /iset;
    		pisetpl = isetpl /iset;
    		pisetpw = isetpw /iset;
    	}
    	if(ivirg !=0){
    		pivirgsl = ivirgsl /ivirg;
    		pivirgsw = ivirgsw /ivirg;
    		pivirgpl = ivirgpl /ivirg;
    		pivirgpw = ivirgpw /ivirg;
    	}
    	if(iversi !=0){
    		piversisl = iversisl /iversi;
    		piversisw = iversisw /iversi;
    		piversipl = iversipl /iversi;
    		piversipw = iversipw /iversi;
    	}
    	
    	List<Datos>listar = new ArrayList<>();
    	
    	Datos dato1 = new Datos();
    	Datos dato2 = new Datos();
    	Datos dato3 = new Datos();
    	
    	dato1.setCategoria("Iris-setosa");
    	dato1.setSl(pisetsl);
    	dato1.setSw(pisetsw);
    	dato1.setPl(pisetpl);
    	dato1.setPw(pisetpw);
    	
    	dato2.setCategoria("Iris-virginica");
    	dato2.setSl(pivirgsl);
    	dato2.setSw(pivirgsw);
    	dato2.setPl(pivirgpl);
    	dato2.setPw(pivirgpw);
    	
    	dato3.setCategoria("Iris-versicolor");
    	dato3.setSl(piversisl);
    	dato3.setSw(piversisw);
    	dato3.setPl(piversipl);
    	dato3.setPw(piversipw);
    	
    	listar.add(dato1);
    	listar.add(dato2);
    	listar.add(dato3);
    	
    	return listar;
	}


}
