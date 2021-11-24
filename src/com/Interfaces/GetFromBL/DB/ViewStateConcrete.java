package com.Interfaces.GetFromBL.DB;
import java.util.Scanner;

public class ViewStateConcrete implements ViewState{

	@Override
	public String view(char[][] list,  int size_list) {
		// TODO Auto-generated method stub
		String Statename = null;
		
		System.out.println("The list of saved states is given as follows:\n");
		for (int i = 0;i < size_list;i++) {
			for(int j = 0;j < list[i].length;i++) {
				System.out.println(list[i][j]);
			}
		}
		
		//getting the name of the desired state
		@SuppressWarnings("resource")
		Scanner myObj = new Scanner(System.in); 
		System.out.println("\nPlease enter the name of the saved state you want to load: ");
		Statename =  myObj.nextLine();
		
		int index = 0;
		
		for (int i = 0;i < size_list;i++) {
				if(Object.equals(list[i],Statename) == 0) {
					index = i;
				}
		}
		
		return Statename;
	}
}
