package com.example.ConnectFour;

public class Utility {

	public static char[][] stringToDeep(String s2) {
		//String s2="[[., ., ., ., ., ., ., .], [., ., ., ., ., ., ., .], [., ., ., ., ., ., ., .], [., ., ., ., ., ., ., .], [., ., ., ., ., ., ., .], [., ., ., ., ., R, ., .]]";
	    int row = 0;
	    int col = 0;
	    for (int i = 0; i < s2.length(); i++) {
	        if (s2.charAt(i) == '[') {
	            row++;
	        }
	    }
	    row--;
	    for (int i = 0;; i++) {
	        if (s2.charAt(i) == ',') {
	            col++;
	        }
	        if (s2.charAt(i) == ']') {
	            break;
	        }
	    }
	    col++;

	    char[][] out = new char[row][col];
	    s2=s2.replaceAll("\\[", "").replaceAll("\\]", "");
	    //System.out.println(s2);
	    String[] s3 = s2.split(", ");
	    //System.out.println(s3[0]);
	    int j = -1;
	    for (int i = 0; i < s3.length; i++) {
	        if (i % col == 0) {
	            j++;
	        }
	        out[j][i % col] = s3[i].charAt(0);
	        //System.out.println(s1[i] + "\t" + j + "\t" + i % col);
	    }
	    return out;
	}

}
