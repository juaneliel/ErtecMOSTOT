package util;

public class pruebasmain {

	public static void main(String[] args) {
		String path = "/home/juan/wildfly-10.1.0.Final/standalone/deployments/ertec.war";
		int index=-1;
		for (int i=0;i<3;i++){
			index = path.indexOf("/",index+1); 
		}   
     
    String inicio=path.substring(0, index);
    System.out.println(index + inicio);
	}

}
