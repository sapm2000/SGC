/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author rma
 */
public class CerrarMes extends ConexionBD {

    private int id;
    private int mes_cierre;
    private int año_cierre;
    private double monto;
    private int id_unidad;
    private int id_gasto;
    private String id_condominio;
    private double alicuota;
    private String estado;
    private int meses_res;
    private int meses_deuda;
    private String nom_concepto;
    private String cedula;
    private String nom_proveedor;
    private String tipo;
    private double saldo_restante;
    private String tipo_gasto;

    public int getId_unidad() {
        return id_unidad;
    }

    public void setId_unidad(int id_unidad) {
        this.id_unidad = id_unidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo_gasto() {
        return tipo_gasto;
    }

    public void setTipo_gasto(String tipo_gasto) {
        this.tipo_gasto = tipo_gasto;
    }
    
    
    
    

    public double getSaldo_restante() {
        return saldo_restante;
    }

    public void setSaldo_restante(double saldo_restante) {
        this.saldo_restante = saldo_restante;
    }

    public int getMeses_deuda() {
        return meses_deuda;
    }

    public void setMeses_deuda(int meses_deuda) {
        this.meses_deuda = meses_deuda;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getMeses_res() {
        return meses_res;
    }

    public void setMeses_res(int meses_res) {
        this.meses_res = meses_res;
    }

    public int getMes_cierre() {
        return mes_cierre;
    }

    public void setMes_cierre(int mes_cierre) {
        this.mes_cierre = mes_cierre;
    }

    public int getAño_cierre() {
        return año_cierre;
    }

    public void setAño_cierre(int año_cierre) {
        this.año_cierre = año_cierre;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

   
    public int getId_gasto() {
        return id_gasto;
    }

    public void setId_gasto(int id_gasto) {
        this.id_gasto = id_gasto;
    }

    public String getId_condominio() {
        return id_condominio;
    }

    public void setId_condominio(String id_condominio) {
        this.id_condominio = id_condominio;
    }

    public double getAlicuota() {
        return alicuota;
    }

    public void setAlicuota(double alicuota) {
        this.alicuota = alicuota;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNom_concepto() {
        return nom_concepto;
    }

    public void setNom_concepto(String nom_concepto) {
        this.nom_concepto = nom_concepto;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNom_proveedor() {
        return nom_proveedor;
    }

    public void setNom_proveedor(String nom_proveedor) {
        this.nom_proveedor = nom_proveedor;
    }

    public boolean registrarGasto(CerrarMes modc) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO detalle_pagos(mes, anio, monto, id_gasto, id_factura, tipo_gasto) VALUES (?, ?, ?, ?, ?, ?);";

        try {

            ps = con.prepareStatement(sql);
           
            ps.setInt(1, getMes_cierre());
            ps.setInt(2, getAño_cierre());
            ps.setDouble(3, getMonto());
            ps.setInt(4, getId_gasto());
            ps.setInt(5, getId());
            ps.setString(6, getTipo_gasto());
            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean cerrar_mes(CerrarMes modc) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO cierre_de_mes(mes, anio,id_condominio) VALUES (?, ?,?);";

        try {

            ps = con.prepareStatement(sql);

            ps.setInt(1, getMes_cierre());
            ps.setInt(2, getAño_cierre());
            ps.setString(3, getId_condominio());

            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean registrar_cuota(CerrarMes modc) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO detalle_pagos(id_gasto, mes, anio, id_factura, monto, tipo_gasto) VALUES (?, ?, ?, ?, ?, ?);";

        try {

            ps = con.prepareStatement(sql);
           
            ps.setInt(1, getId_gasto());
            ps.setInt(2, getMes_cierre());
            ps.setInt(3, getAño_cierre());
            ps.setInt(4, getId());
            ps.setDouble(5, getMonto());
            ps.setString(6, getTipo_gasto());

            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean buscartotal(CerrarMes modc) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT id_factura, sum(monto) as total FROM detalle_pagos where id_factura=? and mes=? and anio=? and tipo_gasto=? group by id_factura;";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, modc.getId());
            ps.setInt(2, modc.getMes_cierre());
            ps.setInt(3, modc.getAño_cierre());
            ps.setString(4, modc.getTipo_gasto());
        
            rs = ps.executeQuery();
            if (rs.next()) {

                modc.setMonto(rs.getDouble("total"));

                return true;
            }

            return false;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    

    

    
     public boolean buscaultimo(CerrarMes modc) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT max(id) as id FROM detalle_total;";

        try {

            ps = con.prepareStatement(sql);
         
            rs = ps.executeQuery();
            if (rs.next()) {

                modc.setId(rs.getInt("id"));

                return true;
            }

            return false;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean guardarsancionpro(CerrarMes modc) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO detalle_pagos(id_factura, id_gasto, mes, anio, monto, tipo_gasto) VALUES (?, ?, ?, ?, ?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            ps.setInt(2, getId_gasto());
            ps.setInt(3, getMes_cierre());
            ps.setInt(4, getAño_cierre());
          
            ps.setDouble(5, getMonto());
             ps.setString(6, getTipo_gasto());

            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean registrar_interes(CerrarMes modc) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO detalle_pagos(id_factura, id_gasto, mes, anio, monto, tipo_gasto) VALUES (?, ?, ?, ?, ?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            ps.setInt(2, getId_gasto());
            ps.setInt(3, getMes_cierre());
            ps.setInt(4, getAño_cierre());
          
            ps.setDouble(5, getMonto());
              ps.setString(6, getTipo_gasto());

            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean guardartotal(CerrarMes modc) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO detalle_total(id_unidad, monto, mes, anio, alicuota, estado, saldo_restante) VALUES (?, ?, ?, ?, ?, ?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, getId_unidad());
            ps.setDouble(2, getMonto());
            ps.setInt(3, getMes_cierre());
            ps.setInt(4, getAño_cierre());
            ps.setDouble(5, getAlicuota());
            ps.setString(6, getEstado());
          
            ps.setDouble(7, getMonto());

            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }
    
     public boolean actualizartotalcierre(CerrarMes modc) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE detalle_total SET monto=?, saldo_restante=? WHERE id=?";

        try {

            ps = con.prepareStatement(sql);
            
          
            ps.setDouble(1, getMonto());
            ps.setDouble(2, getMonto());
            ps.setInt(3, getId());

            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean actualizarGasto(CerrarMes modc) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE gasto_comun SET estado=?	WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getEstado());

            ps.setInt(2, getId_gasto());
            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean actualizar_cuota(CerrarMes modc) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE cuotas_especiales SET estado=?, n_mese_restante=? WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getEstado());

            ps.setInt(2, getMeses_res());
            ps.setInt(3, getId_gasto());
            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean actualizarSancion(CerrarMes modc) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE sancion SET estado=? WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, getEstado());

            ps.setInt(2, getId_gasto());
            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean actualizartotal(CerrarMes modc) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE detalle_total SET saldo_restante=?, estado=? WHERE id=?;";

        try {

            ps = con.prepareStatement(sql);
            ps.setDouble(1, getSaldo_restante());
            ps.setString(2, getEstado());

            ps.setInt(3, getId_gasto());
            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public ArrayList<CerrarMes> listar() {
        ArrayList listaCierremes = new ArrayList();
        CerrarMes modc;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id, mes, anio FROM cierre_de_mes where id_condominio=?;";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, getId_condominio());
            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.setId_gasto(rs.getInt(1));
                modc.setMes_cierre(rs.getInt(2));
                modc.setAño_cierre(rs.getInt(3));

                listaCierremes.add(modc);
            }
        } catch (SQLException e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaCierremes;
    }

    public boolean buscarfechas(CerrarMes modc) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT id_condominio FROM cierre_de_mes where mes=? and anio=? and id_condominio=?;";

        try {

            ps = con.prepareStatement(sql);

            ps.setInt(1, modc.getMes_cierre());
            ps.setInt(2, modc.getAño_cierre());
            ps.setString(3, modc.getId_condominio());
            rs = ps.executeQuery();
            if (rs.next()) {

                modc.setId_condominio(rs.getString("id_condominio"));

                return true;
            }

            return false;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public ArrayList<CerrarMes> listarpagos() {
        ArrayList listaCierremes = new ArrayList();
        CerrarMes modc;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id, monto, mes, anio, alicuota, estado, saldo_restante FROM detalle_total where id_unidad=? order by anio,mes";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId_unidad());
          

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.setId_gasto(rs.getInt(1));
                modc.setMonto(rs.getDouble(2));
                modc.setMes_cierre(rs.getInt(3));
                modc.setAño_cierre(rs.getInt(4));
                modc.setAlicuota(rs.getDouble(5));
                modc.setEstado(rs.getString(6));
                modc.setSaldo_restante(rs.getDouble(7));

                listaCierremes.add(modc);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaCierremes;
    }

    public ArrayList<CerrarMes> listarpagospendientes() {
        ArrayList listaCierremes = new ArrayList();
        CerrarMes modc;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id, monto, mes, anio, alicuota, estado, saldo_restante FROM detalle_total where id_unidad=? and estado='Pendiente de Pago' order by anio,mes";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId_unidad());
           

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.setId_gasto(rs.getInt(1));
                modc.setMonto(rs.getDouble(2));
                modc.setMes_cierre(rs.getInt(3));
                modc.setAño_cierre(rs.getInt(4));
                modc.setAlicuota(rs.getDouble(5));
                modc.setEstado(rs.getString(6));
                modc.setSaldo_restante(rs.getDouble(7));

                listaCierremes.add(modc);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaCierremes;
    }

    public ArrayList<CerrarMes> listarpagospagados() {
        ArrayList listaCierremes = new ArrayList();
        CerrarMes modc;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id, monto, mes, anio, alicuota, estado, saldo_restante FROM detalle_total where id_unidad=? and id_condominio=? and estado='Pagado' order by anio,mes";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId_unidad());
            

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.setId_gasto(rs.getInt(1));
                modc.setMonto(rs.getDouble(2));
                modc.setMes_cierre(rs.getInt(3));
                modc.setAño_cierre(rs.getInt(4));
                modc.setAlicuota(rs.getDouble(5));
                modc.setEstado(rs.getString(6));
                modc.setSaldo_restante(rs.getDouble(7));

                listaCierremes.add(modc);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listaCierremes;
    }

    public boolean buscarmesesdedeuda(CerrarMes modc) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT count(estado) as estado FROM public.detalle_total where estado='Pendiente de Pago' and id_unidad=?";

        try {

            ps = con.prepareStatement(sql);

            ps.setInt(1, getId_unidad());
          
            rs = ps.executeQuery();
            if (rs.next()) {

                modc.setMeses_deuda(rs.getInt("estado"));

                return true;
            }

            return false;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public ArrayList<CerrarMes> listardetallesgastos() {
        ArrayList listadetallegasto = new ArrayList();
        CerrarMes modc;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT concepto_gasto.nom_concepto, detalle_pagos.monto, proveedores.cedula, proveedores.nombre, gasto_comun.tipo  FROM detalle_pagos INNER join gasto_comun on detalle_pagos.id_gasto = gasto_comun.id and tipo_gasto='Gasto comun' INNER join concepto_gasto on concepto_gasto.id = gasto_comun.id_concepto INNER join proveedores on gasto_comun.id_proveedor=proveedores.cedula where detalle_pagos.id_factura=? and detalle_pagos.mes=? and detalle_pagos.anio=? order by gasto_comun.tipo;";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            ps.setInt(2, getMes_cierre());
            ps.setInt(3, getAño_cierre());
           

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.setNom_concepto(rs.getString(1));
                modc.setMonto(rs.getDouble(2));
                modc.setCedula(rs.getString(3));
                modc.setNom_proveedor(rs.getString(4));
                modc.setTipo(rs.getString(5));

                listadetallegasto.add(modc);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listadetallegasto;
    }

    public ArrayList<CerrarMes> listardetallescuotas() {
        ArrayList listadetallecuotas = new ArrayList();
        CerrarMes modc;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT concepto_gasto.nom_concepto, detalle_pagos.monto, proveedores.cedula, proveedores.nombre, cuotas_especiales.n_mese_restante FROM detalle_pagos inner join cuotas_especiales on detalle_pagos.id_gasto=cuotas_especiales.id  and tipo_gasto='Cuota especial' inner join concepto_gasto on cuotas_especiales.id_concepto=concepto_gasto.id inner join proveedores on proveedores.cedula=cuotas_especiales.id_proveedor where id_factura=?  and detalle_pagos.mes=? and detalle_pagos.anio=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            ps.setInt(2, getMes_cierre());
            ps.setInt(3, getAño_cierre());
           

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.setNom_concepto(rs.getString(1));
                modc.setMonto(rs.getDouble(2));
                modc.setCedula(rs.getString(3));
                modc.setNom_proveedor(rs.getString(4));
                modc.setMeses_res(rs.getInt(5));

                listadetallecuotas.add(modc);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listadetallecuotas;
    }

    public ArrayList<CerrarMes> listardetallessancion() {
        ArrayList listadetallesancion = new ArrayList();
        CerrarMes modc;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT detalle_pagos.monto, sancion.monto, sancion.tipo, sancion.descripcion FROM detalle_pagos inner join sancion on detalle_pagos.id_gasto=sancion.id and tipo_gasto='Sancion' where detalle_pagos.id_factura=? and detalle_pagos.mes=? and detalle_pagos.anio=?";
        try {
            ps = con.prepareStatement(sql);
             ps.setInt(1, getId());
            ps.setInt(2, getMes_cierre());
            ps.setInt(3, getAño_cierre());
          

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.setMonto(rs.getDouble(1));
                modc.setAlicuota(rs.getDouble(2));
                modc.setTipo(rs.getString(3));
                modc.setEstado(rs.getString(4));

                listadetallesancion.add(modc);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listadetallesancion;
    }

    public ArrayList<CerrarMes> listardetallesinteres() {
        ArrayList listadetalleinteres = new ArrayList();
        CerrarMes modc;

        Connection con = getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT  detalle_pagos.monto, interes.factor, interes.nombre FROM detalle_pagos INNER join interes on detalle_pagos.id_gasto = interes.id and tipo_gasto='Interes' where detalle_pagos.id_factura=? and detalle_pagos.mes=? and detalle_pagos.anio=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            ps.setInt(2, getMes_cierre());
            ps.setInt(3, getAño_cierre());
           

            rs = ps.executeQuery();

            while (rs.next()) {

                modc = new CerrarMes();

                modc.setMonto(rs.getDouble(1));
                modc.setAlicuota(rs.getDouble(2));
                modc.setEstado(rs.getString(3));

                listadetalleinteres.add(modc);
            }
        } catch (Exception e) {
        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return listadetalleinteres;
    }

    public boolean bucartotal(CerrarMes modc) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT monto, alicuota FROM detalle_total where id_unidad=? and mes=? and anio=?";

        try {

            ps = con.prepareStatement(sql);

            ps.setInt(1, getId_unidad());
            ps.setInt(2, getMes_cierre());
            ps.setInt(3, getAño_cierre());
           
            rs = ps.executeQuery();
            if (rs.next()) {

                modc.setMonto(rs.getDouble("monto"));
                modc.setAlicuota(rs.getDouble("alicuota"));

                return true;
            }

            return false;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean buscId(CerrarMes modc) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT MAX(id) as id from cobro";

        try {

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            if (rs.next()) {

                modc.setMes_cierre(rs.getInt("id"));

                return true;
            }

            return false;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean guardarpuentepagos(CerrarMes modc) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO puente_cobro_factura(id_total, id_cobro, parte_monto) VALUES (?, ?, ?);";

        try {

            ps = con.prepareStatement(sql);
            ps.setDouble(1, getId_gasto());
            ps.setInt(2, getMes_cierre());
            ps.setDouble(3, getSaldo_restante());

            ps.execute();

            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;

        } finally {
            try {

                con.close();

            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }
}
