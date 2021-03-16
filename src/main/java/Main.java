import service.SaleDocumentService;


public class Main {

	public static void main(String[] args)  {
		
//		List<CurrencyRate> currentCurrencyRates = CurrencyUtil.getCurrentCurrencyRates();
		new SaleDocumentService().insert();
	}

}
	