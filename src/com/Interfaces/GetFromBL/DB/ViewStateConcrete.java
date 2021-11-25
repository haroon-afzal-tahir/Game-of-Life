package com.Interfaces.GetFromBL.DB;
import java.util.Scanner;

public class ViewStateConcrete implements ViewState{
	private static int compare(char arr[], char arr2[]){
		int a = 0, i = 0;
		for (i = 0;i < arr.length;i++){
			if(arr[i] == arr2[i]){
				a++;
			}
		}
		
		if(a == arr.length){
			return 1;
		}
		else {
			return 0;
		}

	}
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
					if(compare(list[i], ch) == 1) {
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
