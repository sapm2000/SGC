
package modelo;

/**
 *
 * @author Anthony
 */
public class Persona {
    
    //private int id;
    private String clave;
    private String nombre;
    private String apellido;
    private String domicilio;
    private String telefono;
    private String fecha_nacimiento;
    private String genero;
    private String nacionalidad;
 

    
    
    
    
    
    
    public String getNacionalidad(){
        return nacionalidad;
    
        
    }
    
    public void setNacionalidad(String nacionalidad){
        this.nacionalidad = nacionalidad;
    
    }

   // public int getId() {
      //  return id;
   // }

    //public void setId(int id) {
   //     this.id = id;
   // }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    

    
}
