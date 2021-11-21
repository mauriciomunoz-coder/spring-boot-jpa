package com.bolsaideas.springbootjpa.app.models.dao;

import com.bolsaideas.springbootjpa.app.models.entity.Factura;
import org.springframework.data.repository.CrudRepository;

public interface IFacturaDao extends CrudRepository<Factura, Long> {


}
