package model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "currencyRate")
public class CurrencyRate {
	@Id
	@GeneratedValue
	private long id;
	private String currencyName;
	private CurrencyCode code;
	private BigDecimal rate;
	private LocalDate date;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public CurrencyCode getCode() {
		return code;
	}
	public void setCode(CurrencyCode code) {
		this.code = code;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "id=" + id + ", currencyName=" + currencyName + ", code=" + code + ", rate=" + rate
				+ ", date=" + date;
	}
	
	
}
