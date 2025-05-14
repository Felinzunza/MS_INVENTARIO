package com.EcoMarketMS.MS_INVENTARIO.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EcoMarketMS.MS_INVENTARIO.model.Categoria;
import com.EcoMarketMS.MS_INVENTARIO.repo.CategoriaRepository;

@Service
public class CategoriaService {
 
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }
    public Categoria findById(int id) {
        return categoriaRepository.findById(id);
    }

    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }


    public void delete(int id) {
        categoriaRepository.deleteById(id);
    }
    
}
