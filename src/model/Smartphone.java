package model;

import java.util.ArrayList;

public class Smartphone {
	String nome;
	String precoRange;
	double preco;
	String bateriaRange;
	double bateria;
	String so;
	ArrayList<String> conectividades;

	public Smartphone(String nome, String precoRange, String bateriaRange, String so, ArrayList<String> conectividades) {
		this.nome = nome;
		this.precoRange = precoRange;
		this.preco = 0.0;
		this.bateriaRange = bateriaRange;
		this.bateria = 0.0;
		this.so = so;
		this.conectividades = conectividades;
	}

	public Smartphone(String nome, double preco, double bateria, String so, ArrayList<String> conectividades) {
		this.nome = nome;
		this.precoRange = "";
		this.preco = preco;
		this.bateriaRange = "";
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

	public String getPrecoRange() {
		if(!this.precoRange.equals("")) {
			return "Preco[" + this.precoRange + "]";
		}

		String precoRange = "invalido";
		for (int i = 0; i < 4000; i+=300)
		{
			if(preco >= i + 1 && preco <= i + 300) {
				precoRange = "Preco[" + (i+1) + "-" + (i+300) +"]";
			}
		}

		return precoRange;
	}

	public void setPrecoRange(String precoRange) {
		this.precoRange = precoRange;
	}

	public String getBateriaRange() {
		if(!this.bateriaRange.equals("")) {
			return "Preco[" + this.bateriaRange + "]";
		}

		String bateriaRange = "invalido";

		for (int i = 000; i < 5000; i+=300)
		{
			if(bateria >= i + 1 && bateria <= i + 300) {
				bateriaRange = "Bateria[" + (i+1) + "-" + (i+300) +"]";
			}
		}

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

	public String toString(){
		return nome + " " + preco + " " + bateria + " " + so;
	}
}
