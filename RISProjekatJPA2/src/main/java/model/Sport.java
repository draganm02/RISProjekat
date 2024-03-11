package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the sport database table.
 * 
 */
@Entity
@NamedQuery(name="Sport.findAll", query="SELECT s FROM Sport s")
public class Sport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idSport;

	private String nazivSport;

	//bi-directional many-to-one association to Utakmica
	@OneToMany(mappedBy="sport")
	private List<Utakmica> utakmicas;

	public Sport() {
	}

	public int getIdSport() {
		return this.idSport;
	}

	public void setIdSport(int idSport) {
		this.idSport = idSport;
	}

	public String getNazivSport() {
		return this.nazivSport;
	}

	public void setNazivSport(String nazivSport) {
		this.nazivSport = nazivSport;
	}

	public List<Utakmica> getUtakmicas() {
		return this.utakmicas;
	}

	public void setUtakmicas(List<Utakmica> utakmicas) {
		this.utakmicas = utakmicas;
	}

	public Utakmica addUtakmica(Utakmica utakmica) {
		getUtakmicas().add(utakmica);
		utakmica.setSport(this);

		return utakmica;
	}

	public Utakmica removeUtakmica(Utakmica utakmica) {
		getUtakmicas().remove(utakmica);
		utakmica.setSport(null);

		return utakmica;
	}

}