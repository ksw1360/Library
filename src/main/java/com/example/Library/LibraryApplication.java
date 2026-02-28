package com.example.Library;

import java.util.Scanner;
import com.example.Library.DTO.BookDTO;
import com.example.Library.Service.LibraryService;
import java.util.List;

public class LibraryApplication {

	public static void main(String[] args) {
		LibraryService libraryService = new LibraryService();
		Scanner scanner = new Scanner(System.in, "cp949");
		boolean isRunning = true;

		System.out.println("=========================================");
		System.out.println("   📚 도서관 관리 시스템 V2              ");
		System.out.println("=========================================");
		List<BookDTO> books = null;
		while (isRunning) {
			System.out.println("\n[0] 도서 등록 [1] 도서 목록  [2] 대여  [3] 반납  [4] 대여 리스트  [5] 종료");
			System.out.print("▶ 메뉴 선택: ");
			String choice = scanner.nextLine();

			switch (choice) {
				case "0":
					try {
						System.out
								.println("\n=========================================================================");
						// 자동증가라 필요가 없음
						// System.out.println("도서 ID를 입력하세요");
						// int bookId = Integer.parseInt(scanner.nextLine());

						System.out.println("도서 ISBN을 입력하세요");
						String ISBN = scanner.nextLine();
						System.out.println("도서명을 입력하세요");
						String bookName = scanner.nextLine();
						if (bookName == null || bookName.trim().isEmpty()) {
							System.out.println("경고: 도서명은 비워둘 수 없습니다! 등록을 취소합니다.");
							break; // switch문을 빠져나가서 메인 메뉴로 돌아감
						}

						System.out.println("도서 저자를 입력하세요");
						String author = scanner.nextLine();

						System.out.println("도서 입고 전체 수량을 입력하세요");
						int totalcount = Integer.parseInt(scanner.nextLine());

						System.out.println("도서 입고 수량을 입력하세요");
						int count = Integer.parseInt(scanner.nextLine());

						System.out.println("도서 상태를 입력하세요");
						String status = scanner.nextLine();
						boolean chk = libraryService.insertBook(ISBN, bookName, author, status, totalcount,
								count);
						if (chk) {
							System.out.println("✅ 신규 도서 등록 완료");
						} else {
							System.out.println("❌ 신규 도서 등록 실패");
						}
					} catch (NumberFormatException e) {
						System.out.println("⚠️ 오류: ID는 반드시 숫자만 입력해주세요!");
					}
					break;
				case "1":
					System.out.println("\n" + "=".repeat(110));
					// 제목 줄도 고정 폭으로 출력
					System.out.printf("%s | %s | %s | %s | %s | %s\n",
							format("ID", 4), format("ISBN", 15), format("도서명", 50),
							format("저자", 15), format("재고", 6), format("상태", 10));
					System.out.println("-".repeat(110));

					books = libraryService.bookDAO.getAllBooks();
					for (BookDTO book : books) {
						System.out.printf("%s | %s | %s | %s | %s | %s\n",
								format(String.valueOf(book.getId()), 4),
								format(book.getIsbn(), 15),
								format(book.getBookname(), 50),
								format(book.getAuthor(), 15),
								format(book.getAvailable_count() + "권", 6),
								format(book.getStatus(), 10));
					}
					System.out.println("=".repeat(110));
					break;
				case "2":
					System.out.println("\n>> 📝 대여 처리를 시작합니다.");
					try {
						System.out.print(">> 빌릴 도서 고유번호(ID): ");
						var bookId = Integer.parseInt(scanner.nextLine());

						System.out.print(">> 대여하는 회원 번호(ID): ");
						int memberId = Integer.parseInt(scanner.nextLine());

						if (libraryService.rentBook(bookId, memberId)) {
							System.out.println("✅ 대여 성공! (수량 차감 & 대여 장부 기록 완료)");
						} else {
							System.out.println("❌ 대여 실패! (도서가 없거나 재고 소진)");
						}
					} catch (NumberFormatException e) {
						// 문자를 입력해서 에러가 나면 프로그램이 종료되지 않고 여기로 빠집니다!
						System.out.println("⚠️ 오류: ID는 반드시 숫자만 입력해주세요!");
					}
					break;

				case "3":
					try {
						// ... 반납도 동일하게 int bookId 로 입력받기 ... (생략)
						System.out.print(">> 반납 도서 고유번호(ID): ");
						var bookId = Integer.parseInt(scanner.nextLine()); // 🌟 1, 2, 3 입력!

						System.out.print(">> 반납하는 회원 번호(ID): ");
						var memberId = Integer.parseInt(scanner.nextLine()); // 🌟 1, 2 입력!
						if (libraryService.returnBook(bookId)) {
							System.out.println("✅ 반납 성공! (수량 증가 & 대여 장부 업데이트 완료)");
						} else {
							System.out.println("❌ 반납 실패! (존재하지 않는 도서 또는 대여 기록 없음)");
						}
					} catch (NumberFormatException e) {
						// 문자를 입력해서 에러가 나면 프로그램이 종료되지 않고 여기로 빠집니다!
						System.out.println("⚠️ 오류: ID는 반드시 숫자만 입력해주세요!");
					}
					break;
				case "4":
					System.out.println("\n>> 📋 현재 대여 리스트");
					libraryService.getRentalList();
					for (String rentalInfo : libraryService.getRentalList()) {
						System.out.println(rentalInfo);
					}
					System.out.println("=".repeat(110));
					break;
				case "5":
					isRunning = false;
					break;
			}
		}
		scanner.close();

	}

	public static String format(String s, int width) {
		int curWidth = 0;
		for (char c : s.toCharArray()) {
			curWidth += (c > 127) ? 2 : 1; // 한글(Non-ASCII)이면 2칸, 아니면 1칸
		}
		return s + " ".repeat(Math.max(0, width - curWidth));
	}
}