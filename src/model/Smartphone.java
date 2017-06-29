package model;

import java.util.ArrayList;

public class Smartphone {
	String nome;
	String precoRange;
	String bateriaRange;
	String so;
	ArrayList<String> conectividades;
	
	public Smartphone(String nome, String precoRange, String bateriaRange, String so, ArrayList<String> conectividades) {
		super();
		this.nome = nome;
		this.precoRange = precoRange;
		this.bateriaRange = bateriaRange;
		this.so = so;
		this.conectividades = conectividades;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPrecoRange() {
		return precoRange;
	}

	public void setPrecoRange(String precoRange) {
		this.precoRange = precoRange;
	}

	public String getBateriaRange() {
		return bateriaRange;
	}

	public void setBateriaRange(String bateriaRange) {
		this.bateriaRange = bateriaRange;
	}

	public String getSo() {
		return so;
	}

	public void setSo(String so) {
		this.so = so;
	}

	public ArrayList<String> getConectividades() {
		return conectividades;
	}

	public void setConectividades(ArrayList<String> conectividades) {
		this.conectividades = conectividades;
	}
}
