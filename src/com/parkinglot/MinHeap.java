package com.parkinglot;

public class MinHeap {
	
	int[] arr ;
	private int size;
	private static int HEAD = 1;
	
	public MinHeap(int maxSize){
		arr = new int[maxSize*2+1];
		size=0;
		arr[0] = Integer.MIN_VALUE;
	}
	
	private int getParent(int pos){
		return pos/2;
	}
	
	private int getLeftChild(int pos){
		return pos*2;
	}
	
	private int getRightChild(int pos){
		return pos*2+1;
	}
	
	private void swap(int curr,int parent){
		int tmp = arr[curr];
		arr[curr] = arr[parent];
		arr[parent] = tmp;
	}
	
	
	public void insertElement(int val){
		arr[++size] = val;
		int current = size;
		while(arr[current] < arr[getParent(current)]){
			swap(current,getParent(current));
			current = getParent(current);
		}
	}
	
	private void minHeapify(int pos){
		
		boolean lSwap = false;
		boolean rSwap = false;
		
		if(!isLeaf(pos)){
			
			if((arr[pos] > arr[getLeftChild(pos)] && pos*2 <=size ))
				lSwap = true;
			
			if(( arr[pos] > arr[getRightChild(pos)] && pos*2+1 <=size))
				rSwap = true;
			
			if(((arr[getLeftChild(pos)] > arr[getRightChild(pos)]) && lSwap && rSwap)){
				swap(pos,getRightChild(pos));
				minHeapify(getRightChild(pos));
			} else if (((arr[getLeftChild(pos)] < arr[getRightChild(pos)]) && lSwap && rSwap)) {
				swap(pos,getLeftChild(pos));
				minHeapify(getLeftChild(pos));
			}
			else if(!lSwap && rSwap){
				swap(pos,getRightChild(pos));
				minHeapify(getRightChild(pos));
			}
			else if(lSwap && !rSwap){
				swap(pos,getLeftChild(pos));
				minHeapify(getLeftChild(pos));
			}
			
			
		}
	}
	
	public int removeElement(){
		if(size == 0)
			return -1;
		int poppedElement = arr[HEAD];
		arr[HEAD] = arr[size];
		size--;
		minHeapify(HEAD);
		return poppedElement;
	}
	
	
	private boolean isLeaf(int pos){
		if(pos>size/2){
			return true;
		}
		
		return false;
	}
	
	public void minHeap(){
		for(int pos=size/2;pos>=1;pos--){
			minHeapify(pos);
		}
	}
	
	
}
