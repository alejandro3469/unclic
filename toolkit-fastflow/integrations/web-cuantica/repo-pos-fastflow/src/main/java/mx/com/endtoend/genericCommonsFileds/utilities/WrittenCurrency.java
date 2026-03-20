package mx.com.endtoend.genericCommonsFileds.utilities;

/*
 * Clase que tiene un método llamado enterNumber()
 * 
 * el cual recibe por parametro el monto o cantidad para convertirla a letra
 * @param amount  Cantidad a convertir a letra
 * @return la cantidad o monto en letra
 * */
public class WrittenCurrency {
	String finalAmount = "";
	
	/*
	 * Método que recibe el monto o cantidad y retorna la cantidad en letra
	 * 
	 * */
	public String enterNumber(String amount) {
		Integer times=0;
        String formattedAmount = WritingFunctions.formatAmount(amount);
        String correctedAmount = WritingFunctions.replacePoints(formattedAmount);
        String[] parts = WritingFunctions.separateAmountUnit(correctedAmount);
        Integer aux = 0;
        
        finalAmount = WritingFunctions.classifyAmount(parts,0, aux,times);
		
		return finalAmount.trim();
	}

}
