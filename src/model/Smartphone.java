package model;

import java.util.ArrayList;

public class Smartphone {
	String nome;
	double preco;
	double bateria;
	String so;
	ArrayList<String> conectividades;
	
	public Smartphone(String nome, double preco, double bateria, String so, ArrayList<String> conectividades) {
		super();
		this.nome = nome;
		this.preco = preco;
		this.bateria = bateria;
		this.so = so;
		this.conectividades = conectividades;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public double getBateria() {
		return bateria;
	}

	public void setBateria(double bateria) {
		this.bateria = bateria;
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
