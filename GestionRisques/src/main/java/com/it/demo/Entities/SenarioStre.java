package com.it.demo.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SenarioStre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private long senario_id;
private String Risque_name;
@ManyToOne
@JoinColumn(name = "id_risque",referencedColumnName = "id")
@Cascade(CascadeType.SAVE_UPDATE)
private Risque risquestres;

}
