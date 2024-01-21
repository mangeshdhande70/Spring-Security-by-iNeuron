package in.ineuron.nitin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BankOperationController {
	static {
		System.out.println("BankOperationController.class file is loading...");
	}

	public BankOperationController() {
		System.out.println("BankOperationController :: Zero param constructor...");
	}
	
	@GetMapping("/")
	public String showHome() {
		System.out.println("BankOperationController.showHome()");
		return "home";
	}
	
	@GetMapping("/offers")
	public String showOffers() {
		return "offers";
	}
	
	@GetMapping("/balance")
	public String checkBalance() {
		return "show_balance";
	}
	
	@GetMapping("/loanApprove")
	public String approveLoan() {
		return "loan";
	}
	
	@GetMapping("/denied")
	public String accessDenied() {
		return "access_denied";
	}
	
}
