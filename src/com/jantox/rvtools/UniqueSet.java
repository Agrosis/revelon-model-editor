package com.jantox.rvtools;

import java.util.Arrays;

public class UniqueSet<E> {

	protected E[] items;
	protected int size;
	
	@SuppressWarnings("unchecked")
	public UniqueSet() {
		items = (E[]) new Object[4];
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public int indexOf(E item) {
		for(int i = 0; i < size; i++)
			if(items[i].equals(item))
				return i;
		return -1;
	}
	
	public void add(E item) {
		if(contains(item))
			return;
		
		if (size == items.length) {
	         items = Arrays.copyOf(items, Math.max(4, items.length << 1));
	      }
	      items[size++] = item;
	}
	
	public boolean contains(E item) {
		for(int i = 0; i < size; i++)
			if(items[i].equals(item))
				return true;
		return false;
	}
	
	public E get(int i) {
		return items[i];
	}
	
}
