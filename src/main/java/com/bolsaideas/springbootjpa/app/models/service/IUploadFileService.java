package com.bolsaideas.springbootjpa.app.models.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface IUploadFileService {

    public Resource load(String filename) throws MalformedURLException;

    public String copy(MultipartFile file)throws IOException, IOException;

    public boolean delete(String filename);



    public void deletAll();      //elimina las fotos de la carpeta uploads al detener la aplicacion


    public void init() throws IOException;  //crea la carpeta uploads automaticamente al iniciar la aplicacion
}
