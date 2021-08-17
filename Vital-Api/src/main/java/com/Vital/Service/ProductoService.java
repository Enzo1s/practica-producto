package com.Vital.Service;

import java.util.List;
import java.util.Optional;

import com.Vital.Model.Entity.Producto;

public interface ProductoService {

	Iterable<Producto> findAll();
	
	Optional<Producto> findById(Long id);
	
	List<Producto> findByNombre(String nombre);
	
	List<Producto> findByCategory(String term);
	
	List<Producto> findByUse(String term);
	
	List<Producto> findByBenefit(String term);
	
	Producto create(Producto producto);
	
	void deleteById(Long id);
	
}
