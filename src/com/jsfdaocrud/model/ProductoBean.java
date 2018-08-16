package com.jsfdaocrud.model;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.jsfdapcrud.operations.CrudOperations;



@ManagedBean(name="productoBean")
@RequestScoped
public class ProductoBean {
	
	private int idProducto;
	private String nombreProducto;
	private double precioProducto;
	
	public ArrayList productoListFromDB;
	
	//Get & set methods
	public void setIdProducto(int idProducto)
	{
		this.idProducto=idProducto;
	}
	public int getIdProducto()
	{
		return idProducto;
	}
	public void setNombreProducto(String nombreProducto)
	{
		this.nombreProducto=nombreProducto;
	}
	public String getNombreProducto()
	{
		return nombreProducto;
	}
	public void setPrecioProducto(double precioProducto)
	{
		this.precioProducto=precioProducto;
	}
	public double getPrecioProducto()
	{
		return precioProducto;
	}
	@PostConstruct
	public void init()
	{
		productoListFromDB=CrudOperations.getProductoListFromDB();
	}
	public ArrayList productoList()
	{
		return productoListFromDB;
	}
	public String guardarDetallesProducto(ProductoBean objProducto)
	{
		return CrudOperations.guardarProductoDetallesInDB(objProducto);
	}
	public String editarProducto(int idProducto)
	{
		System.out.println("Registro id"+idProducto);
		return CrudOperations.editarProductGuardaInDB(idProducto);
	}
	public String actualizarProducto(ProductoBean objProducto)
	{
		return CrudOperations.actualizarProductInDB(objProducto);
	}
	public String eliminarProducto(int idProducto)
	{
		return CrudOperations.eliminarProductInDB(idProducto);
	}
}
