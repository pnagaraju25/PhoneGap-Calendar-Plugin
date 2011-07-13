package com.phonegap.calendar.android.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;

import com.phonegap.calendar.android.model.CalendarClient;
import com.phonegap.calendar.android.model.CalendarEntry;
import com.phonegap.calendar.android.model.CalendarFeed;
import com.phonegap.calendar.android.model.CalendarUrl;
import com.phonegap.calendar.android.model.EventEntry;
import com.phonegap.calendar.android.model.EventFeed;
import com.phonegap.calendar.android.utils.DateUtils;

public class CalendarOps {

	private static final String TAG = "CalendarOps";
	
	 public static List<CalendarEntry> getUserCalendars(CalendarClient client){
		 
		  List<CalendarEntry> calendars = new ArrayList<CalendarEntry>();
		    calendars.clear();
		    try {
		      CalendarUrl url = CalendarUrl.forAllCalendarsFeed();
		      // page through results
		      while (true) {
		        CalendarFeed feed = client.executeGetCalendarFeed(url);
		        if (feed.calendars != null) {
		          calendars.addAll(feed.calendars);
		        }
		        String nextLink = feed.getNextLink();
		        if (nextLink == null) {
		          break;
		        }
		      }
//		      int numCalendars = calendars.size();
//		      calendarNames = new String[numCalendars];
//		      for (int i = 0; i < numCalendars; i++) {
//		        calendarNames[i] = calendars.get(i).title;
//		      }
		    } catch (IOException e) {
		    	Log.e(TAG, "Error getting calendars"+e.getMessage());		    	
		      calendars.clear();
		    }
		    return calendars;
//		  return calendarNames;
	  }
	 
	 
	 public static List<EventEntry> findUserEvents(CalendarClient client, Date minStart, Date maxStart){
		 
		  List<EventEntry> events = new ArrayList<EventEntry>();
		    events.clear();
		    try {
		    	String minString = "1970-01-01T00:00:00";
		    	String maxString = "2031-01-01T00:00:00";
		    	if (minStart!=null)
		    		minString = DateUtils.dateToStringCalendarDate(minStart, null);
		    	if (maxStart!=null)
		    		maxString = DateUtils.dateToStringCalendarDate(maxStart, null);

		    CalendarUrl url = CalendarUrl.forDefaultPrivateFullEventFeedBetweenDates(
		    					minString, 
		    					maxString);
		    	
		        EventFeed feed = client.executeGetEventFeed(url);
		        if (feed.events != null) {
		          events.addAll(feed.events);
		        }
		    } catch (IOException e) {
		    	Log.e(TAG, "Error getting calendars"+e.getMessage());
		    	e.printStackTrace();
		    	events.clear();
		    }
		  return events;
	  }
	 
	 //TODO When must be this called?
	  public static void shutdown(CalendarClient client) {
		    try {
		      client.shutdown();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }

		  public static CalendarEntry addCalendar(CalendarClient client, CalendarEntry calendar) throws IOException {
		    Log.i(TAG, "Add Calendar");
		    CalendarUrl url = CalendarUrl.forOwnCalendarsFeed();
		    CalendarEntry result = client.executeInsertCalendar(calendar, url);
		    Log.i(TAG, "Added "+ result.title +" Calendar");
		    return result;
		  }

	public static CalendarEntry updateCalendar(CalendarClient client,
			CalendarEntry calendar, CalendarEntry original) throws IOException {
		Log.i(TAG, "Update Calendar");
		CalendarEntry result = client.executePatchCalendarRelativeToOriginal(
				calendar, original);
		Log.i(TAG, "Updated: " + original.title + " Calendar into: "
				+ result.title + " Calendar");
		return result;
	}

	public static EventEntry updateEvent(CalendarClient client,
			EventEntry event, EventEntry original) throws IOException {
		Log.i(TAG, "Update Calendar");
		EventEntry result = client.executePatchEventRelativeToOriginal(event,
				original);
		Log.i(TAG, "Updated: " + original.title + " Calendar into: "
				+ result.title + " Calendar");
		return result;
	}
		  
		  public static void addEvent(CalendarClient client, CalendarEntry calendar, EventEntry event) throws IOException {
			  Log.i(TAG, "Add Event");
		    CalendarUrl url = new CalendarUrl(calendar.getEventsFeedLink());
		    EventEntry result = client.executeInsertEvent(event, url);
		    Log.i(TAG, "Added "+result.title+" Event");
		  }

		  public static void deleteCalendar(CalendarClient client, CalendarEntry calendar)
		      throws IOException {
			  Log.i(TAG,"Delete Calendar");
		    client.executeDelete(calendar);
		  }
		  
		  public static void deleteEvent(CalendarClient client, EventEntry event)
	      throws IOException {
			  Log.i(TAG,"Delete Calendar");
			  client.executeDelete(event);
		  }
	 
		  public static CalendarEntry getUserCalendarByTitle(CalendarClient client, String title){
				 
			  List<CalendarEntry> calendars = new ArrayList<CalendarEntry>();
			    calendars.clear();
			    try {
			      CalendarUrl url = CalendarUrl.forAllCalendarsFeed();
			      // page through results
			      while (true) {
			        CalendarFeed feed = client.executeGetCalendarFeed(url);
			        if (feed.calendars != null) {
			          calendars.addAll(feed.calendars);
			        }
			        String nextLink = feed.getNextLink();
			        if (nextLink == null) {
			          break;
			        }
			      }
			      int numCalendars = calendars.size();

			      for (int i = 0; i < numCalendars; i++) {
			        if (calendars.get(i).title.equals(title))
			        	return calendars.get(i);
			      }
			    } catch (IOException e) {
			    	Log.e(TAG, "Error getting calendars"+e.getMessage());
			      calendars.clear();
			      return null;
			    }
			    return null;
		  }
		  
//		  public static void batchAddEvents(CalendarClient client, CalendarEntry calendar)
//	      throws IOException {
//		  Log.i(TAG, "Batch Add Events");
//	    EventFeed feed = new EventFeed();
//	    for (int i = 0; i < 3; i++) {
//	      try {
//	        Thread.sleep(1000);
//	      } catch (InterruptedException e) {
//	      }
//	      EventEntry event = newEvent();
//	      event.batchId = Integer.toString(i);
//	      event.batchOperation = BatchOperation.INSERT;
//	      feed.events.add(event);
//	    }
//	    EventFeed result = client.executeBatchEventFeed(feed, calendar);
//	    for (EventEntry event : result.events) {
//	      BatchStatus batchStatus = event.batchStatus;
//	      if (batchStatus != null && !HttpResponse.isSuccessStatusCode(batchStatus.code)) {
//	        System.err.println("Error posting event: " + batchStatus.reason);
//	      }else {
//	    	  Log.i(TAG, "Added "+event.title+" Event");
//	      }
//	    }
//	  //TODO SHOW result
//	  }
	 
}