package com.n26.cc.server.models;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionSummingDTO {

	@NotNull
	@XmlElement
	private double sum;

	public TransactionSummingDTO() {}

	public TransactionSummingDTO(final double sum) {
		this.sum = sum;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(final double sum) {
		this.sum = sum;
	}

}
