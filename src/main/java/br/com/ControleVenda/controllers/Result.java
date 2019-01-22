package br.com.ControleVenda.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import br.com.ControleVenda.dao.Pedido;

@Generated("org.jsonschema2pojo")
public class Result {

	@SerializedName("pedidos")
	@Expose
	private List<Pedido> pedidos = new ArrayList<Pedido>();
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}
	
	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
}