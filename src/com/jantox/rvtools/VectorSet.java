package com.jantox.rvtools;

import java.util.Arrays;

public class VectorSet<E> {

	protected E[] items;
	protected int size;
	
	@SuppressWarnings("unchecked")
	public VectorSet() {
		items = (E[]) new Object[4];
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public int indexOf(Vector3D item) {
		for(int i = 0; i < size; i++) {
			Vector3D t = (Vector3D) items[i];
			if(item.x == t.x && item.y == t.y && item.z == t.z)
				return i;
		}
		return -1;
	}
	
	public void add(Vector3D item) {
		if(contains(item))
			return;
		
		if (size == items.length) {
	         items = Arrays.copyOf(items, Math.max(4, items.length << 1));
	      }
	      items[size++] = (E) item;
	}
	
	public boolean contains(Vector3D item) {
		for(int i = 0; i < size; i++) {
			Vector3D t = (Vector3D) items[i];
			if(item.x == t.x && item.y == t.y && item.z == t.z)
				return true;
		}
		return false;
	}
	
	public E get(int i) {
		return items[i];
	}
	
}
