package com.example.sort;

import java.util.Arrays;

public class MergeSort {

	public static void main(String[] args) {
		int[] arr = {3, 41, 52, 26, 38, 57, 9, 49};
		MergeSort sort = new MergeSort();
		sort.mergesort(0,arr.length-1,arr);
		System.out.println(Arrays.toString(arr));
	}
	
	public void mergesort(int p,int r,int[] arr) {
		if(p<r) {
			int q = (p+r)/2;
			mergesort(q+1,r,arr);
			mergesort(p,q,arr);
			merge(p,q,r,arr);
		}
	}

	private int[] merge(int p, int q, int r, int[] arr) {
		int n1 = q-p+1;
		int n2 = r-q;
		int[] left = new int[n1];
		int[] right = new int[n2];
		for(int i=0;i<n1;i++ ) {
			left[i] = arr[p+i];
		}
		
		for(int i=0;i<n2;i++ ) {
			right[i] = arr[q+i+1];
		}
		
		int i =0;int j=0;
		int k = p; 
		while(i<n1&&j<n2) {
			if(left[i]<right[j]) {
				arr[k] = left[i];
				i++;
			}else{
				arr[k] = right[j];
				j++;
			}
			k++;
		}
		
		while(i<n1) {
			arr[k] = left[i];
			i++;
			k++;
		}
		
		while(j<n2) {
			arr[k] = right[j];
			j++;
			k++;
		}
		return arr;
	}
}
