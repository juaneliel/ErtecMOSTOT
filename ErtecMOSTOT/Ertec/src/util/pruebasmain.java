package util;

public class pruebasmain {

	public static void main(String[] args) {
		String path = "hola \n dale que va \n porfa";
		String[] parts = path.split("\n");
		for(String s : parts){
			System.out.println(s);
		}
	}

}
