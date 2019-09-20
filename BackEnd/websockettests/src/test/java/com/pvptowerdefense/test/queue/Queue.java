package com.pvptowerdefense.test.queue;

import java.util.NoSuchElementException;

/**
 * The type Queue.
 *
 * @param <T> the type parameter
 */
public class Queue<T> {
	private Node first;
	private Node last;

	private int size;

	/**
	 * Instantiates a new Queue.
	 */
	public Queue() {
		first = null;
		last = null;
		size = 0;
	}

	/**
	 * Enqueue t.
	 *
	 * @param val the val
	 */
	public void enqueue(T val) {
		Node oldLast = last;

		last = new Node();
		last.val = val;
		last.next = null;

		if (isEmpty()) {
			first = last;
		}
		else {
			oldLast.next = last;
		}
		size++;
	}

	/**
	 * Dequeue t.
	 *
	 * @return the t
	 */
	public T dequeue() {
		if (isEmpty()) {
			return null;
		}

		T val = first.val;
		first = first.next;
		size--;

		if (isEmpty()) {
			last = null;
		}
		return val;
	}

	/**
	 * Is empty boolean.
	 *
	 * @return the boolean
	 */
	public boolean isEmpty() {
		return first == null;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();

		s.append("[");

		for (Node n = first; n != null; n = n.next) {
			s.append(n.val.toString());

			if (n.next != null) {
				s.append(", ");
			}
		}

		s.append("]");

		return s.toString();
	}

	private class Node {
		private Node next;
		private T val;
	}
}
