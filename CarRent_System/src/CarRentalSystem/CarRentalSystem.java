package CarRentalSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarRentalSystem {

	private List<Car> cars;
	private List<Customer> customers;
	private List<Rental> rentals;

	public CarRentalSystem() {
		cars = new ArrayList<>();
		customers = new ArrayList<>();
		rentals = new ArrayList<>();
	}

	public void addCar(Car car) {
		cars.add(car);
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	public void rentCar(Car car, Customer customer, int days) {
		if (car.isAvailable()) {
			car.rent();
			rentals.add(new Rental(car, customer, days));
		} else {
			System.out.println("Car is not available for rent!");
		}
	}

	public void returnCar(Car car) {
		Rental rentalToRemove = null;

		for (Rental rental : rentals) {
			if (rental.getCar().equals(car)) {
				rentalToRemove = rental;
				break;
			}
		}

		if (rentalToRemove != null) {
			rentals.remove(rentalToRemove);
			car.returnCar();
			System.out.println("Car returned successfully");
		} else {
			System.out.println("Car was not rented");
		}
	}

	public void menu() {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("========== Car Rental System ==========");
			System.out.println("1. Rent a Car");
			System.out.println("2. Return a Car");
			System.out.println("3. Exit");
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // move cursor to next line

			if (choice == 1) {
				System.out.println("========== Rent a Car ==========");
				System.out.print("Enter your name: ");
				String customerName = scanner.nextLine();

				System.out.println("Available Cars: ");
				for (Car car : cars) {
					if (car.isAvailable()) {
						System.out.println(car.getCarId() + " - " + car.getBrand() + " - " + car.getModel());
					}
				}

				System.out.print("Enter the car ID you want to rent: ");
				String carId = scanner.nextLine();

				System.out.print("Enter the number of days for rental: ");
				int rentalDays = scanner.nextInt();
				scanner.nextLine();

				Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
				addCustomer(newCustomer);

				Car selectedCar = null;
				for (Car car : cars) {
					if (car.getCarId().equals(carId) && car.isAvailable()) {
						selectedCar = car;
						break;
					}
				}

				if (selectedCar != null) {
					double totalPrice = selectedCar.calculatePrice(rentalDays);
					System.out.println("========= Rental Information =========");
					System.out.println("Customer ID: " + newCustomer.getCustomerId());
					System.out.println("Customer Name: " + newCustomer.getName());
					System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
					System.out.println("Rental Days: " + rentalDays);
					System.out.println("Total Price: " + totalPrice);

					System.out.print("Confirm rental (Y/N): ");
					String confirm = scanner.nextLine();

					if (confirm.equalsIgnoreCase("Y")) {
						rentCar(selectedCar, newCustomer, rentalDays);
						System.out.println("Car rented successfully.");
					} else {
						System.out.println("Rental Cancelled");
					}
				} else {
					System.out.println("Invalid car selection or car not available for rent.");
				}
			} else if (choice == 2) {
				System.out.println("========== Return a Car ==========");
				System.out.print("Enter the car ID you want to return: ");
				String carId = scanner.nextLine();

				Car carToReturn = null;
				for (Car car : cars) {
					if (car.getCarId().equals(carId) && !car.isAvailable()) {
						carToReturn = car;
						break;
					}
				}

				if (carToReturn != null) {
					returnCar(carToReturn);
				} else {
					System.out.println("Invalid car ID or car is not rented.");
				}
			} else if (choice == 3) {
				break;
			} else {
				System.out.println("Invalid choice. Please enter a valid option.");
			}
		}

		System.out.println("Thank you for using the Car Rental System");
		scanner.close();
	}
}
