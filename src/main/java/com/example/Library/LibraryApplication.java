package com.example.Library;

import java.util.Scanner;
import com.example.Library.DTO.BookDTO;
import com.example.Library.Service.LibraryService;
import java.util.List;

public class LibraryApplication {

	public static void main(String[] args) {
		LibraryService libraryService = new LibraryService();
		Scanner scanner = new Scanner(System.in);
		boolean isRunning = true;

		System.out.println("=========================================");
		System.out.println("   📚 도서관 관리 시스템 V2 (순수 JDBC)  ");
		System.out.println("=========================================");

		while (isRunning) {
			System.out.println("\n[1] 도서 목록  [2] 대여  [3] 반납  [0] 종료");
			System.out.print("▶ 메뉴 선택: ");
			String choice = scanner.nextLine();

			switch (choice) {
				case "1":
					System.out.println("\n=========================================================================");
					System.out.println("ID\t| ISBN\t\t| 도서명\t\t| 저자\t| 재고\t| 상태");
					System.out.println("-------------------------------------------------------------------------");
					List<BookDTO> books = libraryService.bookDAO.getAllBooks();
					for (BookDTO book : books) {
						System.out.printf("%d\t| %s\t| %s\t| %s\t| %d권\t| %s\n",
								book.getId(), book.getIsbn(), book.getBookname(), book.getAuthor(),
								book.getAvailable_count(), book.getStatus());
					}
					System.out.println("=========================================================================");
					break;

				case "2":
					System.out.println("\n>> 📝 대여 처리를 시작합니다.");
					System.out.print(">> 빌릴 도서 고유번호(ID): ");
					int bookId = Integer.parseInt(scanner.nextLine()); // 🌟 1, 2, 3 입력!

					System.out.print(">> 대여하는 회원 번호(ID): ");
					int memberId = Integer.parseInt(scanner.nextLine()); // 🌟 1, 2 입력!

					if (libraryService.rentBook(bookId, memberId)) {
						System.out.println("✅ 대여 성공! (수량 차감 & 대여 장부 기록 완료)");
					} else {
						System.out.println("❌ 대여 실패! (도서가 없거나 재고 소진)");
					}
					break;

				case "3":
					// ... 반납도 동일하게 int bookId 로 입력받기 ... (생략)
					System.out.print(">> 반납 도서 고유번호(ID): ");
					bookId = Integer.parseInt(scanner.nextLine()); // 🌟 1, 2, 3 입력!

					System.out.print(">> 반납하는 회원 번호(ID): ");
					memberId = Integer.parseInt(scanner.nextLine()); // 🌟 1, 2 입력!
					if (libraryService.returnBook(bookId)) {
						System.out.println("✅ 반납 성공! (수량 증가 & 대여 장부 업데이트 완료)");
					} else {
						System.out.println("❌ 반납 실패! (존재하지 않는 도서 또는 대여 기록 없음)");
					}
					break;
				case "0":
					isRunning = false;
					break;
			}
		}
		scanner.close();
	}
}