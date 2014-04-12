package com.binar.core.report.reportInterface;

import org.joda.time.DateTime;

public class ReportInterfaceModel {
	
	public static void main(String[] args) {
		DateTime date=new DateTime();
		date=date.withDayOfMonth(30);
		System.out.println(date.withDayOfWeek(1).toString());
		System.out.println(date.withDayOfWeek(7).toString());
		System.out.println(date.getDayOfWeek());
	}
}
