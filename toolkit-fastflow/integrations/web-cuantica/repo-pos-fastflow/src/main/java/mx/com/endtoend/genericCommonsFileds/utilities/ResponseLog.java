package mx.com.endtoend.genericCommonsFileds.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ResponseLog {

	public static String todayUserAction() {

		String pattern = "MM/dd/yyyy";
		DateFormat df = new SimpleDateFormat(pattern);
		Date today = Calendar.getInstance().getTime();
		String todayAsString = df.format(today);

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();

		int idAction = (int) (Math.random() * 1000 + 1);

		String action = String.valueOf(idAction);

		return todayAsString + "-" + username + "-" + action;

	}

	public static String generateIdOperation(String companyCode, String branchCode, String module) {

		try {
			String pattern = "MM/dd/yyyy";
			DateFormat df = new SimpleDateFormat(pattern);
			Date today = Calendar.getInstance().getTime();
			String todayAsString = df.format(today);

			Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
			String username = loggedInUser.getName();

			int idAction = (int) (Math.random() * 1000 + 1);
			String action = String.valueOf(idAction);

			return companyCode + "-" + branchCode + "-" + module + "-" + todayAsString + "-" + username + "-" + action;

		} catch (Exception e) {
			int idAction = (int) (Math.random() * 1000 + 1);
			return "ID-OPERATION" + idAction;
		}

	}

	public static String generateIdOperation(String companyCode, String module) {

		String pattern = "MM/dd/yyyy";
		DateFormat df = new SimpleDateFormat(pattern);
		Date today = Calendar.getInstance().getTime();
		String todayAsString = df.format(today);

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();

		int idAction = (int) (Math.random() * 1000 + 1);
		String action = String.valueOf(idAction);

		return companyCode + "-" + module + "-" + todayAsString + "-" + username + "-" + action;

	}

	public static String generateIdOperation(String module) {

		String pattern = "MM/dd/yyyy";
		DateFormat df = new SimpleDateFormat(pattern);
		Date today = Calendar.getInstance().getTime();
		String todayAsString = df.format(today);

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();

		int idAction = (int) (Math.random() * 1000 + 1);
		String action = String.valueOf(idAction);

		return "ETE" + "-" + module + "-" + todayAsString + "-" + username + "-" + action;
	}
}
