package mx.com.endtoend.genericCommonsFileds.utilities;
/*
 * Clase que contine las diferentes funciones o métodos para realizar la conversión
 * de numero a letra
 * 
 * */
public class WritingFunctions {
	
	/*
	 * Método que verifica que el monto tenga punto decimal,
	 * si no es así, se le asigna
	 * 
	 * @param literalAmount   monto ingresado por el usuario
	 * @return retorna una cadena con el monto con punto decimal
	 * */
	public static String formatAmount(String literalAmount) {

		String literalAmountFormatted = "";
		if (literalAmount.contains(".")) {
			literalAmountFormatted += literalAmount;
		} else {
			literalAmountFormatted = literalAmount + ".00";
		}
		return literalAmountFormatted;
	}
	
	/*
	 * Método que reemplaza el punto decimal del monto 
	 * por el signo de coma
	 * @Param literalAmount monto ingresado por el usuario
	 * */
	public static String replacePoints(String literalAmount) {
		String correctedAmount = literalAmount.replace(".", ",");
		return correctedAmount;
	}

	/*
	 * Método que guarda el monto ingresado en un arreglo para separar
	 * la parte entera de la decimal 
	 * @Param correctedAmount monto ingresado por el usuario
	 * @Return retorna un arreglo de longitud 2, donde se guardo el monto separado
	 * */
	public static String[] separateAmountUnit(String correctedAmount) {
		String[] parts = correctedAmount.split(",");
		Integer logicalAmmount = Integer.parseInt(parts[0]);
		String literalAmmount = Integer.toString((logicalAmmount));
		parts[0] = literalAmmount;
		return parts;
	}

	/*
	 * Método que asigna el valor de los cientos en letra
	 * y prosigue con las validaciones 
    */
	public static String calculateHundred(String[] amount, Integer position, Integer aux, Integer time) {
		String amountLetter = "";

		String hundreds[] = { "CIENTO", "DOSCIENTOS", "TRESCIENTOS", "CUATROCIENTOS", "QUINIENTOS", "SEISCIENTOS",
				"SETECIENTOS", "OCHOCIENTOS", "NOVECIENTOS" };

		Integer numberHundred = Integer.parseInt(amount[position]);
		Integer hundredBased = numberHundred / 100;
		String literalAmount = hundreds[hundredBased - 1];
		Integer modHundred = numberHundred % 100;

		if (modHundred != 0) {
			amountLetter += " " + literalAmount;

			amount[position] = Integer.toString(modHundred);
			amountLetter += classifyAmount(amount, 0, aux, time);
		} else {
			amountLetter += " " + literalAmount + " PESOS CON CERO CENTAVOS";
		}

		return amountLetter;

	}
	/*
	 * Método que asigna el valor de llas decenas en letra
	 * y prosigue con las validaciones 
    */
	public static String calculateTen(String[] amount, Integer position, Integer aux, Integer time) {
		String amountLetter = "";

		String tens[] = { "DIEZ", "VEINTE", "TREINTA", "CUARENTA", "CINCUENTA", "SESENTA", "SETENTA", "OCHENTA",
				"NOVENTA" };

		Integer tenNumber = Integer.parseInt(amount[position]);//90
		Integer modTen = tenNumber % 10; //0
		if (tenNumber >= 16 && tenNumber < 100 || modTen == 0) {

			Integer baseTen = tenNumber / 10;//9
			String literalAmount = tens[baseTen - 1];

			if (modTen != 0) {
				amountLetter += " " + literalAmount + " Y";

				amount[position] = Integer.toString(modTen);
				amountLetter += classifyAmount(amount, 0, aux, time);
			} else {
				amount[position] = amount[1];
		
				amountLetter += " " + literalAmount + " PESOS" +calculateCentsTens(amount, aux);
				//amountLetter += " " + literalAmount + " PESOS CON CERO CENTAVOS";
			}

		} else {
			aux=1;
        	amountLetter += " " + calculateSpecialCaseTen(modTen);
        	amountLetter += calculateCentsTens(amount, aux);
            
		}
		return amountLetter;
	}

