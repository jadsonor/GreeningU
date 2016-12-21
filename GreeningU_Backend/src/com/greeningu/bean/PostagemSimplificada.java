package com.greeningu.bean;

import java.io.Serializable;
import java.util.Date;

public class PostagemSimplificada implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2495223781850496807L;
	private Integer id;
	private String titulo;
	private Date dataPostagem;
	private String nomeUsuario;
	
	public PostagemSimplificada(){}
	
	public PostagemSimplificada(String titulo, Date dataPostagem,
			String nomeUsuario) {
		super();
		this.titulo = titulo;
		this.dataPostagem = dataPostagem;
		this.nomeUsuario = nomeUsuario;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getDataPostagem() {
		return dataPostagem;
	}

	public void setDataPostagem(Date dataPostagem) {
		this.dataPostagem = dataPostagem;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataPostagem == null) ? 0 : dataPostagem.hashCode());
		result = prime * result
				+ ((nomeUsuario == null) ? 0 : nomeUsuario.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostagemSimplificada other = (PostagemSimplificada) obj;
		if (dataPostagem == null) {
			if (other.dataPostagem != null)
				return false;
		} else if (!dataPostagem.equals(other.dataPostagem))
			return false;
		if (nomeUsuario == null) {
			if (other.nomeUsuario != null)
				return false;
		} else if (!nomeUsuario.equals(other.nomeUsuario))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

}
