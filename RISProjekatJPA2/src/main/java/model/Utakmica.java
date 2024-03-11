package model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the utakmica database table.
 * 
 */
@Entity
@NamedQuery(name="Utakmica.findAll", query="SELECT u FROM Utakmica u")
public class Utakmica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUtakmica;

	private String timDomaci;

	private String timGost;
	
	private Integer poeniDomaci;
	
	private Integer poeniGost;

	@Temporal(TemporalType.TIMESTAMP)
	private Date vremeUtakmica;

	//bi-directional many-to-one association to Kvota
	@OneToMany(mappedBy="utakmica")
	private List<Kvota> kvotas;

	//bi-directional many-to-one association to Sport
	@ManyToOne
	private Sport sport;

	public Utakmica() {
	}

	public int getIdUtakmica() {
		return this.idUtakmica;
	}

	public void setIdUtakmica(int idUtakmica) {
		this.idUtakmica = idUtakmica;
	}

	public String getTimDomaci() {
		return this.timDomaci;
	}

	public void setTimDomaci(String timDomaci) {
		this.timDomaci = timDomaci;
	}

	public String getTimGost() {
		return this.timGost;
	}

	public void setTimGost(String timGost) {
		this.timGost = timGost;
	}

	public Date getVremeUtakmica() {
		return this.vremeUtakmica;
	}

	public void setVremeUtakmica(Date vremeUtakmica) {
		this.vremeUtakmica = vremeUtakmica;
	}

	public List<Kvota> getKvotas() {
		return this.kvotas;
	}

	public void setKvotas(List<Kvota> kvotas) {
		this.kvotas = kvotas;
	}

	public Kvota addKvota(Kvota kvota) {
		getKvotas().add(kvota);
		kvota.setUtakmica(this);

		return kvota;
	}

	public Kvota removeKvota(Kvota kvota) {
		getKvotas().remove(kvota);
		kvota.setUtakmica(null);

		return kvota;
	}

	public Sport getSport() {
		return this.sport;
	}

	public void setSport(Sport sport) {
		this.sport = sport;
	}
	
	public Integer getPoeniDomaci() {
		return poeniDomaci;
	}

	public void setPoeniDomaci(Integer poeniDomaci) {
		this.poeniDomaci = poeniDomaci;
	}

	public Integer getPoeniGost() {
		return poeniGost;
	}

	public void setPoeniGost(Integer poeniGost) {
		this.poeniGost = poeniGost;
	}

}