package com.example.sort;

import java.util.Arrays;

public class SelectionSort {
	
	
	public static void main(String[] args) {
		int[] arr = {5, 2, 4, 6, 1, 3,16,17,14,20,21,13,12,10,28,26,24,30,33,44,39,42};
		SelectionSort sort = new SelectionSort();
		System.out.println(Arrays.toString(sort.sort(arr)));
	}
	
	public int[] sort(int[] arr) {
		int count = 0;
		for(int i=0;i<arr.length;i++) {
			int j =i;
			int min = j;
			int key= 0;
			
			while(j<arr.length) {
				count++;
				 if(arr[j]<arr[min]) {
					 min = j;
				 }
				j++;
			}
			key = arr[min];
			arr[min] = arr[i];
			arr[i] = key;
			
		}
		System.out.println("Iterations are::"+count);
		return arr;
	}

}