	public static String calculateSpecialCaseTen(Integer modTen) {
		String tenSpecial[] = { "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE" };

		String literalAmount = tenSpecial[modTen - 1];
		return literalAmount;
	}

	/*
	 * Método que asigna el valor de las unidades en letra
	 * y prosigue con las validaciones 
    */
	public static String calculateUnit(String[] amount, Integer position, Integer aux, Integer time) {

		String units[] = { "CERO", "UN", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE" };
		aux = 1;
		String amountLetter = "";
		Integer unitNumber = Integer.parseInt(amount[position]);

		Integer modUnit = unitNumber % 1;

		if (modUnit != 0) {
			String literalAmount = units[unitNumber];
			amountLetter += " Y " + literalAmount + " PESOS";

			amount[position] = amount[1];
			System.out.println("***CENTAVOS " + amount[0]);
			if (modUnit == 0 && aux == 1) {
				amountLetter += calculateCentsTens(amount, aux);
			}
		} else {
			String literalAmount = units[unitNumber];
			amountLetter += " "+ literalAmount + " PESOS";
			if (modUnit == 0 && aux == 1) {

				amountLetter += calculateCentsTens(amount, aux);
			}
		}
		return amountLetter;

	}
	/*
	 * Método que asigna el valor de los unidades-miles en letra
	 * y prosigue con las validaciones 
    */
	public static String calculatesUnitThousands(String[] amount, Integer position, Integer aux, Integer time) {
		String thousandUnits[] = { "UN MIL", "DOS MIL", "TRES MIL", "CUATRO MIL", "CINCO MIL", "SEIS MIL", "SIETE MIL",
				"OCHO MIL", "NUEVE MIL" };

		String amountLetter = "";

		Integer numberThousandUnits = Integer.parseInt(amount[position]);
		Integer basethousandUnits = numberThousandUnits / 1000;
		String literalAmount = thousandUnits[basethousandUnits - 1];
		Integer modHundred = numberThousandUnits % 1000;
		if (time == 1) {

			if (modHundred != 0) {
				amountLetter = literalAmount;
				amount[position] = Integer.toString(modHundred);
				amountLetter += classifyAmount(amount, 0, aux, time);
			} else {
				amountLetter += literalAmount + " PESOS CON CERO CENTAVOS";
			}

		} else if (modHundred != 0) {
			amountLetter += " Y " + literalAmount;
			amount[position] = Integer.toString(modHundred);
			amountLetter += classifyAmount(amount, 0, aux, time);

		} else {

			amountLetter = " Y " + literalAmount + " CON CERO CENTAVOS";
		}

		return amountLetter;

	}
	/*
	 * Método que asigna el valor de los decenas-miles en letra
	 * y prosigue con las validaciones 
    */
	public static String calculateThousandsTen(String[] amount, Integer position, Integer aux, Integer time) {

		String thousandTens[] = { "DIEZ", "VEINTE", "TREINTA", "CUARENTA", "CINCUENTA", "SESENTA", "SETENTA", "OCHENTA",
				"NOVENTA" };
		String amountLetter = "";

		Integer numberThousandTens = Integer.parseInt(amount[position]);//20586
		Integer modThousandTen = numberThousandTens % 10000;//586
		if (numberThousandTens >= 10000 && numberThousandTens < 11000) {
			String literalAmount = "";

			Integer baseThousandTens = numberThousandTens / 10000;

			if (modThousandTen == 0) {
				literalAmount = thousandTens[baseThousandTens - 1] + " MIL PESOS";
				amountLetter = literalAmount + " CON CERO CENTAVOS";

			} else {
				literalAmount = thousandTens[baseThousandTens - 1];
				
				if(String.valueOf(modThousandTen).length() != 4) {
					amountLetter += " " + literalAmount + " MIL";
				}else {
					amountLetter += "" + literalAmount;
					}
					amount[position] = Integer.toString(modThousandTen);
					amountLetter += classifyAmount(amount, 0, aux, time);
				}
		}else {
		if (numberThousandTens >= 16000 && numberThousandTens < 100000 || modThousandTen == 0) {

			String literalAmount = "";

			Integer baseThousandTens = numberThousandTens / 10000;//20586/10000= 2

			if (modThousandTen == 0) {
				literalAmount = thousandTens[baseThousandTens - 1] + " MIL PESOS";
				amountLetter = literalAmount + " CON CERO CENTAVOS";

			} else {
				literalAmount = thousandTens[baseThousandTens - 1];
				
				if(String.valueOf(modThousandTen).length() != 4) {
					amountLetter += " " + literalAmount + " MIL";
					
				}else {
				amountLetter += " " + literalAmount;
				}
				amount[position] = Integer.toString(modThousandTen);
				amountLetter += classifyAmount(amount, 0, aux, time);
			}
		} else {
			amount[position] = Integer.toString(modThousandTen);// 1000
			amountLetter += calculateSpecialCaseThousandTen(amount, 0, aux, time);
		}
		}

		return amountLetter;
	}

	public static String calculateSpecialCaseThousandTen(String[] amount, Integer position, Integer aux, Integer time) {
		String mThousandTenSpecial[] = { "ONCE MIL", "DOCE MIL", "TRECE MIL", "CATORCE MIL", "QUINCE  MIL" };
		String amountLetter = "";
		Integer numberThousandTen = Integer.parseInt(amount[position]);
		Integer baseThousandTens = numberThousandTen / 1000;
		Integer modThousandTen = numberThousandTen % 1000;
		String literalAmount = mThousandTenSpecial[baseThousandTens - 1];

		if (modThousandTen != 0) {
			amountLetter = " " + literalAmount;
			amount[position] = Integer.toString(modThousandTen);// 0
			amountLetter += classifyAmount(amount, 0, aux, time);
		} else {
			amountLetter = " " + literalAmount + " PESOS CON CERO CENTAVOS";
		}

		return amountLetter;

	}
	
	/*
	 * Método que asigna el valor de los cientos-miles en letra
	 * y prosigue con las validaciones 
    */
	public static String calculateThousandsHundreds(String[] amount, Integer position, Integer aux, Integer time) {

		String milhundreds[] = { "CIENTO", "DOSCIENTOS", "TRESCIENTOS", "CUATROCIENTOS", "QUINIENTOS", "SEISCIENTOS",
				"SETECIENTOS", "OCHOCIENTOS", "NOVECIENTOS" };
		String amountLetter = "";
		Integer numberThousandHundreds = Integer.parseInt(amount[position]);
		Integer modThousandHundred = numberThousandHundreds % 100000;
		Integer baseThousandHundred = numberThousandHundreds / 100000;
		String literalAmount = "";
		if (modThousandHundred == 0 && baseThousandHundred == 1) {
			literalAmount = " CIEN MIL PESOS CON CERO CENTAVOS";

			amountLetter = literalAmount;

		}else if(String.valueOf(modThousandHundred).length() ==4){
			literalAmount = milhundreds[baseThousandHundred - 1];
			amountLetter = literalAmount;
			amount[position] = Integer.toString(modThousandHundred);
			amountLetter += classifyAmount(amount, 0, aux, time);
		}
		else if(String.valueOf(modThousandHundred).length() != 5) {
			literalAmount = " CIEN MIL";
			amountLetter += literalAmount;
			amount[position] = Integer.toString(modThousandHundred);
			amountLetter += classifyAmount(amount, 0, aux, time);
		}
		else if (modThousandHundred == 0) {
			amount[position] = amount[1];
			literalAmount = milhundreds[baseThousandHundred - 1] + " MIL PESOS";
			amountLetter = literalAmount;
			amountLetter += calculateCentsTens(amount, aux);

		} else {
			literalAmount = milhundreds[baseThousandHundred - 1];
			amountLetter += literalAmount;
			amount[position] = Integer.toString(modThousandHundred);// amount[0]= 1
			amountLetter += classifyAmount(amount, 0, aux, time);
		}

		return amountLetter;
	}

	public static String calculateCentsTens(String[] amount, Integer position) {

		String tens[] = { "DIEZ", "VEINTE", "TREINTA", "CUARENTA", "CINCUENTA", "SESENTA", "SETENTA", "OCHENTA",
				"NOVENTA" };
		String amountLetter = "";
		Integer len = amount[position].length();
		Integer numberCentsTens = Integer.parseInt(amount[position]);//16
		if (len == 1 && numberCentsTens != 0) {
			numberCentsTens = numberCentsTens * 10;
			Integer baseTen = numberCentsTens / 10;
			String literalAmount = tens[baseTen - 1];

			amountLetter += " CON " + literalAmount + " CENTAVOS";
		} else if (numberCentsTens == 0) {
			String literalAmount = " CON CERO CENTAVOS";
			amountLetter += literalAmount;
		} else {
			Integer modCentsTens = numberCentsTens % 10;//16%10= 6
			if (numberCentsTens >= 16 && numberCentsTens < 100 || modCentsTens == 0) {
				Integer baseTen = numberCentsTens / 10; //16/10= 1
				String literalAmount = tens[baseTen - 1];

				if (modCentsTens != 0) {
					amountLetter += " CON " + literalAmount;
					amount[position] = Integer.toString(modCentsTens);
					amountLetter += calculaCentavosUnidad(amount, position, 0);
				} else {
					amountLetter += " CON " + literalAmount + " CENTAVOS";
				}

			} else {
				if (numberCentsTens > 10 && numberCentsTens < 16) {
					amountLetter += calculateSpecialCaseCentsTen(modCentsTens);
				} else {
					Integer time = 1;
					amountLetter += calculaCentavosUnidad(amount, position, time);
				}
			}
		}
		return amountLetter;
	}

	public static String calculateSpecialCaseCentsTen(Integer modCentsTens) {
		String amountLetter = "";

		String tenSpecial[] = { "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE" };

		String literalAmount = tenSpecial[modCentsTens - 1];
		amountLetter = " CON " + literalAmount + " CENTAVOS ";
		return amountLetter;
	}

	private static String calculaCentavosUnidad(String[] amount, Integer position, Integer time) {

		String units[] = { "CERO", "UN", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE" };

		String amountLetter = "";
		if (Integer.parseInt(amount[position]) != 0) {

			Integer unitNumber = Integer.parseInt(amount[position]);
			Integer modUnit = unitNumber % 1;
			String literalAmount = units[unitNumber];//
			if (time == 1) {
				if (literalAmount.equals("UN")) {
					amountLetter = " CON " + literalAmount + " CENTAVO";
				}else {
				amountLetter = " CON " + literalAmount + " CENTAVOS";
				}
			} else {
				if (literalAmount.equals("UN")) {
					amountLetter = " Y " + literalAmount + " CENTAVO";
				}else {
				amountLetter = " Y " + literalAmount + " CENTAVOS";
				amount[position] = amount[1];
				}
			}

		}
		return amountLetter;
	}

	/*
	 * Método que se encarga de recibir el monto ya en un arreglo,
	 * el cual valida la longitud de cada uno para pasarlo a su respectivo metodo
	 * de conversión
	 * 
	 * @Param amount       arreglo que contiene el monto separado
	 * @Param position     posicion del arreglo que se evaluara primero
	 * @Param aux          valor que ayuda para hacer validaciones
	 * @Param time         valor que ayuda para saber si es la primera vez que entra al método
	 * */
	public static String classifyAmount(String[] amount, Integer position, Integer aux, Integer time) {

		time++;

		String amountLetter = "";

		Integer typeAmount = amount[position].length();
		switch (typeAmount) {
		case 1: {
			amountLetter = calculateUnit(amount, 0, aux, time);
			break;
		}
		case 2: {
			amountLetter = calculateTen(amount, 0, aux, time);
			break;
		}
		case 3: {
			amountLetter = calculateHundred(amount, 0, aux, time);
			break;
		}
		case 4: {
			amountLetter = calculatesUnitThousands(amount, 0, aux, time);
			break;
		}
		case 5: {
			amountLetter = calculateThousandsTen(amount, 0, aux, time);
			break;
		}
		case 6: {
			amountLetter = calculateThousandsHundreds(amount, 0, aux, time);

			break;
		}
		default: {
			System.out.println("Opcion incorrecta");
		}
		}
		return amountLetter;
	}

}
