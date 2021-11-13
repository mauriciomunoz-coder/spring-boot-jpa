package com.bolsaideas.springbootjpa.app.models.dao;

import com.bolsaideas.springbootjpa.app.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;


public interface IClienteDao extends CrudRepository<Cliente, Long> {


}
