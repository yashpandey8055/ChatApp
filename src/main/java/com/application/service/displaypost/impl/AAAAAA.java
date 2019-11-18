package com.application.service.displaypost.impl;

public class AAAAAA {
	
	public static void main(String[] args) throws InterruptedException {
		for(int i =100;i>0;i--) {
			for(int j=0;j<i;j++) {
				System.out.print(" ");
				
			}
			System.out.print("A");
			for(int j=0;j<100-i;j++) {
				if(i<50) {
					System.out.print("AA");	
				}else {
					System.out.print("  ");
				}
				
			}
			System.out.print("A");
			System.out.println("");
			Thread.sleep(50);
		}
	}
	

}
