package com.unifacisa.ouvidoriacelgon;

import com.unifacisa.ouvidoriacelgon.enums.UserTypeEnum;
import com.unifacisa.ouvidoriacelgon.exceptions.CpfAlreadyTakenException;
import com.unifacisa.ouvidoriacelgon.exceptions.InvalidCpfException;
import com.unifacisa.ouvidoriacelgon.exceptions.InvalidLoginCredentials;
import com.unifacisa.ouvidoriacelgon.exceptions.UsernameAlreadyTakenException;
import com.unifacisa.ouvidoriacelgon.models.ComplimentModel;
import com.unifacisa.ouvidoriacelgon.models.ProtestModel;
import com.unifacisa.ouvidoriacelgon.models.UserModel;
import com.unifacisa.ouvidoriacelgon.repositories.ProtestRepository;
import com.unifacisa.ouvidoriacelgon.services.ComplimentService;
import com.unifacisa.ouvidoriacelgon.services.ProtestService;
import com.unifacisa.ouvidoriacelgon.services.UserService;
import com.unifacisa.ouvidoriacelgon.util.PrintFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;
import java.util.Scanner;

@SpringBootApplication
public class OuvidoriaCelgonApplication implements CommandLineRunner {

	@Autowired
	private ProtestService protestService;
	@Autowired
	private ComplimentService complimentService;
	@Autowired
	private UserService userService;
	@Autowired
	private PrintFormatter printFormatter;

	public static void main(String[] args) {
		SpringApplication.run(OuvidoriaCelgonApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Scanner sc = new Scanner(System.in);
		boolean isLoggedInAsDefault = false;
		boolean isLoggedInAsAdmin = false;
		String opc = "";
		UserModel userModel = null;

		while (!Objects.equals(opc, "5")) {
			// Login
			System.out.println(showLoginMenu());
			System.out.print("Opção: ");
			opc = sc.nextLine();
			if (opc.equals("1")) { // Cadastrar usuário padrão
				System.out.print("Digite seu nome de usuário: ");
				String username = sc.nextLine();
				System.out.print("Digite sua senha: ");
				String password = sc.nextLine();
				System.out.print("Digite seu CPF (12312312312): ");
				String cpf = sc.nextLine();

				try {
					userService.saveUser(new UserModel(UserTypeEnum.DEFAULT, cpf, username, password));
				} catch (UsernameAlreadyTakenException e) {
					System.out.println("Username existente, escolha um novo.");
				} catch (InvalidCpfException e) {
					System.out.println("Por favor insira um CPF válido.");
				} catch (CpfAlreadyTakenException e) {
					System.out.println("CPF já cadastrado.");
				}

			} else if (opc.equals("2")) { // Cadastrar usuário administrador
				System.out.print("Digite seu nome de usuário: ");
				String username = sc.nextLine();
				System.out.print("Digite sua senha: ");
				String password = sc.nextLine();
				System.out.print("Digite seu CPF (12312312312): ");
				String cpf = sc.nextLine();

				try {
					userService.saveUser(new UserModel(UserTypeEnum.ADMIN, cpf, username, password));
				} catch (UsernameAlreadyTakenException e) {
					System.out.println("Username existente, escolha um novo.");
				} catch (InvalidCpfException e) {
					System.out.println("Por favor insira um CPF válido.");
				} catch (CpfAlreadyTakenException e) {
					System.out.println("CPF já cadastrado.");
				}

			} else if (opc.equals("3")) { // Logar como usuário padrão
				System.out.print("Digite seu nome de usuário: ");
				String username = sc.nextLine();
				System.out.print("Digite sua senha: ");
				String password = sc.nextLine();

				try {
					isLoggedInAsDefault = userService.login(new UserModel(UserTypeEnum.DEFAULT, username, password));
					if (isLoggedInAsDefault) {
						userModel = userService.findUserByUsername(username).get(0);
					}
					break;
				} catch (InvalidLoginCredentials e) {
					System.out.println("Credenciais de login inválidas!");
				}

			} else if (opc.equals("4")) { // Logar como usuário administrador
				System.out.print("Digite seu nome de usuário: ");
				String username = sc.nextLine();
				System.out.print("Digite sua senha: ");
				String password = sc.nextLine();

				try {
					isLoggedInAsAdmin = userService.login(new UserModel(UserTypeEnum.ADMIN, username, password));
					userModel = userService.findUserByUsername(username).get(0);
					break;
				} catch (InvalidLoginCredentials e) {
					System.out.println("Credenciais de login inválidas!");
				}
			} else if (opc.equals("5")) {
				System.out.println("Finalizando... Obrigado!");
				System.exit(0);
			}
		}

	if (isLoggedInAsDefault) {
		while (!Objects.equals(opc, "5")) {
			System.out.println(showDefaultUserMenu());
			System.out.print("Opção: ");
			opc = sc.nextLine();

			if (opc.equals("1")) {
				System.out.println("Descreva o elogio: ");
				String complimentDescription = sc.nextLine();
				complimentService.saveCompliment(new ComplimentModel(complimentDescription, userModel));

			} else if (opc.equals("2")) {
				System.out.println("Descreva a recalamação: ");
				String protestDescription = sc.nextLine();
				protestService.saveProtest(new ProtestModel(protestDescription, userModel));

			} else if (opc.equals("3")) {
				System.out.println(complimentService.findAllComplimentsByUserId(userModel.getId()));

			} else if (opc.equals("4")) {
				System.out.println(protestService.findAllProtestsByUserId(userModel.getId()));
			} else if (opc.equals("5")) {
				System.out.println("Finalizando... Obrigado!");
				System.exit(0);
			}
		}
	} else if (isLoggedInAsAdmin) {
		System.out.println(showAdminMenu());
		System.out.print("Opção: ");
		opc = sc.nextLine();

		if (opc.equals("1")) {
			System.out.println("Reclamações:");
			printFormatter.formatProtestsOutput(protestService.findAllProtests());
		} else if (opc.equals("2")) {
			System.out.println("Elogios:");
			printFormatter.formatComplimentsOutput(complimentService.findAllCompliments());
		} else if (opc.equals("3")) {
			System.out.println("Reclamações:");
			printFormatter.formatProtestsOutput(protestService.findAllProtests());
			System.out.println("Elogios:");
			printFormatter.formatComplimentsOutput(complimentService.findAllCompliments());
		} else if (opc.equals("4")) {

		} else if (opc.equals("5")) {

		} else if (opc.equals("6")) {

		} else if (opc.equals("7")) {

		} else if (opc.equals("8")) {

		} else if (opc.equals("9")) {

		}
	}


	}

	public String showLoginMenu() {
		return """
				1. Cadastrar Usuário Padrão
				2. Cadastrar Usuário Administrador
				3. Realizar login como Usuário Padrão
				4. Realizar login como Usuário Administrador
				5. Sair
				""";
	}

	public String showAdminMenu() {
		return """
				1. Pesquisar todas as reclamações
				2. Pesquisar todas os elogios
				3. Pesquisar todas as reclamações e elogios
				4. Excluir uma reclamação
				5. Excluir um elogio
				6. Excluir todas as reclamações
				7. Excluir todos os elogios
				8. Excluir todas as reclamações e elogios
				9. Sair
				""";
	}

	public String showDefaultUserMenu() {
		return """
				1. Cadastrar elogio
				2. Cadastrar reclamação
				3. Pesquisar elogios
				4. Pesquisar reclamações
				5. Sair
				""";
	}
}
