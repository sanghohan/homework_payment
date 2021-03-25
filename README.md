##서버사전과제1_결제시스템
### 개발 프레임워크
* openjdk1.8
* Spring Boot JPA 2.43
* H2 Database
* Maven

### 테이블 설계
![db_schema](https://user-images.githubusercontent.com/8196614/112402875-adefb500-8d50-11eb-9377-9dc87fedc899.png)

### 문제 해결 전략
* 기본 결제 취소 로직 : 결제 테이블 한 ROW에 취소 합계, VAT 금액 저장, 취소 ROW에 원결제 txId 저장
* 20자리 unique key : System.currentTimeMillis() + RandomString 7자리
* Masking : 제외 하는 영역이 아닌 Masking은 해야하는 영역을 인자값으로 받도록 함, Custom Annotation을 사용
* 카드사 연동 String Data : Custom Annotation을 사용
* 동시성 문제 해결 : concurrent 클래스를 이용함

### 빌드, 실행 방법
1. IDE에서 빌드, HomworkPaymentApplication.java의 메인함수 실행
2. mvn clean package -DskipTests 실행 후 target 경로에서
   java -jar homework_payment-0.0.1-SNAPSHOT.jar 실행

### DB 접속
* Application 실행 후, http://localhost:8080/h2-console/ 접속
* Driver Class: org.h2.Driver
* JDBC URL: jdbc:h2:mem:payment
* User Name: sa
* Password : N/A (없음)

### API
* 결제 : (POST) http://localhost:8080/payments/v1/pay
<br><br>
  request sample <br><br>
  {
  "cardNum":"151361324432",
  "validPeriod":"0522",
  "cvc":"123",
  "installmentMonths":0,
  "payAmount":20000,
  "vat" : 909
  }
  <br><br>
  result sample <br><br>
  {
  "txId": "1616637066989RrUDsY0",
  "stringData": " 416PAY       1616637066989RrUDsY0151361324432        000522123     200000000000909                    sy32TwjsePD6fRa715qyoC/wALTg1sBfo8xNikLSf4A=                                                                                                                                                                                                                                                                                                               "
  }
  <br><br>
* 취소 : (POST) http://localhost:8080/payments/v1/cancel
<br><br>
  request sample <br><br>
  {
  "txId" : "1616568935187uVnHUh7",
  "cancelAmount" : 10000,
  "cancelVat" : 0
} <br><br>
  result sample <br><br>
  {
  "txId": "1616637189729ux1zaGt",
  "stringData": " 416CANCEL    1616637189729ux1zaGt151361324432        000522123     100000000000000                    sy32TwjsePD6fRa715qyoC/wALTg1sBfo8xNikLSf4A=                                                                                                                                                                                                                                                                                                               "
  }<br><br>
  
* 조회 : (GET) http://localhost:8080/payments/v1/1616568935187uVnHUh7
  <br><br>
  result sample <br><br>
{
"txId": "1616637066989RrUDsY0",
"cardDataVo": {
"cardNum": "151361***432",
"validPeriod": "0522",
"cvc": "123"
},
"payType": "PAY",
"status": "PARTIALLY_CANCELLED",
"payAmountInfo": {
"transactionAmount": 20000,
"vat": 909
},
"orgTxId": null,
"transactionDt": "2021-03-25T10:53:09.74"
}
  