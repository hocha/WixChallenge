package com.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WixServlet
 */
@WebServlet("/toDo/*")
public class WixServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String HTML_1 = "<html><head><meta charset=\"ISO-8859-1\"><title> To Do List </title><style>td{ padding:15px 15px 0 15px; }table { border: none; border-collapse: collapse; }table  }table td:first-child { border-left: none; }</style></head><body><div style = \"background-color:darkblue\"><center><div class=\"container\"><h1><span style=\"color:white\"><font face=\"Tw Cen MT\" size=\"10\">ToDo List</font></span> </h1><h3><span style=\"color:lightblue\"><font face=\"Futura\"> Welcome to your personalized ToDo list app.</font></span> </h3></div></center></div><div class=\"container\"><table ><col width=\"40%\"><col width=\"40%\"><tr><td valign=\"top\"><center><div style=\"color:darkblue\"><h2><font face=\"Futura\"> What should I do?</font> </h2></center></div><ul><font face=\"Futura\"><h3>Add a task to the toDo list.</h3><p>Write the name of the task below.</p><form method=\"POST\" action=\"toDo/add\"/><input name=\"taskDesc\" type=\"text\" /><input type=\"submit\" value=\"add task\" /></form><br><h3>Delete a toDo list task.</h3><p>Write the ID of the task you wish to delete. If you don't remember the task's ID, click on the button below to look at the list of tasks.</p><form method=\"POST\" action=\"toDo/delete\"/><input name=\"taskID\" id = \"intNum\" type=\"number\" min=\"0\" max=\"";
	private static final String HTML_2 = "\" /><input type=\"submit\" value=\"delete task\" /></form><br><h3>Show me.</h3><p> Update the display to show the history or the task list.</p><form method=\"POST\" action=\"toDo/update\"/><input type=\"radio\" name=\"displayVal\" value=\"tasks\"> All Tasks<br><input type=\"radio\" name=\"displayVal\" value=\"log\" checked> All Actions<br><input type=\"submit\" value=\"see list\" /></form></font></ul></td><td valign=\"top\"><center><div style=\"color:darkblue\"><h2><font face=\"Futura\"> What's on my list?</font> </h2></div><ul><font face=\"Futura\"><table style=\"width:100%\"><tr style=\"outline: thin solid\"><th> ID </th><th> Description </th><th> Timestamp </th></tr>";
	private static final String HTML_3 = "</table></font></ul></center></td></tr></table></body></html>";
	
	private HashMap<Integer, TaskObj> taskList;
	private HashSet<TaskObj> log;
	private int count;
	private boolean changeDisplay;
	
	
	private PrintWriter tasks;
	private PrintWriter history;
	
	
	/**
	 * Object class for toDo tasks and log entries
	 */	
	public static class TaskObj {
		String[] attr;
		
		public TaskObj(int ID, String task, String date){
			attr = new String[3];
			attr[0] = "" + ID;
			attr[1] = task;
			attr[2] = date;
		}
		@Override
		public boolean equals(Object o){
			if(!(o instanceof TaskObj)){				
				return false;
			}
			if (!((TaskObj) o).attr[0].equals(attr[0])){
				return false;
			}
			if (!((TaskObj) o).attr[1].equals(attr[1])){
				return false;
			}
			if (!((TaskObj) o).attr[2].equals(attr[2])){
				return false;
			}
			return true;
			
		}
	}
		
       
    /**
     * @throws IOException 
     * @see HttpServlet#HttpServlet()
     */
    public WixServlet() throws IOException {
        super();
        taskList = new HashMap<Integer, TaskObj>();   	
    	log = new HashSet<TaskObj>();
    	changeDisplay = true;
    	count = 0;   
    	readFile("tasks.txt", true);
    	readFile("log.txt", false);    	

    }
    /**
     * Clears the task and log files to restart the program
     */
    
    public void clear() throws FileNotFoundException, UnsupportedEncodingException{
    	taskList.clear();
        log.clear();
        count = 0;	
        history = new PrintWriter("log.txt", "UTF-8");
        history.close();
        tasks = new PrintWriter("tasks.txt", "UTF-8");
        tasks.close();
  }
    
    
	/**
	 * @throws IOException
	 * reads in tasks from task.txt
	 */
	public void readFile(String file, boolean list) throws IOException{
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
		    String line = br.readLine();
		    int i = 0;
		    String[] data = new String[3];
		    while (line != null) {
		    	data[i++] = line;
		    	if (i ==3){
		    		int ID = Integer.parseInt(data[0]);
		    		if (list) {
		    			taskList.put(ID, new TaskObj(ID, data[1], data[2]));
		    			count = ID+1;
		    		}
		    		else {
		    			log.add(new TaskObj(ID, data[1], data[2]));
		    		}
		    		
		    		i = 0;
		    	}
		    	line = br.readLine();
		    }
		    br.close();
		} catch (IOException e){
			return;
		}	
	}

	/**
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 * @return all the items and writes them to task.txt for persistent storage 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */    
    public Collection<TaskObj> getAllitems() throws FileNotFoundException, UnsupportedEncodingException {
    	tasks = new PrintWriter("tasks.txt", "UTF-8");
    	for (TaskObj t: taskList.values()){
    		tasks.println(t.attr[0]);
    		tasks.println(t.attr[1]);
    		tasks.println(t.attr[2]);    		
    	}
    	tasks.close();
    	return taskList.values();

    }
    
    
	/**
	 * @return the history of actions
	 * @throws UnsupportedEncodingException 
	 * @return all the actions and writes them to log.txt for persistent storage 
	 * @throws FileNotFoundException 
	 */  
    public Collection<TaskObj> showHistory() throws FileNotFoundException, UnsupportedEncodingException {
    	history = new PrintWriter("log.txt", "UTF-8");
    	for (TaskObj t: log){
    		history.println(t.attr[0]);
    		history.println(t.attr[1]);
    		history.println(t.attr[2]);    		
    	}
    	history.close();   	
    	return log;
    }

    
	/**
	 * Deletes the specified item
	 */  
    public boolean deleteItem(int ID){
    	if (ID >= count){
    		return false;
    	}
    	Date date = new java.util.Date();
    	DateFormat formatter = new SimpleDateFormat();
    	taskList.remove(ID);
    	log.add(new TaskObj(ID, "deleted", formatter.format(date)));	
    	return true;
    	
    }
    public boolean deleteItem(int ID, String date){
    	if (ID >= count){
    		return false;
    	}
    	taskList.remove(ID);
    	log.add(new TaskObj(ID, "deleted", date));	
    	return true;
    	
    }

	/**
	 * Adds the specified item.
	 * @return that items ID
	 */  
    public int addItem(String description){
    	Date date = new java.util.Date();
    	DateFormat formatter = new SimpleDateFormat();
    	taskList.put(count, new TaskObj(count, description, formatter.format(date)));
    	log.add(new TaskObj(count, "added", formatter.format(date)));
    	return count++;
    }
    
	/**
	 * Adds the specified item.
	 * @return that items ID
	 */  
    public int addItem(String description, String date){
    	taskList.put(count, new TaskObj(count, description, date));
    	log.add(new TaskObj(count, "added", date));
    	return count++;
    }
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter p = response.getWriter();
		p.append(HTML_1);
		p.append("" + (count-1)); //to limit the delete option to be within range
		p.append(HTML_2);
			
		Collection<TaskObj> info = (changeDisplay) ? getAllitems() : showHistory();		
		for (TaskObj a: info){	
			StringBuilder sb = new StringBuilder();
			sb.append("<tr>");
			for (int j = 0; j < 3; j ++){
				sb.append("<td>"); 
				sb.append(a.attr[j]);
				sb.append("</td>"); 
			}
			sb.append("</tr>");
			p.append(sb);
		}			
		p.append(HTML_3);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		//to add a new task
		if (request.getPathInfo().endsWith("/add")){			
			addItem(request.getParameter("taskDesc"));
			response.sendRedirect(request.getContextPath() + "/toDo/add");
		}
		
		//to delete a task
		else if (request.getPathInfo().endsWith("/delete")){	
			int ID = Integer.parseInt(request.getParameter("taskID"));
			deleteItem(ID);	
			response.sendRedirect(request.getContextPath() + "/toDo/delete");
		}
		
		//to change the display to feature either history or the task list
		else if (request.getPathInfo().endsWith("/update")){			
			//to display the history
			if (request.getParameter("displayVal").equals("log")){
				changeDisplay = false;
			}
			
			//to display the task list
			else {
				changeDisplay = true;
			}
			response.sendRedirect(request.getContextPath() + "/toDo");
		}
	}
}

