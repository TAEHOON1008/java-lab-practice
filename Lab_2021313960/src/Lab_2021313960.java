import java.util.Scanner;
import java.util.ArrayList;

public class Lab_2021313960 {
	static Scanner scn= new Scanner(System.in);
	public static void main(String[] args) {
		int num = scn.nextInt();
		int temp = num;
		ArrayList<Integer> binary= new ArrayList<>();
		ArrayList<Integer> octal = new ArrayList<>();
		ArrayList<String>  hexa = new ArrayList<>();
		ArrayList<Integer> newarr_1 = new ArrayList<>();
		ArrayList<Integer> newarr_2 = new ArrayList<>();
		ArrayList<String> newarr_3 = new ArrayList<>();
		
		for(int i=0; temp != 0; i++) {
			binary.add(temp%2);
			temp = temp/2;
		}
		
		for(int i=0; i<binary.size(); i++) {
			int x = binary.get(binary.size()-1-i);
			newarr_1.add(x);
		}
		System.out.print("b ");
		for(int i:newarr_1) {
			System.out.print(i);
		}
		System.out.print("\n");
		
		temp = num;
		for(int i=0; temp != 0;i++) {
			octal.add(temp%8);
			temp = temp/8;
		}
		for(int i=0; i<octal.size(); i++) {
			int x = octal.get(octal.size()-1-i);
			newarr_2.add(x);
		}
		System.out.print("o ");
		for(int i:newarr_2) {
			System.out.print(i);
		}
		System.out.print("\n");
		
		
		temp = num;
		for(int i=0; temp != 0; i++) {
			switch((temp%16)) {
			case 10 : hexa.add("a"); break;
			case 11 : hexa.add("b"); break;
			case 12 : hexa.add("c"); break;
			case 13 : hexa.add("d"); break;
			case 14 : hexa.add("e"); break;
			case 15 : hexa.add("f"); break;
			default :  hexa.add(temp%16+"")  ;
			}
			temp = temp/16;
		}
		for(int i=0; i<hexa.size(); i++) {
			String x = hexa.get(hexa.size()-1-i);
			newarr_3.add(x);
		}
		System.out.print("h ");
		for(String i:newarr_3) {
			System.out.print(i);
		}

	}

}
