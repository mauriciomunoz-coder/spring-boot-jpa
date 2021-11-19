package com.bolsaideas.springbootjpa.app.models.entity;




import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    private static final long serialVersionUID =1L;

    @Id  //indica que es llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 4, max = 20)   //esta anotacion valida que minimo sean 4 caracteres y maximo 20 caracteres
    private String nombre;

    @NotEmpty
    private String apellido;

    @NotEmpty
    @Email
    private String email;


    @NotNull
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE) //solo se guarda la fecha
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    private String foto;

    @OneToMany(mappedBy = "cliente" ,fetch = FetchType.LAZY, cascade =  CascadeType.ALL )
    private List<Factura> facturas;


    public Cliente(){
        facturas = new ArrayList<Factura>();
    }
    //crea la fecha automaticamente, por eso no se pone en el formulario
    /*@PrePersist
    public void prePersist() {
        createAt = new Date();
    }*/


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    //inserta las facturas en la lista facturas
    public void addFactura(Factura factura) {
        facturas.add(factura);
    }
}
