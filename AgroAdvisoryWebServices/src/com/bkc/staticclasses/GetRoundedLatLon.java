package com.bkc.staticclasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class GetRoundedLatLon {

	public static Float getLatLonPoint25Grid(String point) {

		Float pts = Float.valueOf(point.trim()).floatValue();
		Integer intPt = pts.intValue();
		point = point.substring(point.indexOf('.') + 1);
		/*System.out.println(point);*/
		if (point.length() > 1) {
			point = point.substring(0, 2);
		}

		Float aa = Float.parseFloat(point);
		//System.out.println(";;;;;;;;;"+aa);
		
		if (aa >= 0.00f && aa < 15.00f)
			
			pts = intPt + 0.00f;
		
		else if (aa >= 15.00f && aa < 35.00f)
			pts = intPt + 0.25f;
		else if (aa >= 35.00f && aa < 65.00f)
			pts = intPt + 0.50f;
		else if (aa >= 65.00f && aa < 85.00f)
			pts = intPt + 0.75f;
		else if (aa >= 85.00f)
			pts = intPt + 1.00f;

		return pts;
	}
	
	
	public static String priceDescriptionOfCrop(String cropName) {

		StringBuilder desc = new StringBuilder();
		File f = new File("/home/dmdd/Desktop/fasalsalah/mandidescription/" + cropName + ".txt");
		if (f.exists()) {
			try {
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				String line;
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					desc.append(line);
					desc.append(System.getProperty("line.separator"));
				}

				return desc.toString();
			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		return desc.append("No file found").toString();

	}

	public static void main(String[] args) {
		System.out.println(GetRoundedLatLon.getLatLonPoint25Grid("22.50"));
		
		
	}
}
