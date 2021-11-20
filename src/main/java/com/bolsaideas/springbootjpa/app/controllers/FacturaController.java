package com.bolsaideas.springbootjpa.app.controllers;


import com.bolsaideas.springbootjpa.app.models.entity.Cliente;
import com.bolsaideas.springbootjpa.app.models.entity.Factura;
import com.bolsaideas.springbootjpa.app.models.entity.Producto;
import com.bolsaideas.springbootjpa.app.models.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

    @Autowired
    private IClienteService clienteService;


    //metodo que crea una factura
    @GetMapping("/form/{clienteId}")
    public String crear(@PathVariable(value = "clienteId") Long clienteId, Model model, RedirectAttributes flash) {

        Cliente cliente = clienteService.findOne(clienteId);  //encontramos el cliente al que vamos a crearle la factura

        if (cliente == null) {  //si el cliente No existe redirigimos a listar con un mensaje
            flash.addFlashAttribute("msg", "El Cliente No existe en la BD");
            return "redirect:/listar";
        }

        Factura factura = new Factura();
        factura.setCliente(cliente); //antes de crear la factura le asignamos un cliente

        model.addAttribute("factura", factura);
        model.addAttribute("titulo", "Crear factura");

        return "factura/form";
    }

    //busca el producto para el autocomplete
    @GetMapping(value = "/cargar-productos/{term}", produces = {"application/json"})
    public @ResponseBody
    List<Producto> cargarProductos(@PathVariable String term) {
        return clienteService.findByNombre(term);
    }
}
