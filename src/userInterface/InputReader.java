package userInterface;
import java.util.Scanner;

public class InputReader{

	static Scanner reader = new Scanner(System.in);

	public static int getInputInt() {
		int num;

		try {
			num = Integer.parseInt(reader.nextLine());
		} catch (NumberFormatException e) {
			num = -1;
		}
		
		return num;
	}

	public static String getInputString() {
		String str;

		try {
			str = reader.nextLine();
		} catch (Exception e) {
			str = null;
		}

		return str;
	}

	public static void close(){
		if (reader != null) {
			reader.close();
		}
	}
	
	public static void nextLine() {
		if (reader != null) {
			reader.nextLine();
		}
	}
}
