![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F.svg?style=for-the-badge&logo=Spring-Boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F.svg?style=for-the-badge&logo=Spring-Security&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![Git](https://img.shields.io/badge/git%20-%23F05033.svg?&style=for-the-badge&logo=git&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Liquibase](https://img.shields.io/badge/liquibase-%23316192.svg?style=for-the-badge&logo=liquibase&logoColor=white)

<h1 align="center">REST API сервиса доставки еды из ресторанов</h1>
<h2>Краткое описание сервиса:</h2>

Данный сервис предоставляет возможность по доставке еды из ближайших ресторанов прямо к вам домой. Сервис разработан с использованием передовых технологий на микросервисной архитектуре, что позволяет пользователям легко и быстро выбирать блюда из широкого ассортимента меню, делать заказы и отслеживать статус их доставки в реальном времени. 

Мы уделяем особое внимание пользовательскому опыту, поэтому наша система предлагает персонализированные рекомендации (на стадии разработки), удобные способы оплаты и возможность оценить качество обслуживания. Мы также работаем над разработкой интеллектуальных алгоритмов, которые позволят оптимизировать процесс доставки и улучшить качество обслуживания.


<h2>Функционал для 3-х категорий пользователей:</h2>
<ul>  
  <h3>Клиент (Customer) может:</h3>
    <li>Регистрироваться в сервисе</li>
    <li>Осуществлять заказ</li>
    <li>Оплачивать и отменять заказ</li>
    <li>Отслеживать статус заказа</li>
    <li>Просматривать историю заказов</li>
    <li>Получать уведомления об изменении статуса заказа</li>

  <h3>Ресторан (Restaurant) может:</h3>
    <li>Регистрироваться в сервисе</li>
    <li>Добавлять и редактировать меню</li>
    <li>Принимать и отменять заказ на приготовление</li>
    <li>Просматривать историю заказов</li>
    <li>Получать уведомления о поступлении нового заказа</li>

  <h3>Курьер (Courier) может:</h3>
    <li>Регистрироваться в сервисе</li>
    <li>Начинать и заканчивать смену</li>
    <li>Принимать и отменять заказ на доставку</li>
    <li>Просматривать историю заказов</li>
    <li>Получать уведомления о поступлении нового заказа</li>
</ul>

<h2>Для запуска необходимо:</h2>
  

1. Клонировать проект в среду разработки
2. Настроить БД и прописать значения в файле **[application.properties](src/main/resources/application.properties)** 
3. Скачать **[Docker](https://www.docker.com)** и запустить его.
4. Скачать Docker образ с помощью команды ```docker pull xxxxx.ru``` 
5. Запустить Docker образ с помощью команды ```docker run -p 3000:3000 xxxxxx.ru``` 
6. Запустить метод **main** в файле **[FinalTaskApplication.java](src/main/java/com/example/finaltask/FinalTaskApplication.java)** 

<h2>После запуска вам будет доступно 3 сервиса:</h2>

Сервис клиентов - http://localhost:8081

Сервис ресторанов - http://localhost:8082

Сервис курьеров - http://localhost:8083

Swagger UI - http://localhost:8080/swagger-ui/index.html#


