package product.view;

import java.util.List;
import java.util.Scanner;

import product.controller.ProductController;
import product.model.vo.Product;
import product.model.vo.ProductIO;

public class ProductMenu {
	Scanner sc = new Scanner(System.in);
	ProductController productController = new ProductController();
	
	public void mainMenu() {

		String menu = "***** 상품 재고관리 프로그램 *****\n"
				+ "1. 전체상품조회\n"
				+ "2. 상품검색\n"
				+ "3. 상품등록\n"
				+ "4. 상품정보변경\n"
				+ "5. 상품삭제\n"
				+ "6. 상품입/출고 메뉴\n"
				+ "0. 프로그램 종료\n"
				+ "********************************\n"
				+ "> 선택: ";
		
		while(true) {
			System.out.print(menu);
			String choice = sc.next();
			ProductController productController = new ProductController();
			
			List<Product> list = null;
			Product product = null;
			String id = null;
			int result = 0;
			
			switch(choice) {
			case "1" : 
				list = productController.selectAll();
				printProductList(list);
				break;
			case "2" : 
				findProduct();
				break;
			case "3" : 
				product = inputProduct();
				result = productController.insertProduct(product);
				printResultMsg(result, "제품 등록 성공!", "제품 등록 실패!");
				break;
			case "4" : 
				updateProduct(singleoutProduct("수정"));
				break;
			case "5" : 
				id = inputId();
				result = productController.deleteProduct(id);
				printResultMsg(result, "제품 삭제 성공!", "제품 삭제 실패!");
				break;
			case "6" : 
				productIO();
				break;
			case "0" : 
				return;
			}
		}
	}

	private void productIO() {
		String IOMenu = "***** 상품입출고 메뉴*****\n"
					  + "1. 입출고내역조회\n"
					  + "2. 상품입고\n"
					  + "3. 상품출고\n"
					  + "0. 메인메뉴로 돌아가기\n"
					  + "***********************\n"
					  + "> 선택: ";
		
		while(true) {
			System.out.print(IOMenu);
			String choice = sc.next();
			String id = null;
			int result = 0;
			List<ProductIO> list = null;
			Product p = null;
			
			switch(choice) {
			case "1": 
				id = singleoutProduct("입출고 내역을 조회");
				list = productController.selectProductIOAll(id);
				printProductIOList(list);
				break;
			case "2": 
				id = singleoutProduct("입고");
				System.out.print("입고 수량을 입력하세요: ");
				int inputNum = sc.nextInt();
				result = productController.inputProduct(id, inputNum);
				printResultMsg(result, "상품 입고 성공!", "상품 입고 실패!");
				break;
			case "3": 
				id = singleoutProduct("출고");
				System.out.print("출고 수량을 입력하세요: ");
				int outputNum = sc.nextInt();
				p = productController.selectOne("id", id).get(0);
				
				if(p.getStock() < outputNum) {
					System.out.println("재고가 출고수량보다 적습니다!");
					break;
				}
				else {
					result = productController.outputProduct(id, outputNum);
					printResultMsg(result, "상품 출고 성공!", "상품 출고 실패!");
					break;					
				}
			case "0": return;
			default: 
				System.out.println("잘못 누르셨습니다.");
				continue;
			}
		}
	}

	private String singleoutProduct(String string) {
		Product product = null;
		List<Product> list = productController.selectAll();
		
		do {
			for(int i = 0; i < list.size(); i++) {
				Product p = list.get(i);
				System.out.printf("%d. %s[%s] - %s\n", i+1, p.getName(), p.getBrand(), p.getId());
			}
			System.out.println("0. 전체메뉴로 돌아가기");
			System.out.println("---------------------------------");
			System.out.printf("%s할 상품의 번호를 선택하세요: ", string);
			int index = sc.nextInt() - 1;
						
			if(index < 0 || list.size() <= index)
				return null;
			else
				product = list.get(index);
		} while(product == null);
			
		return product.getId();
	}

	private void updateProduct(String id) {
		if(id == null) {
			return;
		}
		else {
			List<Product> productList = productController.selectOne("id", id);
			Product product = productList.get(0);
			
			String updateMenu = "***** 상품정보 변경 메뉴 *****\n"
					+ "1. 상품명변경\n"
					+ "2. 가격변경\n"
					+ "3. 사양변경\n"
					+ "0. 메인메뉴로 돌아가기\n"
					+ "**************************\n"
					+ "> 선택: ";
			
			while(true) {
				System.out.print(updateMenu);
				String choice = sc.next();
				
				switch(choice) {
				case "1": 
					sc.nextLine();
					System.out.print("변경할 상품명을 입력하세요: ");
					product.setName(sc.nextLine());
					break;
				case "2": 
					System.out.print("변경할 가격을 입력하세요: ");
					product.setPrice(sc.nextInt());
					break;
				case "3": 
					System.out.print("변경할 모니터 사이즈를 입력하세요(인치): ");
					product.setMonitor_size(sc.nextInt());
					System.out.print("변경할 Os를 입력하세요: ");
					product.setOs(sc.next());
					System.out.print("변경할 용량을 입력하세요: ");
					product.setStorage(sc.nextInt());
					break;
				case "0": return;
				default : 
					System.out.println("잘못 입력하셨습니다.");
					continue;
				}
				System.out.println();
				int result = productController.updateProduct(product);
				printResultMsg(result, "정보 수정 성공!", "정보 수정 실패!");
				printProduct(product);
			}
			
		}
		
		
	}

