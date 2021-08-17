package com.Vital.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Vital.Model.Entity.Producto;
import com.Vital.Service.ProductoService;

@RestController
@CrossOrigin(origins ="*")
@RequestMapping(path = "/api/v1/producto")
public class ProductoController {

	@Autowired
	private ProductoService service;
	
	@GetMapping("/all")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.findAll());
	}
	
	@GetMapping(params = "id")
	public ResponseEntity<?> findById(@RequestParam Long id) {
		Optional<Producto> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Producto producto = o.get();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(producto);
	}
	
	@GetMapping(params = "name")
	public ResponseEntity<?> findByNombre(@RequestParam String name) {
		List<Producto> productos = service.findByNombre(name);
		if(productos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(productos);
	}
	
	@GetMapping("/uploads/img/{id}")
	public ResponseEntity<?> viewImage(@PathVariable Long id) {
		Optional<Producto> o = service.findById(id);
		if(o.isEmpty() || o.get().getImagen() == null) {
			return ResponseEntity.notFound().build();
		}
		Resource imagen = new ByteArrayResource(o.get().getImagen());
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
	}
	
	@GetMapping(params = "category")
	public ResponseEntity<?> findByCategory(@RequestParam String category) {
		List<Producto> productos = service.findByCategory(category);
		if(productos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(productos);
	}
	
	@GetMapping(params = "use")
	public ResponseEntity<?> findByUse(@RequestParam String use) {
		List<Producto> productos = service.findByUse(use);
		if(productos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(productos);
	}
	
	@GetMapping(params = "benefit")
	public ResponseEntity<?> findByBenefit(@RequestParam String benefit) {
		List<Producto> productos = service.findByBenefit(benefit);
		if(productos.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(productos);
	}
	
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody Producto producto, BindingResult result) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(producto));
	}
	
	@PostMapping("/crear-con-img")
	public ResponseEntity<?> createWithImg(@Valid Producto producto, BindingResult result,
			@RequestParam MultipartFile image) throws IOException {
		if(!image.isEmpty()) {
			producto.setImagen(image.getBytes());
		}
		return create(producto,result);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@RequestBody Producto producto, @PathVariable Long id) {
		Optional<Producto> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Producto productoDb = o.get();
		productoDb.setNombre(producto.getNombre());
		productoDb.setCategoria(producto.getCategoria());
		productoDb.setUso(producto.getUso());
		productoDb.setBeneficio(producto.getBeneficio());
		productoDb.setDescripcion(producto.getDescripcion());
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.create(productoDb));
	}
	
	@PutMapping("/editar-con-img/{id}")
	public ResponseEntity<?> updateWithImg(@Valid Producto producto, BindingResult result,
			@PathVariable long id, @RequestParam MultipartFile image) throws IOException {
		if(result.hasErrors()) {
			return this.validar(result);
		}
		Optional<Producto> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Producto productoDb = o.get();
		productoDb.setNombre(producto.getNombre());
		productoDb.setCategoria(producto.getCategoria());
		productoDb.setUso(producto.getUso());
		productoDb.setBeneficio(producto.getBeneficio());
		productoDb.setDescripcion(producto.getDescripcion());
		if(!image.isEmpty()) {
			productoDb.setImagen(image.getBytes());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(productoDb));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		service.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	protected ResponseEntity<?> validar(BindingResult result) {
		Map<String, Object> errors = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errors);
	}
}
