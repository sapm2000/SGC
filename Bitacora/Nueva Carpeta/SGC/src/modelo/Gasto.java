package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sgc.SGC;

public class Gasto extends ConexionBD {

    private Integer id;
    private String tipo;
    private Proveedores proveedor = new Proveedores();
    private String calcular;
    private int mes;
    private int anio;
    private Integer numMeses;
    private Asambleas asamblea;
    private String observacion;
    private Integer mesesRestantes;
    private Double monto;
    private Double saldo;
    private String estado;
    private String pagado;
    private String moneda;
    private String nombre;

    private ArrayList<ConceptoGasto> conceptos = new ArrayList();
    private ArrayList<Double> montoConceptos = new ArrayList();

    private Connection con;

    private Boolean agregarConcepto(Integer id, Double monto) throws SQLException {
        int ind;
        ps = null;

        String sql = "INSERT INTO puente_gasto_concepto(id_gasto, id_concepto, monto) VALUES (?,?,?);";

        ps = con.prepareStatement(sql);

        ind = 1;
        ps.setInt(ind++, getId());
        ps.setInt(ind++, id);
        ps.setDouble(ind++, monto);

        ps.execute();

        return true;
    }
    
    public int contar() {

        ps = null;
        rs = null;
        con = getConexion();

        String sql = "SELECT COUNT(*) FROM gasto;";

        try {

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            rs.next();

            int count = rs.getInt("count");

            return count;

        } catch (SQLException e) {

            System.err.println(e);
            return 0;

        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }

    }

