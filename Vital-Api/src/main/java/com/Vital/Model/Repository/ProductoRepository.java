package com.Vital.Model.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Vital.Model.Entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

	List<Producto> findByNombre(String nombre);
	
	List<Producto> findByCategoriaContainingIgnoreCase(String term);
	
	List<Producto> findByUsoContainingIgnoreCase(String term);
	
	List<Producto> findByBeneficioContainingIgnoreCase(String term);
}
