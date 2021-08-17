package com.Vital.Model.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String nombre;
	
	private String categoria;
	
	private String descripcion;
	
	@Lob
	@JsonIgnore
	private byte[] imagen;
	
	private String uso;
	
	private String beneficio;
	
	public Integer getimgHashCode() {
		return (this.imagen != null) ? this.imagen.hashCode(): null;
	}
}
