package test;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.streamsoft.model.AmountDataToConvert;
import pl.streamsoft.model.ConvertedAmount;
import pl.streamsoft.model.CurrencyCode;
import pl.streamsoft.util.CurrencyConverter;

public class RollbackDateTest {

    @Test
    void should_rollbackDateToFriday_when_dataNofFoundOnSaturday() {

//		given:
	CurrencyConverter currencyConverter = new CurrencyConverter();
	BigDecimal valueToConvert = new BigDecimal("1500.99");
	CurrencyCode euro = CurrencyCode.EUR;
	LocalDate sat = LocalDate.parse("2021-03-20");
		
	AmountDataToConvert amountDataToConvert = new AmountDataToConvert(valueToConvert, euro, sat);

//		when:
	ConvertedAmount convertedAmount = currencyConverter.convertToPLN(amountDataToConvert);
	
//		then:
	LocalDate fri = LocalDate.parse("2021-03-19");
	LocalDate dateOfAnnouncedRate = convertedAmount.getCurrencyRateUsedToConvertion().getDateOfAnnouncedRate();
	AssertJUnit.assertEquals(fri,dateOfAnnouncedRate);
    }

    
    @Test
    void should_rollbackDateToPreviousDateWithData_when_dataNofFoundOnHoliday() {

//		given:
	CurrencyConverter currencyConverter = new CurrencyConverter();
	BigDecimal valueToConvert = new BigDecimal("1500.99");
	CurrencyCode euro = CurrencyCode.EUR;
	LocalDate hiliday = LocalDate.parse("2021-01-06");
		
	AmountDataToConvert amountDataToConvert = new AmountDataToConvert(valueToConvert, euro, hiliday);

//		when:
	ConvertedAmount convertedAmount = currencyConverter.convertToPLN(amountDataToConvert);
	
//		then:
	LocalDate dayBeforeHolidayWithData = LocalDate.parse("2021-01-05");
	LocalDate dateOfAnnouncedRate = convertedAmount.getCurrencyRateUsedToConvertion().getDateOfAnnouncedRate();
	AssertJUnit.assertEquals(dayBeforeHolidayWithData,dateOfAnnouncedRate);
    }
}
