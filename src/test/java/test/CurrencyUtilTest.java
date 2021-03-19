package test;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pl.streamsoft.exception.CurrencyRateServiceUnavailableException;
import pl.streamsoft.exception.FutureDateException;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.service.CurrencyRatesProvider;
import pl.streamsoft.util.CurrencyUtil;

class CurrencyUtilTest {
	
	@Test(dataProvider = "testData")
	void convertToPLNByNBPServiceBasicTest(BigDecimal valueToConvert, CurrencyCode newCurrencyCode, LocalDate date, BigDecimal expectedValue) {
		Assert.assertEquals(CurrencyUtil.convertToPLN(valueToConvert, newCurrencyCode, date, CurrencyRatesProvider.NBP), expectedValue);
	}
	
	@Test(dataProvider = "testDataWeekend")
	void convertToPLNByNBPServiceOnWeekendTest(BigDecimal valueToConvert, CurrencyCode newCurrencyCode, LocalDate date, BigDecimal expectedValue) {
		Assert.assertEquals(CurrencyUtil.convertToPLN(valueToConvert, newCurrencyCode, date, CurrencyRatesProvider.NBP), expectedValue);
	}

	
	@Test(expectedExceptions = CurrencyRateServiceUnavailableException.class)
	void throwCurrencyRateServiceUnavaibleExceptionForCurrencyRateCSVFileServiceTest() {
		CurrencyUtil.convertToPLN(new BigDecimal("1000"), CurrencyCode.EUR, LocalDate.parse("2020-03-19"), CurrencyRatesProvider.CSV_FILE);
	}
	
	@Test(expectedExceptions = FutureDateException.class)
	void throwFutureDateExceptionForCurrencyRateCSVFileServiceTest() {
		CurrencyUtil.convertToPLN(new BigDecimal("1000"), CurrencyCode.BGN, LocalDate.now().plusDays(2), CurrencyRatesProvider.CSV_FILE);
	}
	
	@Test(expectedExceptions = FutureDateException.class)
	void throwFutureDateExceptionForForCurrencyRateNBPServiceTest() {
		CurrencyUtil.convertToPLN(new BigDecimal("1000"), CurrencyCode.EUR, LocalDate.now().plusYears(3), CurrencyRatesProvider.CSV_FILE);
	}
	
	@DataProvider(name = "testData")
	Object dataProvider() { 
		Object[][] data = new Object[3][4];
		
		data[0][0] = new BigDecimal("1000");
		data[0][1] = CurrencyCode.USD; 
		data[0][2] = LocalDate.parse("2021-03-16");
		data[0][3] = new BigDecimal("3851.90");
		
		data[1][0] = new BigDecimal("213");
		data[1][1] = CurrencyCode.JPY;
		data[1][2] = LocalDate.parse("2019-10-10");
		data[1][3] = new BigDecimal("7.78");
		
		data[2][0] = new BigDecimal("1275.35");  
		data[2][1] = CurrencyCode.HUF;
		data[2][2] = LocalDate.parse("2012-12-12");
		data[2][3] = new BigDecimal("18.48");
	
		return data;
	}
	
	@DataProvider(name = "testDataWeekend")
	Object dataProviderWeekend() { 
		Object[][] data = new Object[3][4];
		
		data[0][0] = new BigDecimal("312.45");
		data[0][1] = CurrencyCode.USD; 
		data[0][2] = LocalDate.parse("2021-03-06");
		data[0][3] = CurrencyUtil.convertToPLN(new BigDecimal("312.45"), CurrencyCode.USD, LocalDate.parse("2021-03-05"), CurrencyRatesProvider.NBP);
		
		data[1][0] = new BigDecimal("213");
		data[1][1] = CurrencyCode.EUR;
		data[1][2] = LocalDate.parse("2019-10-27");
		data[1][3] = CurrencyUtil.convertToPLN(new BigDecimal("213"), CurrencyCode.EUR, LocalDate.parse("2019-10-26"), CurrencyRatesProvider.NBP);
		
		data[2][0] = new BigDecimal("1275.35");  
		data[2][1] = CurrencyCode.HUF;
		data[2][2] = LocalDate.parse("2021-03-14");
		data[2][3] = CurrencyUtil.convertToPLN(new BigDecimal("1275.35"), CurrencyCode.HUF, LocalDate.parse("2021-03-12"), CurrencyRatesProvider.NBP);
	
		return data;
	}
	
}
