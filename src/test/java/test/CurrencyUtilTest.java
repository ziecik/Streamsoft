package test;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import model.CurrencyCode;
import util.CurrencyUtil;

class CurrencyUtilTest {
	
	@Test(dataProvider = "testData")
	void convertToPLNBasicTest(BigDecimal valueToConvert, CurrencyCode newCurrencyCode, LocalDate date, BigDecimal expectedValue) {
		Assert.assertEquals(expectedValue, CurrencyUtil.convertToPLN(valueToConvert, newCurrencyCode, date));
	}
	
	@Test(dataProvider = "testDataWeekend")
	void convertToPLNOnWeekendTest(BigDecimal valueToConvert, CurrencyCode newCurrencyCode, LocalDate date, BigDecimal expectedValue) {
		Assert.assertEquals(expectedValue, CurrencyUtil.convertToPLN(valueToConvert, newCurrencyCode, date));
	}

	
	@DataProvider(name = "testData")
	Object dataProvider() { 
		Object[][] data = new Object[3][4];
		
		data[0][0] = new BigDecimal("1000");
		data[0][1] = CurrencyCode.USD; 
		data[0][2] = LocalDate.parse("2021-03-16");
		data[0][3] = new BigDecimal("3851.9").setScale(2);
		
		data[1][0] = new BigDecimal("213");
		data[1][1] = CurrencyCode.JPY;
		data[1][2] = LocalDate.parse("2019-10-10");
		data[1][3] = new BigDecimal("7.77");
		
		data[2][0] = new BigDecimal("1275.35");  
		data[2][1] = CurrencyCode.HUF;
		data[2][2] = LocalDate.parse("2012-12-12");
		data[2][3] = new BigDecimal("18.47");
	
		return data;
	}
	
	@DataProvider(name = "testDataWeekend")
	Object dataProviderWeekend() { 
		Object[][] data = new Object[3][4];
		
		data[0][0] = new BigDecimal("312.45");
		data[0][1] = CurrencyCode.USD; 
		data[0][2] = LocalDate.parse("2021-03-06");
		data[0][3] = new BigDecimal("1199.58");
		
		data[1][0] = new BigDecimal("213");
		data[1][1] = CurrencyCode.EUR;
		data[1][2] = LocalDate.parse("2019-10-27");
		data[1][3] = new BigDecimal("910.83");
		
		data[2][0] = new BigDecimal("1275.35");  
		data[2][1] = CurrencyCode.HUF;
		data[2][2] = LocalDate.parse("2012-12-12");
		data[2][3] = new BigDecimal("18.47");
	
		return data;
	}
	
}
