package com.bolsaideas.springbootjpa.app;

import com.bolsaideas.springbootjpa.app.models.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootJpaApplication implements CommandLineRunner {

    @Autowired
    IUploadFileService uploadFileService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJpaApplication.class, args);
    }


    //crea la carpeta uploads automaticamente al iniciar la aplicacion
    //elimina las fotos de la carpeta uploads al detener la aplicacion
    @Override
    public void run(String... args) throws Exception {
        uploadFileService.deletAll();
        uploadFileService.init();
    }
}
