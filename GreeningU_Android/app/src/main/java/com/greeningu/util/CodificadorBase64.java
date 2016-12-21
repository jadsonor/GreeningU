package com.greeningu.util;


import android.util.Base64;

public class CodificadorBase64 {
	
	public static String codificarParaString(byte[] bytes){
		
		String resultado = null;

		resultado = Base64.encodeToString(bytes,0);
		
		return resultado;
	}
	
	public static byte[] decodificar(String base64){
		
		long tamanho = base64.length();
		
		byte[] bytes = new byte[(int) tamanho];
		
		bytes = Base64.decode(base64,0);
		
		return bytes;
	}

}
