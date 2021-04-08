package pl.streamsoft.dto;

import java.time.LocalDate;

public class Period {
    public LocalDate start;
    public LocalDate end;
    
    public Period(LocalDate start, LocalDate end) {
	super();
	this.start = start;
	this.end = end;
    }

    public Period() {
    }

    public LocalDate getStart() {
	return start;
    }

    public void setStart(LocalDate start) {
	this.start = start;
    }

    public LocalDate getEnd() {
	return end;
    }

    public void setEnd(LocalDate end) {
	this.end = end;
    }

}