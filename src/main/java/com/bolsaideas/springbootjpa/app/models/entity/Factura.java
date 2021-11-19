package com.bolsaideas.springbootjpa.app.models.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "facturas")
public class Factura implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String descripcion;
    private String observacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private Date createAt;


    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;


    //genera la fecha automaticamente
    @PrePersist
    public void prePersist() {
        createAt = new Date();
    }


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "factura_id")  //crea la llave foranea en la tabla factura_items de la BD - se hace cuando la relacion es unidireccional
    private List<ItemFactura> items;

    public Double getTotal(){
        Double total = 0.0;
        int size = items.size();
        for (int i = 0; i < size; i++) {
            total += items.get(i).calcularImporte();
        }
        return total;
    }


    //inicializamos la lista items
    public Factura(){
        this.items = new ArrayList<ItemFactura>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemFactura> getItems() {
        return items;
    }

    public void setItems(List<ItemFactura> items) {
        this.items = items;
    }

    //agrega varios item a la lista items
    public void addItemFactura(ItemFactura item){
        this.items.add(item);
    }

    private static final long serialVersionUID = 1L;
}
