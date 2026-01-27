# CI-3
## Регулярный запуск авто-тестов и оповещение по почте.

![Screenshot_1.png](../screenshots/ci3/Screenshot_1.png)
![Screenshot_2.png](../screenshots/ci3/Screenshot_2.png)
Письмо с приложенным allure отчетом в виде zip-файла
![Screenshot_3.png](../screenshots/ci3/Screenshot_3.png)
Письмо с приложенным allure отчетом в виде ссылки\
(Один тест специально провален для наглядности)
![Screenshot_4.png](../screenshots/ci3/Screenshot_4.png)


## Настройки
Регулярный запуск в 17:45 каждого дня (cron)
![Screenshot_2.png](../screenshots/settings4Jenkins/Screenshot_2.png)

Подключение плагина testNG для сохранения отчета, \
из которого берутся данные для отправки статистики по почте
![Screenshot_5.png](../screenshots/settings4Jenkins/Screenshot_5.png)

Подключение плагина post-build-task. С помощью тригера в выводе консоли запускается скрипт\
копирования папки с allure отчетом из артефактов сборки в сборочную директорию,\
чтобы можно было приложить этот zip-файл с отчетом к письму.
* Важно! Google блокирует рассылку сообщений с приложенными zip-файлами.\
  (как альтернативу можно использовать ссылку на отчет в теле письма)

![Screenshot_7.png](../screenshots/settings4Jenkins/Screenshot_7.png)

Плагин Extended Email Publisher
Html формат в тело письма для отправки динамического отчета после каждого прогона
![Screenshot_9.png](../screenshots/settings4Jenkins/Screenshot_9.png)
Тригер на always
![Screenshot_10.png](../screenshots/settings4Jenkins/Screenshot_10.png) \
Системные настройки для отправки сообщений \
![Screenshot_11.png](../screenshots/settings4Jenkins/Screenshot_11.png)
![Screenshot_12.png](../screenshots/settings4Jenkins/Screenshot_12.png)