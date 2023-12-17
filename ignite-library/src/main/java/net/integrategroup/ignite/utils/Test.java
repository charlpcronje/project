package net.integrategroup.ignite.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Test {

	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		Test test = new Test();

		String pwd;

		pwd = "L30nHug0!";
		System.out.println(pwd + " -> " + test.passwordEncoder().encode(pwd));

		pwd = "t35tU53r!";
		System.out.println(pwd + " -> " + test.passwordEncoder().encode(pwd));

		//     Ingrid
		pwd = "1ngr1d!";
		System.out.println(pwd + " -> " + test.passwordEncoder().encode(pwd));

		pwd = "@4ndr3!";
		System.out.println(pwd + " -> " + test.passwordEncoder().encode(pwd));
	}

}
