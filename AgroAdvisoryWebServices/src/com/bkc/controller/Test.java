package com.bkc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Test {

	public static void main(String[] args) {
		int a = 010 | 4;
		System.out.println(a);

	}

	static Float getLatLonPoint(Float point) {
		// Float pts = Float.valueOf(point.trim()).floatValue();
		Integer intPt = point.intValue();

		String point1 = String.valueOf(point);
		String point2 = point1.substring(point1.indexOf('.') + 1, point1.indexOf('.') + 3);
		Float aa = Float.parseFloat(point2);
		System.out.println("lll" + aa);
		if (aa >= 0.00f && aa < 30.00f)
			point = intPt + 0.00f;
		else if (aa >= 30.00f && aa < 75.00f)
			point = intPt + 0.50f;
		else if (aa > 75.00f)
			point = intPt + 1.00f;

		return point;
	}

}
