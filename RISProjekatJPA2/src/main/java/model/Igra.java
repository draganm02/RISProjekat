package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the igra database table.
 * 
 */
@Entity
@NamedQuery(name="Igra.findAll", query="SELECT i FROM Igra i")
public class Igra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idIgra;

	private String nazivIgra;

	//bi-directional many-to-one association to Kvota
	@OneToMany(mappedBy="igra")
	private List<Kvota> kvotas;

	public Igra() {
	}

	public int getIdIgra() {
		return this.idIgra;
	}

	public void setIdIgra(int idIgra) {
		this.idIgra = idIgra;
	}

	public String getNazivIgra() {
		return this.nazivIgra;
	}

	public void setNazivIgra(String nazivIgra) {
		this.nazivIgra = nazivIgra;
	}

	public List<Kvota> getKvotas() {
		return this.kvotas;
	}

	public void setKvotas(List<Kvota> kvotas) {
		this.kvotas = kvotas;
	}

	public Kvota addKvota(Kvota kvota) {
		getKvotas().add(kvota);
		kvota.setIgra(this);

		return kvota;
	}

	public Kvota removeKvota(Kvota kvota) {
		getKvotas().remove(kvota);
		kvota.setIgra(null);

		return kvota;
	}

}