	private Product inputProduct() {
		Product product = new Product();
		System.out.println("------ 상품 등록 ------");
		System.out.print("아이디: ");
		product.setId(sc.next());
		System.out.print("브랜드: ");
		product.setBrand(sc.next());
		sc.nextLine();
		System.out.print("이름: ");
		product.setName(sc.nextLine());
		System.out.print("가격: ");
		product.setPrice(sc.nextInt());
		System.out.print("모니터 크기: ");
		product.setMonitor_size(sc.nextInt());
		System.out.print("운영체제: ");
		product.setOs(sc.next());
		System.out.print("저장공간: ");
		product.setStorage(sc.nextInt());
		
		return product;
	}

	private void findProduct() {
		String findMenu = "***** 상품검색 메뉴 *****\n"
						+ "1. 아이디 검색\n"
						+ "2. 상품명 검색\n"
						+ "0. 메인메뉴로 돌아가기\n"
						+ "***************************\n"
						+ "> 선택: ";
		
		while(true) {
			System.out.print(findMenu);
			String choice = sc.next();
			
			String searchId = null;
			String searchName = null;
			
			List<Product> list = null;
			
			switch(choice) {
			case "1" : 
				String id = "id";
				searchId = inputId();
				list = productController.selectOne(id, searchId);
				printProductList(list);
				break;
			case "2" :
				String name = "name";
				searchName = inputName();
				list = productController.selectOne(name, searchName);
				printProductList(list);
				break;
			case "0" : return;
			}			
		}
	}

	private void printResultMsg(int result, String successMsg, String failureMsg) {
		if(result > 0) 
			System.out.println(successMsg);
		else
			System.out.println(failureMsg);
	}

	private String inputId() {
		System.out.println("-----------------------");
		System.out.print("> 아이디 입력: ");
		return sc.next();
	}
	
	private String inputName() {
		System.out.println("-----------------------");
		System.out.print("> 이름 입력: ");
		return sc.next();
	}

	private void printProductList(List<Product> list) {
		
		if(list == null || list.isEmpty()) {
			System.out.println("조회된 행이 없습니다.");
		}
		else {
			System.out.println("----------------------------------------------------------------------------------------------------------------");
			System.out.println("\tid\t\tbrand\t\tname\t\tprice\t\t\tspec\t\t\tstock\t");
			System.out.println("----------------------------------------------------------------------------------------------------------------");

			for(Product p : list) {				
				System.out.printf("%15s%15s%15s%15d%10d인치 / %s / %dGB %10d\n",
						   p.getId(), p.getBrand(), p.getName(), p.getPrice(),
						   p.getMonitor_size(), p.getOs(), p.getStorage(), p.getStock());

			}
			
			System.out.println("----------------------------------------------------------------------------------------------------------------");
			System.out.println();			
		}
		
	}
	
	private void printProductIOList(List<ProductIO> list) {
		if(list == null || list.isEmpty()) {
			System.out.println("조회된 행이 없습니다.");
		}
		else {
			System.out.println("-------------------------------------------------------------");
			System.out.println("No\tProduct_id\tCount\tStatus\tio_DateTime");
			System.out.println("-------------------------------------------------------------");
			for(ProductIO p : list) {
				System.out.printf("%d\t%10s\t%5d\t%5s\t%10s\t\n",
								  p.getNo(), p.getProduct_id(), p.getCount(),
								  p.getStatus(), p.getIo_datetime());
			}
		}
	}
	
	private void printProduct(Product product) {
		if(product == null) {
			System.out.println("조회된 제품이 없습니다.");
			System.out.println();
		}
		else {
			System.out.println("------ 상품 정보 ------");
			System.out.printf("id: %s\n", product.getId());
			System.out.printf("Brand: %s\n", product.getBrand());
			System.out.printf("Name: %s\n", product.getName());
			System.out.printf("Price: %d\n", product.getPrice());
			System.out.printf("Monitor Size: %d인치\n", product.getMonitor_size());
			System.out.printf("Os: %s\n", product.getOs());
			System.out.printf("Storage: %dGB\n", product.getStorage());
			System.out.printf("Stock: %d\n", product.getStock());
			System.out.println("---------------------------");
		}
	}
}
