package com.unifacisa.ouvidoriacelgon;

import com.unifacisa.ouvidoriacelgon.enums.UserTypeEnum;
import com.unifacisa.ouvidoriacelgon.exceptions.CpfAlreadyTakenException;
import com.unifacisa.ouvidoriacelgon.exceptions.InvalidCpfException;
import com.unifacisa.ouvidoriacelgon.exceptions.InvalidLoginCredentials;
import com.unifacisa.ouvidoriacelgon.exceptions.UsernameAlreadyTakenException;
import com.unifacisa.ouvidoriacelgon.models.ComplimentModel;
import com.unifacisa.ouvidoriacelgon.models.ProtestModel;
import com.unifacisa.ouvidoriacelgon.models.UserModel;
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
	private final ProtestService protestService;
	@Autowired
	private final ComplimentService complimentService;
	@Autowired
	private final UserService userService;
	@Autowired
	private final PrintFormatter printFormatter;

	public OuvidoriaCelgonApplication(ProtestService protestService, ComplimentService complimentService, UserService userService, PrintFormatter printFormatter) {
		this.protestService = protestService;
		this.complimentService = complimentService;
		this.userService = userService;
		this.printFormatter = printFormatter;
	}

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
				if (complimentService.findAllComplimentsByUserId(userModel.getId()).size() > 0) {
					printFormatter.formatComplimentsOutput(complimentService.findAllComplimentsByUserId(userModel.getId()));
				} else {
					System.out.println("Não há elogios cadastrados em seu nome.");
				}

			} else if (opc.equals("4")) {
				if (protestService.findAllProtestsByUserId(userModel.getId()).size() > 0) {
					printFormatter.formatProtestsOutput(protestService.findAllProtestsByUserId(userModel.getId()));
				} else {
					System.out.println("Não há reclamações cadastradas em seu nome.");
				}
			} else if (opc.equals("5")) {
				System.out.println("Finalizando... Obrigado!");
				System.exit(0);
			}
		}

	} else if (isLoggedInAsAdmin) {
		while (!Objects.equals(opc, "9")) {
			System.out.println(showAdminMenu());
			System.out.print("Opção: ");
			opc = sc.nextLine();

			if (opc.equals("1")) {
				if (protestService.findAllProtests().size() > 0) {
					System.out.println("Reclamações:");
					printFormatter.formatProtestsOutput(protestService.findAllProtests());
				} else {
					System.out.println("Não há reclamações cadastradas");
				}

			} else if (opc.equals("2")) {
				if (complimentService.findAllCompliments().size() > 0) {
					System.out.println("Elogios:");
					printFormatter.formatComplimentsOutput(complimentService.findAllCompliments());
				} else {
					System.out.println("Não há elogios cadastradas");
				}

			} else if (opc.equals("3")) {
				if (protestService.findAllProtests().size() > 0) {
					System.out.println("Reclamações:");
					printFormatter.formatProtestsOutput(protestService.findAllProtests());
				} else {
					System.out.println("Não há reclamações cadastradas");
				}

				if (complimentService.findAllCompliments().size() > 0) {
					System.out.println("Elogios:");
					printFormatter.formatComplimentsOutput(complimentService.findAllCompliments());
				} else {
					System.out.println("Não há elogios cadastradas");
				}

			} else if (opc.equals("4")) {
				if (protestService.findAllProtests().size() > 0) {
					System.out.println("Reclamações:");
					printFormatter.formatProtestsOutput(protestService.findAllProtests());

					try {
						System.out.print("Id da reclamação a ser excluída: ");
						Long id = Long.parseLong(sc.nextLine());
						protestService.deleteProtestById(id);
					} catch (Exception e) {
						System.out.println("Id inválido.");
					}

				} else {
					System.out.println("Não há reclamações cadastradas");
				}

			} else if (opc.equals("5")) {
				if (complimentService.findAllCompliments().size() > 0) {
					System.out.println("Elogios:");
					printFormatter.formatComplimentsOutput(complimentService.findAllCompliments());

					try {
						System.out.print("Id do elogio a ser excluído: ");
						Long id = Long.parseLong(sc.nextLine());
						complimentService.deleteComplimentById(id);
					} catch (Exception e) {
						System.out.println("Id inválido.");
					}

				} else {
					System.out.println("Não há elogios cadastrados");
				}

			} else if (opc.equals("6")) {
				if (protestService.findAllProtests().size() > 0) {
					System.out.println("Tem certeza que deseja excluir todas as reclamações registradas? A ação é irreversível");
					System.out.println("""
                              1. Sim
                              2. Não
                        """);
					System.out.print("Opção: ");
					String confirm = sc.nextLine();
					if (confirm.equals("1")) {
						protestService.deleteAllProtests();
					}
				} else {
					System.out.println("Não há reclamações cadastradas");
				}

			} else if (opc.equals("7")) {
				if (complimentService.findAllCompliments().size() > 0) {
					System.out.println("Tem certeza que deseja excluir todos os elogios registrados? A ação é irreversível");
					System.out.println("""
                              1. Sim
                              2. Não
                        """);
					System.out.print("Opção: ");
					String confirm = sc.nextLine();
					if (confirm.equals("1")) {
						complimentService.deleteAllCompliments();
					}
				} else {
					System.out.println("Não há elogios cadastrados");
				}

			} else if (opc.equals("8")) {
				if (protestService.findAllProtests().size() > 0 || complimentService.findAllCompliments().size() > 0) {
					System.out.println("Tem certeza que deseja excluir todas as manifestações? A ação é irreversível");
					System.out.println("""
                              1. Sim
                              2. Não
                        """);
					System.out.print("Opção: ");
					String confirm = sc.nextLine();
					if (confirm.equals("1")) {
						protestService.deleteAllProtests();
						complimentService.deleteAllCompliments();
					}
				} else {
					System.out.println("Não há elogios ou reclamações registrados");
				}

			} else if (opc.equals("9")) {
				System.out.println("Finalizando... Obrigado!");
				System.exit(0);
			}
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
