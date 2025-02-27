# Job Search App

Job Search App — это приложение для поиска работы. Основная цель проекта — показать мои навыки работы с современными технологиями разработки Android-приложений, следуя принципам **Clean Architecture** и многомодульности.

---

## 🛠️ Стек технологий

- **Язык**: Kotlin
- **Архитектура**: MVVM + Clean Architecture
- **DI**: Hilt
- **Работа с данными**:
  - Room (локальная база данных)
  - Retrofit (сетевые запросы)
- **Асинхронность**: Coroutines + Flow
- **UI**:
  - XML-верстка
  - RecyclerView с использованием AdapterDelegates
  - StateFlow для управления состояниями
- **Многомодульность**: приложение разделено на модули (`app`, `core`, `data`, `domain`, `ui`)
- **Прочее**:
  - Поддержка локализации

---

## 📂 Структура модулей

- **`app`**: Точка входа приложения, инициализация Hilt.
- **`core`**: Общие утилиты и вспомогательные классы, например, `DateUtils`.
- **`data`**: Репозитории, модели данных (DTO и Entity), базы данных, работа с сетью.
- **`domain`**: Бизнес-логика, интерфейсы репозиториев.
- **`ui`**: Отображение (фрагменты, адаптеры, view model).


