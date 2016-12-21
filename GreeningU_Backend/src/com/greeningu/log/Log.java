package com.greeningu.log;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Log {

	private static final String SUCESSO = " Operação realizada com sucesso ";
	private static final String FALHA = " Falha na operação ";
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy : hh:mm:ss");

	private static String contruirMensagemComData(String msg){

		String mensagem = "";
		Instant instante = Instant.now();
		LocalDateTime ldt = LocalDateTime.ofInstant(instante, ZoneId.systemDefault());
		String data = ldt.getDayOfMonth() + "/" + ldt.getMonthValue() + "/" + ldt.getYear();
		data += " " + ldt.getHour() + ":" + ldt.getMinute() + ":" + ldt.getSecond();
		mensagem += data + msg;
		return mensagem;

	}

	public static void erro(String classe, String metodo, Exception e){

		System.err.println(
			contruirMensagemComData(FALHA + classe + "." + metodo)
		);
		System.err.println("Mensagem: " + e.getMessage());
		for(int x = 0; x < 150; x++){
			System.err.print("=");
		}
		System.err.println("");

	}

	public static void erro(String classe, String metodo, Throwable e){

		System.err.println(
			contruirMensagemComData(FALHA + classe + "." + metodo)
		);
		System.err.println("Mensagem: " + e.getMessage());
		for(int x = 0; x < 150; x++){
			System.err.print("= ");
		}
		System.err.println("");
		
	}

	public static void sucesso(String classe, String metodo){
		
		System.out.println(
			contruirMensagemComData(SUCESSO + classe + "." + metodo)
		);
		for(int x = 0; x < 150; x++){
			System.out.print("=");
		}
		System.out.println("");
		
	}
}
