package com.greeningu.util;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class CodificadorBase64 {
	
	public static String codificarParaString(byte[] bytes){
		
		String resultado = null;
		Encoder encoder = Base64.getEncoder();
		
		resultado = encoder.encodeToString(bytes);
		
		return resultado;
	}
	
	public static byte[] decodificar(String base64){
		
		long tamanho = base64.length();
		
		byte[] bytes = new byte[(int) tamanho];
		
		Decoder decoder = Base64.getDecoder();
		
		bytes = decoder.decode(base64);
		
		return bytes;
	}

}
