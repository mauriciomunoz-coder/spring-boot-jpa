package com.bolsaideas.springbootjpa.app.models.dao;

import com.bolsaideas.springbootjpa.app.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {


}
