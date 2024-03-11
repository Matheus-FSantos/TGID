package io.github.matheusfsantos.tgid.model.utils.format;

public class IdentifierDocumentFormat {

	public static String formatarCNPJ(String cnpj) {
        return cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." +
               cnpj.substring(5, 8) + "/" + cnpj.substring(8, 12) + "-" + cnpj.substring(12);
    }
	
	public static String formatarCPF(String cpf) {
		return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." +
               cpf.substring(6, 9) + "-" + cpf.substring(9);
    }
	
}
