package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tiket database table.
 * 
 */
@Entity
@NamedQuery(name="Tiket.findAll", query="SELECT t FROM Tiket t")
public class Tiket implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTiket;

	private BigDecimal dobitak;

	private BigDecimal kvotaTiket;

	private BigDecimal uplata;

	@Temporal(TemporalType.TIMESTAMP)
	private Date vremeTiket;

	//bi-directional many-to-one association to Korisnik
	@ManyToOne
	private Korisnik korisnik;

	//bi-directional many-to-one association to Status
	@ManyToOne
	private Status status;

	//bi-directional many-to-many association to Kvota
	@ManyToMany
	@JoinTable(
		name="tiketkvota"
		, joinColumns={
			@JoinColumn(name="Tiket_idTiket")
			}
		, inverseJoinColumns={
			@JoinColumn(name="Kvota_idKvota")
			}
		)
	private List<Kvota> kvotas;

	public Tiket() {
	}

	public int getIdTiket() {
		return this.idTiket;
	}

	public void setIdTiket(int idTiket) {
		this.idTiket = idTiket;
	}

	public BigDecimal getDobitak() {
		return this.dobitak;
	}

	public void setDobitak(BigDecimal dobitak) {
		this.dobitak = dobitak;
	}

	public BigDecimal getKvotaTiket() {
		return this.kvotaTiket;
	}

	public void setKvotaTiket(BigDecimal kvotaTiket) {
		this.kvotaTiket = kvotaTiket;
	}

	public BigDecimal getUplata() {
		return this.uplata;
	}

	public void setUplata(BigDecimal uplata) {
		this.uplata = uplata;
	}

	public Date getVremeTiket() {
		return this.vremeTiket;
	}

	public void setVremeTiket(Date vremeTiket) {
		this.vremeTiket = vremeTiket;
	}

	public Korisnik getKorisnik() {
		return this.korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Kvota> getKvotas() {
		return this.kvotas;
	}

	public void setKvotas(List<Kvota> kvotas) {
		this.kvotas = kvotas;
	}

}