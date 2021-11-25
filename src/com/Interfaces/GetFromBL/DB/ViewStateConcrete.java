package com.Interfaces.GetFromBL.DB;
import java.util.Objects;
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
		int index = -1;
		//getting the name of the desired state
		@SuppressWarnings("resource")
		Scanner myObj = new Scanner(System.in); 
		while(index == -1) {
			
			System.out.println("\nPlease enter the name of the saved state you want to load/delete: ");
			Statename =  myObj.nextLine();
			
			char[] ch = Statename.toCharArray();
			//searching for the index of name
			//to see if the name is valid or not
			for (int i = 0;i < size_list;i++) {
					if(Objects.equals(list[i], ch) == true) {
						index = i;
					}
			}
			if (index == -1) {
					System.out.println("The name you've entered is not valid. Please try again\n");
			}
		}
		
		return Statename;
	}
}
