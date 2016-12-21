package com.greeningu.dao;

import java.util.List;
import java.util.Map;

public interface CRUD {
	
	final String METODO_BUSCAR = "buscar(Integer id)";
	final String METODO_LISTAR = "listar(Integer id)";
	final String METODO_SALVAR = "salvar(Object objeto)";
	final String METODO_SALVAR_C_PARAM = "salvar(Object objeto, Map<String, Object> campos)";
	final String METODO_ATUALIZAR = "atualizar(Object objeto)";
	final String METODO_ATUALIZAR_C_PARAM = "atualizar(Object objeto, Map<String, Object> campos)";
	final String METODO_EXCLUIR = "excluir(Object objeto)";
	
	Object buscar(Integer id);
	
	List<Object> listar();
	
	int salvar(Object objeto);
	
	int salvar(Object objeto, Map<String, Object> campos);
	
	int atualizar(Object objeto);
	
	int atualizar(Object objeto, Map<String, Object> campos);
	
	int excluir(Object objeto);
	
}
