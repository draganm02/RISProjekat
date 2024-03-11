package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the status database table.
 * 
 */
@Entity
@NamedQuery(name="Status.findAll", query="SELECT s FROM Status s")
public class Status implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idStatus;

	private String status;

	//bi-directional many-to-one association to Kvota
	@OneToMany(mappedBy="status")
	private List<Kvota> kvotas;

	//bi-directional many-to-one association to Tiket
	@OneToMany(mappedBy="status")
	private List<Tiket> tikets;

	public Status() {
	}

	public int getIdStatus() {
		return this.idStatus;
	}

	public void setIdStatus(int idStatus) {
		this.idStatus = idStatus;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Kvota> getKvotas() {
		return this.kvotas;
	}

	public void setKvotas(List<Kvota> kvotas) {
		this.kvotas = kvotas;
	}

	public Kvota addKvota(Kvota kvota) {
		getKvotas().add(kvota);
		kvota.setStatus(this);

		return kvota;
	}

	public Kvota removeKvota(Kvota kvota) {
		getKvotas().remove(kvota);
		kvota.setStatus(null);

		return kvota;
	}

	public List<Tiket> getTikets() {
		return this.tikets;
	}

	public void setTikets(List<Tiket> tikets) {
		this.tikets = tikets;
	}

	public Tiket addTiket(Tiket tiket) {
		getTikets().add(tiket);
		tiket.setStatus(this);

		return tiket;
	}

	public Tiket removeTiket(Tiket tiket) {
		getTikets().remove(tiket);
		tiket.setStatus(null);

		return tiket;
	}

}