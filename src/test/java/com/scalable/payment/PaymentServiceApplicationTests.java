package com.scalable.payment;

import com.scalable.payment.controller.PaymentControllerTest;
import com.scalable.payment.repository.PaymentRepositoryTest;
import com.scalable.payment.service.PaymentServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PaymentServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testController() {
		PaymentControllerTest controllerTest = new PaymentControllerTest();
		try {
			controllerTest.testCreateNewPaymentEndpoint();
		} catch (Exception e) {
			System.out.println("Test failed at Payment Controller - CreateNewPayment");
			System.out.println(e.getMessage());
		}

		try {
			controllerTest.testCreateNewDefaultPaymentEndpoint();
		} catch (Exception e) {
			System.out.println("Test failed at Payment Controller - CreateNewDefaultPayment");
			System.out.println(e.getMessage());
		}

		try {
			controllerTest.testGetBalanceEndpoint();
		} catch (Exception e) {
			System.out.println("Test failed at Payment Controller - GetBalance");
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testRepository() {
		PaymentRepositoryTest repositoryTest = new PaymentRepositoryTest();
		try {
			repositoryTest.testSaveAndFindByUsername();
		} catch (Exception e) {
			System.out.println("Test failed at Payment Repository - SaveAndFindByUsername");
			System.out.println(e.getMessage());
		}

		try {
			repositoryTest.testFindByNonexistentUsername();
		} catch (Exception e) {
			System.out.println("Test failed at Payment Repository - FindByNonexistentUsername");
			System.out.println(e.getMessage());
		}

	}

	@Test
	public void testService() {
		PaymentServiceTest serviceTest = new PaymentServiceTest();
		try {
			serviceTest.testCreateDefaultPayment();
		} catch (Exception e) {
			System.out.println("Test failed at Payment Service - CreateDefaultPayment");
			System.out.println(e.getMessage());
		}

		try {
			serviceTest.testCreateNewPayment();
		} catch (Exception e) {
			System.out.println("Test failed at Payment Service - CreateNewPayment");
			System.out.println(e.getMessage());
		}

		try {
			serviceTest.testGetUser();
		} catch (Exception e) {
			System.out.println("Test failed at Payment Service - GetUser");
			System.out.println(e.getMessage());
		}
	}

}
