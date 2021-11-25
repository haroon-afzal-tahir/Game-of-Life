package com.Interfaces.GetFromBL.DB;
import java.io.File;
import java.util.Objects;
		
class DeleteStateConcrete implements DeleteState{
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
	public char[][] delete(String statename, char[][] list, int size_list) {
		// TODO Auto-generated method stub
		String filename = statename + ".txt";
		File myFile = new File(filename);
		if (myFile.delete()){
            System.out.println(myFile.getName() + "has been deleted.\n" );
        }
        else {
            System.out.println("\nSome problem occurred while deleting the file\n");
        }
		
		@SuppressWarnings("unused")
		int index = -1;
		char[] ch = statename.toCharArray();
		
		//searching for the index of name of the file which is to be deleted
		for (int i = 0;i < size_list;i++) {
				if(compare(list[i], ch) == 1) {
					index = i;
				}
		}
		
		//creating a new list without the name of the deleted file
		char [][]list2 = new char[size_list - 1][];
		int w = 0, e = 0, t = 0;
		for(w = 0; w < size_list - 1; w++){
			if(w != index){
				for(e = 0; e < list[w].length;e++){
					if(list2[w][e] != '\n')
						list2[t][e] = list[w][e];	
				}
				t++;
			}
		}	
		return list2;
	}
}
