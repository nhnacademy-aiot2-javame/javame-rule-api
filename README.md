
<h1 align="center">🔧javame-rule-api🔧</h1>
<div align="center">
Rule Engine에 사용될 rule-api 입니다. 
</br>
사용자가 설정한 Rule을 DB(Mysql)에 저장하고 프론트에 보여주는 역할을 합니다.
</br>
</br>
</div>

</br>
</br>
<div align="center">
<h3 tabindex="-1" class="heading-element" dir="auto">사용스택</h3>



  
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">

![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)</br>
<img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white">
![SonarQube](https://img.shields.io/badge/SonarQube-black?style=for-the-badge&logo=sonarqube&logoColor=4E9BCD)
</br>
</br>
</div>
</br>
</br>
</br>

<div align=center>
<h3 tabindex="-1" class="heading-element" dir="auto">서비스 아키텍쳐</h3> 
  
![image](https://github.com/user-attachments/assets/e277eed9-eb88-4ce2-8cb6-2ba0fcd3f4a9)

</br>
<h3 tabindex="-1" class="heading-element" dir="auto">1.사용자 → Gateway → Rule API</h3> 
<li>센서/서버/임계치 등록</li>
<li>등록된 임계치 목록 조회 (프론트에 표시)</li>

<h3 tabindex="-1" class="heading-element" dir="auto">2.Rule API↔ MySQL</h3> 
<li>Rule 저장 및 조회</li>

<h3 tabindex="-1" class="heading-element" dir="auto">3.TransService → Rule API</h3>
<li>임계치 주기적 조회</li>

</div>

</br>
</br>
</br>
</br>
</br>
</br>

<div align=center>
<h3 tabindex="-1" class="heading-element" dir="auto">Test Coverage (Targe:Line coverage 80%)</h3> 
  <li>
    Line coverage: 93.9% (2025. 05. 15. 기준)
  </li>
  </br>

![image](https://github.com/user-attachments/assets/eef9974d-f231-468f-b7aa-7d3346f7829b)

</div>
