1. Mysql DB Library_db Database 생성
2. Table books, rentals, users 생성
   -- Libraly.books definition

CREATE TABLE `books` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '도서 고유번호 (내부 관리용 PK)',
  `isbn` varchar(20) DEFAULT NULL COMMENT '국제표준도서번호',
  `bookname` varchar(255) NOT NULL COMMENT '도서명',
  `author` varchar(100) DEFAULT NULL COMMENT '저자',
  `publisher` varchar(100) DEFAULT NULL COMMENT '출판사',
  `price` int DEFAULT '0' COMMENT '도서 가격',
  `total_count` int DEFAULT '1' COMMENT '총 보유 수량',
  `available_count` int DEFAULT '1' COMMENT '현재 대출 가능한 수량',
  `createdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
  `createuser` varchar(50) DEFAULT NULL COMMENT '생성자',
  `modifydate` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
  `modifyuser` varchar(50) DEFAULT NULL COMMENT '수정자',
  PRIMARY KEY (`id`),
  UNIQUE KEY `isbn` (`isbn`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='도서 마스터 정보';

-- Libraly.rentals definition

CREATE TABLE `rentals` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '대출/반납 거래 고유번호',
  `user_id` int NOT NULL COMMENT '대출한 고객 ID (FK)',
  `book_id` int NOT NULL COMMENT '대출된 도서 ID (FK)',
  `rent_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '대출 일시',
  `due_date` datetime NOT NULL COMMENT '반납 예정일',
  `return_date` datetime DEFAULT NULL COMMENT '실제 반납 일시',
  `status` varchar(20) DEFAULT 'RENTED' COMMENT '상태 (RENTED:대출중, RETURNED:반납완료, OVERDUE:연체중, LOST:분실)',
  `overdue_days` int DEFAULT '0' COMMENT '연체 일수',
  `overdue_fee` int DEFAULT '0' COMMENT '연체 금액',
  `createdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
  `createuser` varchar(50) DEFAULT NULL COMMENT '생성자',
  `modifydate` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
  `modifyuser` varchar(50) DEFAULT NULL COMMENT '수정자',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `rentals_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `rentals_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='대출 및 반납 이력';

-- Libraly.users definition

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '고객 고유번호 (내부 관리용 PK)',
  `userid` varchar(50) NOT NULL COMMENT '로그인 아이디',
  `userpw` varchar(255) NOT NULL COMMENT '비밀번호',
  `username` varchar(50) NOT NULL COMMENT '고객명',
  `birth_date` date DEFAULT NULL COMMENT '생년월일',
  `gender` enum('M','F','O') DEFAULT NULL COMMENT '성별 (M:남, F:여, O:기타)',
  `email` varchar(100) DEFAULT NULL COMMENT '이메일',
  `phone` varchar(20) DEFAULT NULL COMMENT '연락처 (예: 010-1234-5678)',
  `address1` varchar(255) DEFAULT NULL COMMENT '기본주소',
  `address2` varchar(255) DEFAULT NULL COMMENT '상세주소',
  `useyn` char(1) DEFAULT 'Y' COMMENT '사용여부 (Y/N)',
  `role` varchar(20) DEFAULT 'USER' COMMENT '권한 (USER/ADMIN)',
  `penalty_end_date` date DEFAULT NULL COMMENT '연체 등으로 인한 대출 정지 해제일',
  `createdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `createuser` varchar(50) DEFAULT NULL COMMENT '생성자',
  `modifydate` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
  `modifyuser` varchar(50) DEFAULT NULL COMMENT '수정자',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userid` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='고객 정보';

3. Library\Library\src\main\java\com\example\Library\LibraryApplication.java 선택 
4. F5 클릭
