package com.example.sort;

import java.util.Arrays;

public class InsertionSort {
	
	public static void main(String[] args) {
		int[] arr = {5, 2, 4, 6, 1, 3,16,17,14,20,21,13,12,10,28,26,24,30,33,44,39,42};
		InsertionSort sort = new InsertionSort();
		System.out.println(Arrays.toString(sort.sort(arr)));
	}
	
	public int[] sort(int[] arr) {
		int count=0;
		for(int i=1;i<arr.length;i++) {
			int key = arr[i];
			int j = i-1;
			while(j>=0&&arr[j]<key) {
				count++;
				arr[j+1] = arr[j];
					j--;	
			}
			
			arr[j+1] = key;
		}
		System.out.println("Number of Iterations::"+count);
		return arr;
	}


}