    public boolean buscarId() {
        try {
            ps = null;
            rs = null;
            con = getConexion();

            String sql = "SELECT MAX(id) AS id FROM v_gasto;";

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                setId(rs.getInt("id"));
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
    
     public boolean eliminar() {

        ps = null;
        con = getConexion();

        String sql = "SELECT eliminar_gasto(?,?);";

        try {

            ps = con.prepareStatement(sql);

            ps.setInt(1, getId());
            ps.setInt(2, SGC.usuarioActual.getId());

            if (ps.execute()) {
                rs = ps.getResultSet();
                rs.next();
                return rs.getBoolean(1);

            } else {
                return false;
            }

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

    public Boolean registrar() {
        try {
            int ind;
            boolean resul = false;

            ps = null;
            con = getConexion();

            String sql = "SELECT agregar_gasto(?,?,?,?,?,?,?,?,?,?,?,?,?);";

            ps = con.prepareStatement(sql);
            ind = 1;
            ps.setString(ind++, getNombre());
            ps.setString(ind++, getTipo());
            ps.setString(ind++, proveedor.getCedulaRif());
            ps.setString(ind++, getCalcular());
            ps.setInt(ind++, getMes());
            ps.setInt(ind++, getAnio());
            ps.setInt(ind++, getNumMeses());

            if (asamblea != null) {
                ps.setInt(ind++, asamblea.getId());

            } else {
                ps.setNull(ind++, java.sql.Types.NULL);
            }

            ps.setString(ind++, getObservacion());
            ps.setInt(ind++, getMesesRestantes());
            ps.setDouble(ind++, getMonto());
            ps.setString(ind++, getMoneda());
            ps.setInt(ind++, SGC.usuarioActual.getId());

            if (ps.execute()) {
                rs = ps.getResultSet();
                rs.next();
                resul = rs.getBoolean(1);

            } else {
                return false;
            }

            if (buscarId()) {

                if (registrarConceptos()) {
                    return resul;
                }
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

    public Boolean registrarConceptos() {
        try {
            int ind;
            ps = null;
            con = getConexion();

            String sql = "INSERT INTO puente_gasto_concepto(id_gasto, id_concepto, monto) VALUES (?,?,?);";

            ps = con.prepareStatement(sql);
            ind = 1;
            ps.setInt(ind++, getId());

            for (int i = 0; i < conceptos.size(); i++) {
                ind = 2;
                ps.setInt(ind++, conceptos.get(i).getId());
                ps.setDouble(ind++, montoConceptos.get(i));
                ps.execute();
            }

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

    public ArrayList<Gasto> listar(String status) {
        try {
            ArrayList<Gasto> lista = new ArrayList();
            Gasto item;
            ResultSet rs2;

            con = getConexion();
            ps = null;
            rs = null;

            String sql = "";

            if (status == "Pendiente" || status == "Pagado") {
                sql = "SELECT * FROM v_gasto WHERE pagado = '" + status + "';";

            } else if (status == "") {
                sql = "SELECT * FROM v_gasto;";
            }

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            sql = "SELECT * FROM v_gasto_concepto WHERE id_gasto = ?";
            ps = con.prepareStatement(sql);

            while (rs.next()) {
                item = new Gasto();

                item.setId(rs.getInt("id"));
                item.setTipo(rs.getString("tipo"));
                item.proveedor.setCedulaRif(rs.getString("id_proveedor"));
                item.proveedor.setNombre(rs.getString("proveedor"));
                item.setCalcular(rs.getString("calcular_por"));
                item.setMes(rs.getInt("mes"));
                item.setAnio(rs.getInt("anio"));
                item.setMonto(rs.getDouble("monto"));
                item.setSaldo(rs.getDouble("saldo"));
                item.setNumMeses(rs.getInt("n_meses"));
                item.setMesesRestantes(rs.getInt("meses_restantes"));
                item.setMoneda(rs.getString("moneda"));
                item.setNombre(rs.getString("nombre"));

                if (rs.getInt("id_asamblea") != 0) {
                    item.asamblea = new Asambleas();

                    item.asamblea.setId(rs.getInt("id_asamblea"));
                    item.asamblea.setNombre(rs.getString("asamblea"));
                    item.asamblea.setFecha(rs.getDate("fecha_asamblea"));

                } else {
                    item.asamblea = null;
                }

                item.setObservacion(rs.getString("observacion"));
                item.setEstado(rs.getString("estado"));
                item.setPagado(rs.getString("pagado"));

                ps.setInt(1, item.getId());
                rs2 = ps.executeQuery();

                while (rs2.next()) {
                    ConceptoGasto concepto = new ConceptoGasto();
                    concepto.setId(rs2.getInt("id_concepto"));
                    concepto.setNombre(rs2.getString("nombre"));

                    item.getConceptos().add(concepto);
                    item.montoConceptos.add(rs2.getDouble("monto"));
                }

                lista.add(item);
            }

            return lista;

        } catch (SQLException e) {
            System.err.println(e);
            return null;

        } finally {
            try {
                con.close();

            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public ArrayList<Gasto> listarGastos() {
        ArrayList listacuotasEspeciales = new ArrayList();
        Gasto modcuo;

        con = getConexion();
        ps = null;
        rs = null;

        String sql = "SELECT id, tipo, id_proveedor, calcular_por, mes, anio, n_meses, observacion, meses_restantes, monto, saldo, estado, pagado, moneda FROM gasto where meses_restantes !=0;";
        try {
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {

                modcuo = new Gasto();

                modcuo.setId(rs.getInt("id"));
                modcuo.proveedor.setCedulaRif(rs.getString("id_proveedor"));
                modcuo.setTipo(rs.getString("tipo"));
                modcuo.setCalcular(rs.getString("calcular_por"));
                modcuo.setMes(rs.getInt("mes"));
                modcuo.setAnio(rs.getInt("anio"));
                modcuo.setMonto(rs.getDouble("monto"));
                modcuo.setSaldo(rs.getDouble("saldo"));
                modcuo.setNumMeses(rs.getInt("n_meses"));
                modcuo.setMoneda(rs.getString("moneda"));
                modcuo.setObservacion(rs.getString("observacion"));
                modcuo.setEstado(rs.getString("estado"));
                modcuo.setMesesRestantes(rs.getInt("meses_restantes"));

                listacuotasEspeciales.add(modcuo);
            }
        } catch (Exception e) {
            System.err.println(e);

        } finally {
            try {
                con.close();

            } catch (SQLException e) {
                System.err.println(e);
            }
        }

        return listacuotasEspeciales;
    }

    public boolean modificar() {

        try {
            int ind;
            boolean resul = false;

            ps = null;
            con = getConexion();

            String sql = "SELECT modificar_gasto(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

            ps = con.prepareStatement(sql);
            ind = 1;
            ps.setInt(ind++, getId());
            ps.setString(ind++, getNombre());
            ps.setString(ind++, getTipo());
            ps.setString(ind++, proveedor.getCedulaRif());
            ps.setString(ind++, getCalcular());
            ps.setInt(ind++, getMes());
            ps.setInt(ind++, getAnio());
            ps.setInt(ind++, getNumMeses());

            if (asamblea != null) {
                ps.setInt(ind++, asamblea.getId());

            } else {
                ps.setNull(ind++, java.sql.Types.NULL);
            }

            ps.setString(ind++, getObservacion());
            ps.setInt(ind++, getMesesRestantes());
            ps.setDouble(ind++, getMonto());
            ps.setDouble(ind++, getSaldo());
            ps.setString(ind++, getMoneda());
            ps.setInt(ind++, SGC.usuarioActual.getId());
            
            if (ps.execute()) {
                rs = ps.getResultSet();
                rs.next();
                resul = rs.getBoolean(1);

            } else {
                return false;
            }

            if (modificarConceptos()) {
                return resul;

            } else {
                return false;
            }

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

    private Boolean modificarConceptos() {

        try {
            ArrayList<ConceptoGasto> conceptosViejos;
            ArrayList<Double> montosViejos;
            ConceptoGasto item;

            int numNuevos;
            int numViejos;

            conceptosViejos = new ArrayList();
            montosViejos = new ArrayList();

            ps = null;
            con = getConexion();

            String sql = "SELECT id_concepto, monto FROM v_gasto_concepto WHERE id_gasto = ?;";

            ps = con.prepareStatement(sql);
            ps.setInt(1, getId());
            rs = ps.executeQuery();

            while (rs.next()) {
                
                item = new ConceptoGasto();
                item.setId(rs.getInt("id_concepto"));
                conceptosViejos.add(item);
                montosViejos.add(rs.getDouble("monto"));
                System.out.println("se obtuvo el id_concepto = " + item.getId() + " con el monto " + rs.getDouble("monto"));
                
            }

            numNuevos = getConceptos().size();
            numViejos = conceptosViejos.size();

            Boolean procesado;

            // Ciclo que recorre los conceptos nuevos
            for (int j = 0; j < numNuevos; j++) {

                procesado = false;

                // Ciclo que recorre los conceptos viejos
                for (int i = 0; i < numViejos; i++) {

                    // Si el concepto de la lista de nuevos y su monto coincide con el de la BD
                    if (getConceptos().get(j).getId().equals(conceptosViejos.get(i).getId()) && getMontoConceptos().get(j).equals(montosViejos.get(i))) {
                        
                        // No se hace nada con él y se elimina de ambos arreglos junto a su monto para dejar de compararlos
                        getConceptos().remove(j);
                        getMontoConceptos().remove(j);
                        numNuevos--;
                        
                        conceptosViejos.remove(i);
                        montosViejos.remove(i);
                        numViejos--;
                        
                        procesado = true;
                        break;

                        //En cambio, si el concepto coincide, pero el monto es diferente
                    } else if (getConceptos().get(j).getId().equals(conceptosViejos.get(i).getId()) && !getMontoConceptos().get(j).equals(montosViejos.get(i))) {
                        
                        //Se modifica el monto del concepto y se elimina de ambos arreglos junto al monto para dejar de compararlos
                        modificarMontoConcepto(getConceptos().get(j).getId(), getMontoConceptos().get(j));
                        System.out.println("se modificó el monto del concepto " + getConceptos().get(j).getId() + ", de " + montosViejos.get(i) + " a " + getMontoConceptos().get(j));
                        getConceptos().remove(j);
                        getMontoConceptos().remove(j);
                        numNuevos--;
                        
                        conceptosViejos.remove(i);
                        montosViejos.remove(i);
                        numViejos--;
                        
                        procesado = true;
                        break;
                    }
                }

                // Si el concepto nuevo no ha sido procesado
                if (!procesado) {
                    
                    // Se agrega como nuevo concepto y se elimina del arreglo de nuevos
                    agregarConcepto(getConceptos().get(j).getId(), getMontoConceptos().get(j));
                    System.out.println("se agrego el concepto " + getConceptos().get(j).getId() + " con el monto " + getMontoConceptos().get(j));
                    getConceptos().remove(j);
                    getMontoConceptos().remove(j);
                    numNuevos--;
                    j--;

                } else {
                    //Se reduce el índice que recorre los conceptos nuevos
                    j--;
                }
            }

            // Se retiran los viejos conceptos que quedaron en el arreglo de viejos (ya que fueron eliminados)
            retirarConceptos(conceptosViejos);

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

    private Boolean modificarMontoConcepto(Integer id, Double monto) throws SQLException {
        int ind;
        ps = null;

        String sql = "UPDATE puente_gasto_concepto SET monto = ? WHERE id_gasto = ? AND id_concepto = ?;";

        ps = con.prepareStatement(sql);

        ind = 1;
        ps.setDouble(ind++, monto);
        ps.setInt(ind++, getId());
        ps.setInt(ind++, id);

        ps.execute();

        return true;
    }

//    public Boolean restarSaldo(Double saldoNuevo) {
//        ps = null;
//        con = getConexion();
//        int ind;
//
//        String sql = "SELECT pagar_gasto(?,?)";
//
//        try {
//            ind = 1;
//            ps = con.prepareStatement(sql);
//            ps.setInt(ind++, getId());
//            ps.setDouble(ind++, saldoNuevo);
//            ps.execute();
//
//            return true;
//
//        } catch (Exception e) {
//            System.err.println(e);
//            return false;
//
//        } finally {
//
//            try {
//                con.close();
//
//            } catch (Exception e) {
//                System.err.println(e);
//            }
//        }
//    }

    private Boolean retirarConceptos(ArrayList<ConceptoGasto> conceptos) throws SQLException {
        ps = null;

        int ind;

        String sql = "DELETE FROM puente_gasto_concepto WHERE id_gasto = ? AND id_concepto = ?";

        ps = con.prepareStatement(sql);

        ind = 1;
        ps.setInt(ind++, getId());

        for (int i = 0; i < conceptos.size(); i++) {
            ind = 2;
            System.out.println("se retiro el concepto con id = " + conceptos.get(i).getId());
            ps.setInt(ind++, conceptos.get(i).getId());
            ps.execute();
        }

        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Proveedores getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedores proveedor) {
        this.proveedor = proveedor;
    }

    public String getCalcular() {
        return calcular;
    }

    public void setCalcular(String calcular) {
        this.calcular = calcular;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Integer getNumMeses() {
        return numMeses;
    }

    public void setNumMeses(Integer numMeses) {
        this.numMeses = numMeses;
    }

    public Asambleas getAsamblea() {
        return asamblea;
    }

    public void setAsamblea(Asambleas asamblea) {
        this.asamblea = asamblea;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public ArrayList<ConceptoGasto> getConceptos() {
        return conceptos;
    }

    public void setConceptos(ArrayList<ConceptoGasto> conceptos) {
        this.conceptos = conceptos;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Integer getMesesRestantes() {
        return mesesRestantes;
    }

    public void setMesesRestantes(Integer mesesRestantes) {
        this.mesesRestantes = mesesRestantes;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPagado() {
        return pagado;
    }

    public void setPagado(String pagado) {
        this.pagado = pagado;
    }

    public ArrayList<Double> getMontoConceptos() {
        return montoConceptos;
    }

    public void setMontoConceptos(ArrayList<Double> montoConceptos) {
        this.montoConceptos = montoConceptos;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
