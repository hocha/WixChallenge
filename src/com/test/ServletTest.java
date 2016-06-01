package com.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.test.WixServlet.TaskObj;

public class ServletTest {
	WixServlet a;
	
	@Test
	public void emptyList() throws IOException {
		a = new WixServlet();
		a.clear();
		assertTrue(a.getAllitems().isEmpty());
	}
	
	@Test
	public void addOneNotEmpty() throws IOException {
		a = new WixServlet();
		a.clear();
		a.addItem("Walk the Dog", "6/1/16 10:27 AM");
		assertFalse(a.getAllitems().isEmpty());
	}
	@Test
	public void addOneSize() throws IOException {
		a = new WixServlet();
		a.clear();
		a.addItem("Walk the Cat", "6/1/16 10:27 AM");
		assertEquals(1, a.getAllitems().size());
	}
	@Test
	public void addOneContains() throws IOException {
		a = new WixServlet();
		a.clear();
		TaskObj t = new TaskObj(0, "Walk the Gorilla","6/1/16 10:27 AM");
		a.addItem("Walk the Gorilla", "6/1/16 10:27 AM");
		assertTrue(a.getAllitems().contains(t));
	}
	@Test
	public void deleteEmpty() throws IOException {
		a = new WixServlet();
		a.clear();
		assertFalse(a.deleteItem(8, "6/1/16 10:27 AM"));
	}
	@Test
	public void addOnedeleteOne() throws IOException {
		a = new WixServlet();
		a.clear();
		a.addItem("Walk the Hippo", "6/1/16 10:27 AM");
		assertTrue(a.deleteItem(0, "6/1/16 10:27 AM"));
	}
	@Test
	public void addOnedeleteOneEmpty() throws IOException {
		a = new WixServlet();
		a.clear();
		a.addItem("Walk the Cow", "6/1/16 10:27 AM");
		a.deleteItem(0, "6/1/16 10:27 AM");
		assertTrue(a.getAllitems().isEmpty());
	}
	@Test
	public void addTwoSize() throws IOException {
		a = new WixServlet();
		a.clear();
		a.addItem("Walk the Bunny", "6/1/16 10:27 AM");
		a.addItem("Walk the Goat", "6/1/16 10:27 AM");
		assertEquals(2, a.getAllitems().size());
	}
	@Test
	public void addTwoContainsOne() throws IOException {
		a = new WixServlet();
		a.clear();
		TaskObj t = new TaskObj(0, "Walk the Ant","6/1/16 10:27 AM");
		a.addItem("Walk the Ant", "6/1/16 10:27 AM");
		a.addItem("Walk the Lizard", "6/1/16 10:27 AM");
		assertTrue(a.getAllitems().contains(t));
	}
	@Test
	public void addTwoContainsTwo() throws IOException {
		a = new WixServlet();
		a.clear();
		TaskObj t = new TaskObj(1, "Walk the Lizard","6/1/16 10:27 AM");
		a.addItem("Walk the Ant", "6/1/16 10:27 AM");
		a.addItem("Walk the Lizard", "6/1/16 10:27 AM");
		assertTrue(a.getAllitems().contains(t));
	}
	@Test
	public void addTwoedeleteOneNotEmpty() throws IOException {
		a = new WixServlet();
		a.clear();
		a.addItem("Walk the Giraffe", "6/1/16 10:27 AM");
		a.addItem("Walk the Armadilo", "6/1/16 10:27 AM");
		a.deleteItem(0, "6/1/16 10:27 AM");
		assertFalse(a.getAllitems().isEmpty());
	}
	@Test
	public void addTwodeleteOneNotContains() throws IOException {
		a = new WixServlet();
		a.clear();
		TaskObj t = new TaskObj(1, "Walk the Spider","6/1/16 10:27 AM");
		a.addItem("Walk the Hippo", "6/1/16 10:27 AM");
		a.addItem("Walk the Spider", "6/1/16 10:27 AM");
		a.deleteItem(1, "6/1/16 10:27 AM");
		assertFalse(a.getAllitems().contains(t));
	}
	@Test
	public void addTwodeleteOneContains() throws IOException {
		a = new WixServlet();
		a.clear();
		TaskObj t = new TaskObj(0, "Walk the Fly","6/1/16 10:27 AM");
		a.addItem("Walk the Fly", "6/1/16 10:27 AM");
		a.addItem("Walk the Hyena", "6/1/16 10:27 AM");
		a.deleteItem(1, "6/1/16 10:27 AM");
		assertTrue(a.getAllitems().contains(t));
	}
	@Test
	public void emptyLog() throws IOException {
		a = new WixServlet();
		a.clear();
		assertTrue(a.showHistory().isEmpty());
	}
	@Test
	public void addOnedeleteOneNotEmptyLog() throws IOException {
		a = new WixServlet();
		a.clear();
		a.addItem("Walk the StingRay", "6/1/16 10:27 AM");
		a.deleteItem(0, "6/1/16 10:27 AM");
		assertFalse(a.showHistory().isEmpty());
	}
	@Test
	public void addTwoSizeLog() throws IOException {
		a = new WixServlet();
		a.clear();
		a.addItem("Walk the Deer", "6/1/16 10:27 AM");
		a.addItem("Walk the Fish", "6/1/16 10:27 AM");
		assertEquals(2, a.showHistory().size());
	}

	@Test
	public void addOnedeleteOneLogSize() throws IOException {
		a = new WixServlet();
		a.clear();
		a.addItem("Walk the Tapir", "6/1/16 10:27 AM");
		a.deleteItem(0, "6/1/16 10:27 AM");
		assertEquals(2, a.showHistory().size());
	}

	@Test
	public void addTwodeleteOneLogSize() throws IOException {
		a = new WixServlet();
		a.clear();
		a.addItem("Walk the Trex", "6/1/16 10:27 AM");
		a.addItem("Walk the Shark", "6/1/16 10:27 AM");
		a.deleteItem(0, "6/1/16 10:27 AM");
		assertEquals(3, a.showHistory().size());
	}
}
