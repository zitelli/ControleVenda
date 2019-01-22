package br.com.ControleVenda.dao;

import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
@Entity
@Table(name="pedido")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	//@SerializedName("id")
	@Expose
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@SerializedName("controle")
	@Expose
	@Column(name="controle")
	private int ctr;
    
	@SerializedName("data")
    @Expose
	@Column(name="data")
	private String data;

	@SerializedName("produto")
    @Expose
	@Column(name="produto")
	private String prod;

	@SerializedName("valorunitario")
    @Expose
	@Column(name="valorunitario")
	private double vlr;

	@SerializedName("quantidade")
    @Expose
	@Column(name="quantidade")
    private int qtd;

	@SerializedName("cliente")
    @Expose
	@Column(name="codigocliente")
	private String cod;

	@Column(name="total")
	private double total;	
	
	public int getId() {
		return id;
	}
    
    public int getCtr() {
		return ctr;
	}

	public void setCtr(int ctr) {
		this.ctr = ctr;
	}
    
    public String getData() {
		return data;
	}

    public void setData(String dat) {
		this.data = dat;
	}

    public String getProd() {
		return prod;
	}

    public void setProd(String prd) {
		this.prod = prd;
	}

    public double getVlr() {
		return vlr;
	}

	public void setVlr(double vlr) {
		this.vlr = vlr;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}
	
	public void setTotal() {
		double dtot;
		dtot = this.qtd * this.vlr;
		if (this.qtd >= 10) {
			dtot = dtot * (1 - 0.1);
		} else if (this.qtd > 5) {
			dtot = dtot * (1 - 0.05);
		}
		this.total = dtot;
	}

	public double getTotal() {
		return this.total;
	}
	
	public void MontaPedido(int ctr, String dat, String prod, double vlr, int qtd, String cod ) {
		this.setCtr(ctr);
		this.setData(dat);
		this.setProd(prod);
		this.setVlr(vlr);
		this.setQtd(qtd);
		this.setCod(cod);
	}
	
	@Override
	public String toString() {
		return "Pedido " + id + ": [controle = " + ctr + ", data = " + data + ", produto = " + prod + ", valor unitário = " + vlr + ", quantidade = " + qtd + ", cliente = "
				+ cod  + ", total = " + total + "]";
	}

}
