package com.example.sort;

import java.util.Arrays;

public class RecursiveInsertionSort {

	public static void main(String[] args) {
		int[] arr = {5, 2, 4, 6, 1, 3,16,17,14,20,21,13,12,10,28,26,24,30,33,44,39,42};
		RecursiveInsertionSort sort = new RecursiveInsertionSort();
		sort.insertsort(arr.length-1,arr);
		System.out.println(Arrays.toString(arr));
	}
	
	
	public void insertsort(int n, int[] arr) {
		if(n<0) {
			return;
		}
			insertsort(n-1,arr);
			
			int i = n;
			while(i>0) {
				if(n<arr.length&&arr[i]<arr[i-1]) {
					int key = arr[i-1];
					arr[i-1] = arr[i];
					arr[i] = key;
				}
				i--;
			}
		
	}
	
	
}
