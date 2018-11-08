package com.appliedsni.channel.core.server.security.service;

public class PasswordGenrate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PasswordEncoder pe=new PasswordEncoder();
		System.out.println(pe.hashPassword("abcd@1234"));
	}

}
