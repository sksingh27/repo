package com.bkc.dao;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class AdvisoryDaoImplTest {

	@Test
	public void testAhead7Days() {
		AdvisoryDaoImpl test= new AdvisoryDaoImpl();
		
		
		assertEquals("true", test.ahead7Days(new Date(), 22.5f, 77.5f));
	}

}
