package com.Vital.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Vital.Model.Entity.Producto;
import com.Vital.Model.Repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService{

	@Autowired
	private ProductoRepository repository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<Producto> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Producto> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByNombre(String nombre) {
		return repository.findByNombre(nombre);
	}

	@Override
	public Producto create(Producto producto) {
		return repository.save(producto);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByCategory(String term) {
		return repository.findByCategoriaContainingIgnoreCase(term);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByUse(String term) {
		return repository.findByUsoContainingIgnoreCase(term);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByBenefit(String term) {
		return repository.findByBeneficioContainingIgnoreCase(term);
	}
}
