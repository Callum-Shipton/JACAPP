package save;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class SaveHandler {
	
	public static void save(Save save, int num){
		 try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream("save" + num + ".ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(save);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in /tmp/employee.ser");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	public static Save load(int num){
		
		Save s = null;
		
		try
	      {
	         FileInputStream fileIn = new FileInputStream("save" + num + ".ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         s = (Save) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Employee class not found");
	         c.printStackTrace();
	      }
		
		return s;
	}
}
