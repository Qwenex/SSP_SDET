# CI-4
## Запуск тестов в Docker
Используется 3 контейнера app + selenoid + selenoid ui через docker-compose \
В качестве ui тест кейсов - проверка дат рождения и смерти известных личностей на сайте wikipedia
![Screenshot_1.png](../screenshots/ci4/Screenshot_1.png)
![Screenshot_2.png](../screenshots/ci4/Screenshot_2.png)
![Screenshot_3.png](../screenshots/ci4/Screenshot_3.png)
Сохранение в артефакты сборки allure отчета и папки target
![Screenshot_4.png](../screenshots/ci4/Screenshot_4.png)

## Настройки
Вместо шага "Вызвать цели Maven", выполняется команда windows. (Запуск maven прописан внутри докера)
![Screenshot_13.png](../screenshots/settings4Jenkins/Screenshot_13.png)

### Настройка-памятка selenoid без docker-compose:
В командной строке в директории с selenoid: 
* Запуск selenoid - cm_windows_amd64.exe selenoid start \
(запускается так же на порте 4444) 
* Запуск selenoid ui - cm_windows_amd64.exe selenoid-ui start \
(запускается на порте 8080) 
* Обновление selenoid и браузеров - cm_windows_amd64.exe selenoid update
* Должен быть запущен docker desktop, если выдает ошибку про версию,\
в настройках docker указать "min-api-version":"1.24"
![Screenshot_14.png](../screenshots/settings4Jenkins/Screenshot_14.png)