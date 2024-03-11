package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the kvota database table.
 * 
 */
@Entity
@NamedQuery(name="Kvota.findAll", query="SELECT k FROM Kvota k")
public class Kvota implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idKvota;

	private BigDecimal kvotaKvota;

	//bi-directional many-to-one association to Igra
	@ManyToOne
	private Igra igra;

	//bi-directional many-to-one association to Status
	@ManyToOne
	private Status status;

	//bi-directional many-to-one association to Utakmica
	@ManyToOne
	private Utakmica utakmica;

	//bi-directional many-to-many association to Tiket
	@ManyToMany(mappedBy="kvotas")
	private List<Tiket> tikets;

	public Kvota() {
	}

	public int getIdKvota() {
		return this.idKvota;
	}

	public void setIdKvota(int idKvota) {
		this.idKvota = idKvota;
	}

	public BigDecimal getKvotaKvota() {
		return this.kvotaKvota;
	}

	public void setKvotaKvota(BigDecimal kvotaKvota) {
		this.kvotaKvota = kvotaKvota;
	}

	public Igra getIgra() {
		return this.igra;
	}

	public void setIgra(Igra igra) {
		this.igra = igra;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Utakmica getUtakmica() {
		return this.utakmica;
	}

	public void setUtakmica(Utakmica utakmica) {
		this.utakmica = utakmica;
	}

	public List<Tiket> getTikets() {
		return this.tikets;
	}

	public void setTikets(List<Tiket> tikets) {
		this.tikets = tikets;
	}

}