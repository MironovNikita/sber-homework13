
# Фреймворк WebFlux

### ✨Немного теории

🛠️ **WebFlux** - это фреймворк для разработки реактивных веб-приложений. Он обеспечивает возможность создания масштабируемых и отзывчивых веб-приложений, способных обрабатывать большое количество запросов с минимальными ресурсами. Он основан на реактивном программировании, что позволяет обрабатывать асинхронные события и реагировать на них в реальном времени.

Основные принципы **WebFlux** включают в себя использование реактивных типов данных, таких как `Flux` (поток данных, который генерирует несколько элементов) и `Mono` (поток данных, который генерирует ноль или один элемент), а также асинхронные API для обработки HTTP-запросов.

**WebFlux** может быть использован для создания как _RESTful_, так и _WebSocket-приложений_. Он поддерживает различные веб-серверы, включая `Netty` и `Jetty`.

🔄 **JSON (JavaScript Object Notation)** - это легковесный формат обмена данными, основанный на синтаксисе `JavaScript`, который используется для передачи структурированных данных между приложениями. **JSON** представляет собой текстовый формат, состоящий из пар "ключ: значение", разделенных запятыми, и ограниченных фигурными скобками `{}` для объектов, или квадратными скобками `[]` для массивов.

Пример JSON объекта:
```java
{
  "name": "Имя",
  "age": 30,
  "city": "Город"
}
```
**JSON** широко используется в веб-разработке для передачи данных между клиентским и серверным приложениями, а также для хранения конфигурационных данных, API-ответов и других структурированных данных.

📦 **Чанки (chunks)** в контексте передачи данных обычно относятся к фрагментам данных, которые передаются по сети или обрабатываются потоковыми процессами. Например, когда данные слишком большие, чтобы передать их целиком одним пакетом, они могут быть разделены на чанки, которые передаются по отдельности. Это особенно полезно для эффективной передачи больших объёмов данных по сети, таких как файлы, потоковое видео и т.д.

При использовании чанков данные могут быть переданы порциями, что позволяет получателю начинать обработку данных до того, как весь поток данных будет полностью завершен. Это может снизить задержки и улучшить производительность при передаче больших объёмов данных. В контексте **JSON**, данные могут быть разделены на чанки для передачи по сети, если они слишком большие для передачи в одном запросе или ответе.

### 🚀Практика

В данной работе представлена реализация задания, связанного с вышеописанной темой:
1. Разбиение JSON-объекта на чанки с передачей их в пользовательский интерфейс

## Задание

На основе фреймворка **WebFlux**/(любой другой реактивный фреймворк) сделать приложение-сервер и простенький UI, который будет запрашивать из сервера информацию в формате `json`, а тот будет ему отправлять ``json`` чанками с интервалом в 5 секунд между чанками, пока не отправит все чанки.

UI должен корректно отобразить **JSON** и провалидировать (парсится без ошибок).
Пример json: 
```java
    {
        "field":"value",
        "field":"value",
        "field":"value"
    }
```
Необходимо получить результат:
```java
{field:value}
{field:value}
{field:value}
{}
{}
{}, где
{} -> чанк
```

## Описание результатов

🤔 Для реализации данного задания был создан класс [**`User`**](https://github.com/MironovNikita/sber-homework13/blob/main/src/main/java/com/application/sberhomework13/data/User.java), объекты которого будут передаваться в формате **JSON**. 
```java
public class User {
    private String surname;
    private String name;
    private String patronymic;
    private LocalDate birthday;
    private String address;
    private Double weight;
    private Double height;
    private String education;
}
```

Для работы с **JSON**, а также с их последующим разбиением на _чанки_ был создан класс [**`ChunckController`**](https://github.com/MironovNikita/sber-homework13/blob/main/src/main/java/com/application/sberhomework13/data/ChunkController.java). Данный класс содержит один метод **`public Flux<String> getChunks()`**, который и будет возвращать чанки сгенерированных в методе **`private List<User> createUserList()`** объекты класса [**`User`**](https://github.com/MironovNikita/sber-homework13/blob/main/src/main/java/com/application/sberhomework13/data/User.java).

🖼️🖌️ Для тестового запуска программы был создан класс [**`UserInterface`**](https://github.com/MironovNikita/sber-homework13/blob/main/src/main/java/com/application/sberhomework13/graphics/UserInterface.java). Он генерирует небольшое окно UI для запуска программы. При нажатии кнопки ***"Запросить JSON"*** каждые 5 секунд в окне будут отображаться чанки всех объектов, сгенерированных в классе [**`ChunckController`**](https://github.com/MironovNikita/sber-homework13/blob/main/src/main/java/com/application/sberhomework13/data/ChunkController.java).

Для запуска программы был создан класс [**`GraphicsRunner`**](https://github.com/MironovNikita/sber-homework13/blob/main/src/main/java/com/application/sberhomework13/GraphicsRunner.java), из которого необходимо осуществить запуск. Пользовательский интерфейс выглядит следующим образом:

![startWindow](https://github.com/MironovNikita/sber-homework12/blob/main/res/startWindow.png)

При нажатии кнопки ***"Запросить JSON"*** в пользовательском окне начнут постепенно (каждые 5 секунд) отображаться чанки:

![workingWindow](https://github.com/MironovNikita/sber-homework12/blob/main/res/workingWindow.png)

Работа приложения будет продолжаться до тех пор, пока на экран не будут выведены все чанки переданных объектов:

![endWindow](https://github.com/MironovNikita/sber-homework12/blob/main/res/endWindow.png)

### 💡 Примечание

В [**`pom.xml`**](https://github.com/MironovNikita/sber-homework13/blob/main/pom.xml) данного задания были добавлены следующие зависимости: в блоке *properties /properties*:

```java
<artifactId>spring-boot-starter-webflux</artifactId>
<artifactId>lombok</artifactId>
<artifactId>spring-boot-starter-test</artifactId>
<artifactId>reactor-test</artifactId>
<artifactId>jackson-datatype-jsr310</artifactId>
```

Результат сборки текущего проекта:

```java
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  6.907 s
[INFO] Finished at: 2024-02-18T14:44:53+03:00
[INFO] ------------------------------------------------------------------------
```