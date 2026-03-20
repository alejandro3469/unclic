package mx.com.endtoend.genericCommonsFileds.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {

	private final Logger LOG = LoggerFactory.getLogger(DateUtil.class);

	public static String transformDateBooleana(String date) {
		int year = Integer.parseInt("20" + date.substring(1, 3));
		int days = Integer.parseInt(date.substring(3));

		Calendar c1 = GregorianCalendar.getInstance();
		c1.clear();
		c1.set(Calendar.YEAR, year);
		c1.set(Calendar.DAY_OF_YEAR, days);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		return sdf.format(c1.getTime());
	}

	public static String transformDateJuliana(String date1) {

		StringBuilder builder = new StringBuilder().append("1");

		try {

			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(date1.substring(0, 10));

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			String year = Integer.toString(cal.get(Calendar.YEAR));
			int days = cal.get(Calendar.DAY_OF_YEAR);

			Formatter fmt = new Formatter();
			fmt.format("%03d", days);

			builder.append(year.substring(2)).append(fmt);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return builder.toString();

	}

	public static boolean isBeforeToCurrentDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date today = c.getTime();
		return date.compareTo(today) < 0;
	}

	@SuppressWarnings("deprecation")
	public Long getCurrentJulianDate() {
		Long julianDate = 0L;
		StringBuilder sb = new StringBuilder();
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTimeInMillis(System.currentTimeMillis());
		String date = sb.append("1").append(Integer.toString(currentCalendar.get(Calendar.YEAR)).substring(2, 4))
				.append(String.format("%03d", currentCalendar.get(Calendar.DAY_OF_YEAR))).toString();
		try {
			julianDate = new Long(date);
			return julianDate;
		} catch (NumberFormatException e) {
			LOG.error("METHOD: getCurrentJulianDate()");
			LOG.error("ERROR AL PARSEAR UN STRING A INTEGER", e.getMessage());
			return null;
		}
	}

	public Long getCurrentTimeJulianDate() {

		try {

			Calendar currentCalendar = Calendar.getInstance();
			currentCalendar.setTimeInMillis(System.currentTimeMillis());

			String hh = Integer.toString(currentCalendar.get(Calendar.HOUR_OF_DAY));
			String mm = Integer.toString(currentCalendar.get(Calendar.MINUTE));
			String ss = Integer.toString(currentCalendar.get(Calendar.SECOND));

			while (hh.length() < 2) {
				hh = "0" + hh;
			}

			while (mm.length() < 2) {
				mm = "0" + mm;
			}

			while (ss.length() < 2) {
				ss = "0" + ss;
			}

			return Long.parseLong(hh + mm + ss);

		} catch (NumberFormatException e) {
			LOG.error("METHOD: getCurrentJulianDate()");
			LOG.error("ERROR AL PARSEAR UN STRING A INTEGER", e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public Long convertDateToJulianDate(Date dateInput) {
		Long julianDate = 0L;
		StringBuilder sb = new StringBuilder();
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(dateInput);
		String date = sb.append("1").append(Integer.toString(currentCalendar.get(Calendar.YEAR)).substring(2, 4))
				.append(String.format("%03d", currentCalendar.get(Calendar.DAY_OF_YEAR))).toString();
		try {
			julianDate = new Long(date);
			return julianDate;
		} catch (NumberFormatException e) {
			LOG.error("METHOD: getCurrentJulianDate()");
			LOG.error("ERROR AL PARSEAR UN STRING A INTEGER", e.getMessage());
			return null;
		}
	}

}
