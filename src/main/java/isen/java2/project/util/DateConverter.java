package isen.java2.project.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter {
	private static final String DATE_PATTERN = "dd MM yyyy";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

	public static String convertDate(LocalDate birthdate) {
		// TODO Auto-generated method stub
		return birthdate.format(DATE_FORMATTER);
	}
}
