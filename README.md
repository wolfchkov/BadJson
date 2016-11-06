# BadJson - парсер "плохого" JSON'a :)

Парсит только JSON-объекты, мыссивы не поддерживаются, но можно расширить функционал.

Нормально парсятся не экранированные ковычки в строках-значениях полей, в именах полей не экранированные ковычки не поддерживаются.

Поддерживаемые типы значений в полях объектов JSON:
строки - конвертируются в String
true\false - конвертируются в Boolean
число без точки - Integer
число с точкой - Float
null - null

Сборка: `mvn clean compile assembly:single`
Запуск: `java -jar BadJson-0.1-jar-with-dependencies.jar`

Запускаемый класс net.wolf.bad.json.TestJsonParser, читает файл с примерами JSON, выводит JSON с файла, парсит и отображает как 
JsonObject.prettyPrint().


