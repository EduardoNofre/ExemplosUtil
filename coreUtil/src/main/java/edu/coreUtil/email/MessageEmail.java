/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.coreUtil.email;

import javax.mail.Message;

public class MessageEmail {

	private String								email;
	private Message.RecipientType	recipiente;
	private String								nome;

	public MessageEmail(String email, Message.RecipientType recipiente, String nome) {
		this.email = email;
		this.recipiente = recipiente;
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Message.RecipientType getRecipiente() {
		return recipiente;
	}

	public void setRecipiente(Message.RecipientType recipiente) {
		this.recipiente = recipiente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
