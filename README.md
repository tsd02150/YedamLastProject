## 모의 주식 사이트
## - 프로젝트 명 : 팔기 전까진 손실이 아니다
## - 조 이름 : 어제 팔 걸


## 팀 소개
- [신재영](https://github.com/tsd02150) - 프로젝트 총괄, 메인페이지, 커뮤니티, 익명채팅
- [김태연](https://github.com/Benhur41) - 주식시장, 관리자모드, 실시간 알림
- [신지은](https://github.com/shinji56n) - 포인트 몰, 결제, 관리자모드
- [김미향](https://github.com/kmhyang) - 회원관리, 현황, 투자성향분석

## 개발환경
- Spring Tool Suite 3
- Oracle

## 협업
- Github
- Slack


## 사용기술
  * JAVA
  * Database
  * Oracle
  * JavaScript
  * html
  * css
  * Spring boot
  * Gradle

<br/>
<br/>
<hr>
<br/>

## STS 환경에서 Github 연동하는 과정입니다.
  - 반드시 <b>작업 전 main branch의 내용(팀원의 작업물)을 pull 받고</b> 작업 시작한다.
  - 반드시 <b>개인 브랜치</b>에서 작업 후 merge한다.
  - 브랜치명은 각자의 이름으로한다
  - push전 main branch pull은 필수로 이행한다.
  - 별도의 Pakage 추가 시 즉시 psuh & 알려준다.
  
## 1. Local에 Repository Clone
1. (Eclipse Git Repositories) : git에서 code<> 클릭 > https url 복사 > Git Repositories 의 [Clone a git repository and add the clone to this view] 선택 > Clone되는 경로기억  
![1](https://github.com/tsd02150/YedamLastProject/assets/85140469/48f0f742-98a9-43c8-aaca-7472d2eb5cd6)

![2](https://github.com/tsd02150/YedamLastProject/assets/85140469/47b0ea9c-c9f6-479b-ad0e-e1b92aa8d03d)

![3](https://github.com/tsd02150/YedamLastProject/assets/85140469/1c182875-80e2-4882-8666-82a40151c0b2)


## 2. 프로젝트 STS 작업환경에 import
![4](https://github.com/tsd02150/YedamLastProject/assets/85140469/d8d8c420-f002-4e87-a5ef-aefeaab9a4f6)

![5](https://github.com/tsd02150/YedamLastProject/assets/85140469/40c9fd28-6415-4b13-92bc-878f6fb187ac)

![6](https://github.com/tsd02150/YedamLastProject/assets/85140469/830d7c42-ca3c-4a4c-bd81-94c4231067d3)


## 3. 개인 Branch 생성 및 이용방법
만들어진 레파지토리 우클릭 > 현재는 main으로 되어있음 > switch to > new branch > Branch name 작성
![7](https://github.com/tsd02150/YedamLastProject/assets/85140469/ff3687b9-d369-4434-a375-58a83358b0a5)



## 4. 개인 Branch에서 자유롭게(?) 작업한 후에 push하는 과정
1.
git에 올리기 전 git에 있는 main branch의 내용을 본인 로컬branch로 pull해야함
pull... 클릭
![image](https://user-images.githubusercontent.com/85140469/235565671-328d60fb-96a9-4ce7-a45e-4996c397fc58.png)

![8](https://github.com/tsd02150/YedamLastProject/assets/85140469/5766a7a4-0023-46b5-ae8f-ae2f1c69c0c0)

2.
Git staging에서 올릴 파일들을 stage에 넣어주고 commit message 작성후 commit&push
커밋을 했었다면 프로젝트 우클릭 Team Share > push branch '본인 브랜치'or Git stageing 에서 push HEAD 
![image](https://user-images.githubusercontent.com/85140469/235566029-924fb414-e207-43a1-9ede-3d9a039fd533.png)

  
## 5. 마무리로 main branch에 병합
1.
switch to로 로컬 main 브랜치로 이동
![image](https://user-images.githubusercontent.com/85140469/235566227-46d3db56-ea94-4959-9cd8-20ea116a970d.png)

2.
git의 main branch의 내용 로컬 main branch로 pull
pull... 클릭
![image](https://user-images.githubusercontent.com/85140469/235565671-328d60fb-96a9-4ce7-a45e-4996c397fc58.png)

3.
우클릭 해서 merge 선택
"본인브랜치" 선택
![9](https://github.com/tsd02150/YedamLastProject/assets/85140469/c89bb154-0317-4ebd-b949-901f1fd4af5d)

4.
merge 완료 후
다시 우클릭 push branch 'main'
![10](https://github.com/tsd02150/YedamLastProject/assets/85140469/8841e8c7-15ea-41e8-9da1-4e66ad146a01)
