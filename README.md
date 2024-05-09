# 주요 공부 내용
- 외부 API 를 호출해 가져온 데이터를 DB 에 저장
- Jenkins를 이용한 CI•CD 구현, Https 적용

# 주요 기술 스택
- `Java 17`
- `SpringBoot 3.1.2.RELEASE`
- `SpringSecurity 6.1.2`
- `QueryDsl 5.0.0`
- `AWS EB`
- `Jenkins`
### DB
- `MySQL`
### Swagger URL
- [https://hrs.o-r.kr](https://hrs.o-r.kr)
* AWS RDS 의 정책 변화로 인해 요금이 부과되어, 현재는 배포 중단중입니다.

# 기능
- 데이터 가져오기
  - HRD-NET 의 데이터를 가져와 DB 에 저장
- 사용자
  - 회원가입
  - 로그인
  - 로그아웃
  - 아이디 찾기
  - 비밀번호 찾기
    - 이메일로 임시 비밀번호 발급
- 관리자
  - 회원가입 승인 및 거절
  - 사용자 정보 수정
