package com.jsfdapcrud.operations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import javax.faces.context.FacesContext;
import com.jsfdaocrud.model.ProductoBean;

//This class contains the DAO
public class CrudOperations {
	//Objects connections
	public static Connection conn;
	public static PreparedStatement pstmt;
	public static Statement stmt;
	public static ResultSet rs;
	
	//Making the connection with db with a method 
	public static Connection getConnection()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/hugo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true&useSSL=false",
					user="root",
					password="root";
			conn=DriverManager.getConnection(url, user,password);
		}
		catch(Exception sqlException){
			sqlException.printStackTrace();
		}
		return conn;
	}
	public static ArrayList<ProductoBean> getProductoListFromDB()
	{
		ArrayList<ProductoBean> productList = new ArrayList<ProductoBean>();
		try {
			stmt=getConnection().createStatement();
			rs=stmt.executeQuery("select * from producto");
			while(rs.next()) {
				ProductoBean objProducto = new ProductoBean();
				objProducto.setIdProducto(rs.getInt("idProducto"));
				objProducto.setNombreProducto(rs.getString("NombreProducto"));
				objProducto.setPrecioProducto(rs.getDouble("Precio"));
				
				productList.add(objProducto);
			}
			System.out.println("Total de registros Extraidos"+productList.size());
			conn.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return productList;
	}
	
	public static String guardarProductoDetallesInDB(ProductoBean objProducto)
	{
		int resultadoGuardado=0;
		String resultadoNavegacion="";
		try {
			pstmt=getConnection().prepareStatement("insert into producto (idProducto,NombreProducto,Precio) values(?,?,?)");
			pstmt.setInt(1, objProducto.getIdProducto());
			pstmt.setString(2, objProducto.getNombreProducto());
			pstmt.setDouble(3, objProducto.getPrecioProducto());
			
			//Check if the result has been recorded on the database
			resultadoGuardado=pstmt.executeUpdate();
			conn.close();
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		}
		if(resultadoGuardado !=0) {
			resultadoNavegacion="index.xhtml?faces-redirect=true";
		}else {
			resultadoNavegacion="agregarProducto.xhtml?faces-redirect=true";
		}
		return resultadoNavegacion; 
	}
	
	public static String editarProductGuardaInDB(int idProducto)
	{
		ProductoBean editarRegistro = null;
		System.out.println("editarRegistroProductoInDB() : Producto ID: "+ idProducto);
		Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		
		try {
			stmt=getConnection().createStatement();
			rs=stmt.executeQuery("select * from producto where idProducto="+idProducto);
			if(rs != null) {
				rs.next();
				editarRegistro=new ProductoBean();
				editarRegistro.setIdProducto(rs.getInt("idProducto"));
				editarRegistro.setNombreProducto(rs.getString("NombreProducto"));
				editarRegistro.setPrecioProducto(rs.getDouble("Precio"));
			}
			sessionMapObj.put("editarRegistroObj", editarRegistro);
			conn.close();
		}catch(Exception sqlException) {
			sqlException.printStackTrace();
		}
		
		return "/editarProducto.xhtml?faces-redirect=true";
	}
	
	public static String actualizarProductInDB(ProductoBean objProducto)
	{
		try {
			pstmt = getConnection().prepareStatement("update producto set idProducto=?, NombreProducto=?, Precio=? where idProducto=?");
			pstmt.setInt(1, objProducto.getIdProducto());
			pstmt.setString(2, objProducto.getNombreProducto());
			pstmt.setDouble(3, objProducto.getPrecioProducto());
			pstmt.setInt(4, objProducto.getIdProducto());
			pstmt.executeUpdate();
			conn.close();
		}catch(Exception sqlException) {
			sqlException.printStackTrace();
		}
		return "/index.xhtml?faces-redirect=true";
	}
	public static String eliminarProductInDB(int idProducto)
	{
		System.out.println("eliminarProductInDB() : IdProducto:"+idProducto);
 
		try {
			pstmt = getConnection().prepareStatement("delete from producto where idProducto = "+idProducto);
			pstmt.executeUpdate();
			conn.close();
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		}
		return "/index.xhtml?faces-redirect=true";
	}

}
