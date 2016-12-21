package com.greeningu.bean;

import java.io.Serializable;
import java.util.Date;


public class Comentario implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1330738108388426158L;
	private Integer id;
	private Date data;
	private String texto;
	private Integer idUsuario;
	private Integer numComentario;
	private Integer idPostagem;
	
	public Comentario(){}

	public Comentario(Integer id, Date data, String texto, Integer idUsuario,
			Integer numComentario, Integer idPostagem) {
		super();
		this.id = id;
		this.data = data;
		this.texto = texto;
		this.idUsuario = idUsuario;
		this.numComentario = numComentario;
		this.idPostagem = idPostagem;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getNumComentario() {
		return numComentario;
	}

	public void setNumComentario(Integer numComentario) {
		this.numComentario = numComentario;
	}

	public Integer getIdPostagem() {
		return idPostagem;
	}

	public void setIdPostagem(Integer idPostagem) {
		this.idPostagem = idPostagem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((idPostagem == null) ? 0 : idPostagem.hashCode());
		result = prime * result
				+ ((idUsuario == null) ? 0 : idUsuario.hashCode());
		result = prime * result
				+ ((numComentario == null) ? 0 : numComentario.hashCode());
		result = prime * result + ((texto == null) ? 0 : texto.hashCode());
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
		Comentario other = (Comentario) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idPostagem == null) {
			if (other.idPostagem != null)
				return false;
		} else if (!idPostagem.equals(other.idPostagem))
			return false;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		if (numComentario == null) {
			if (other.numComentario != null)
				return false;
		} else if (!numComentario.equals(other.numComentario))
			return false;
		if (texto == null) {
			if (other.texto != null)
				return false;
		} else if (!texto.equals(other.texto))
			return false;
		return true;
	}
}
