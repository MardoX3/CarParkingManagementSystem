# 🅿️ System Zarządzania Parkingiem

> Aplikacja konsolowa napisana w Javie do zarządzania systemem parkingowym. 

*System umożliwia użytkownikom rejestrację, zarządzanie pojazdami i dokonywanie rezerwacji miejsc parkingowych.*

---

## ✨ Funkcjonalności

### **👤 Zarządzanie Użytkownikami**
  - ✅ Rejestracja użytkowników
  - ✅ Bezpieczny system logowania
  - ✅ Zarządzanie własnymi pojazdami
  
### **💰 System Portfela**
  - ✅ Sprawdzanie salda
  - ✅ Integracja płatności **BLIK** do doładowań
  - ✅ Automatyczne płatności za rezerwacje parkingu

### **🚗 Zarządzanie Pojazdami**
  - ✅ Dodawanie wielu pojazdów do konta użytkownika
  - ✅ Obsługa różnych danych pojazdu *(numer rejestracyjny, marka, model, kolor)*
  - ✅ Usuwanie pojazdów z konta

### **🎫 Zarządzanie Parkingiem**
  - ✅ Dokonywanie rezerwacji parkingu
  - ✅ Automatyczne obliczanie kosztów **(5 PLN za godzinę)**
  - ✅ Miejsca parkingowe bazujące na dacie i czasie

---

## 🔧 Szczegóły Techniczne

System wykorzystuje:
```java
✓ Podstawowe funkcje Javy
✓ Przechowywanie danych w pliku
✓ Interfejs konsolowy z formatowaniem kolorów
✓ Zarządzanie kolorami pojazdów oparte na enum
✓ LocalDateTime do obsługi czasu parkowania
```

## 🚀 Jak Uruchomić

1. `git clone [url-repozytorium]`
2. `javac *.java`
3. `java CarParkingManagementSystem`
4. Postępuj zgodnie z instrukcjami wyświetlanymi na ekranie

## 📁 Struktura Plików

| Plik | Opis |
|------|------|
| `CarParkingManagementSystem.java` | Główny plik systemu z interfejsem użytkownika |
| `User.java` | Implementacja klasy użytkownika |
| `Vehicle.java` | Implementacja klasy pojazdu |
| `ParkingSpot.java` | Implementacja zarządzania parkingiem |
| `UsersDatabase.java` | Przechowywanie danych i zarządzanie użytkownikami |
| `Color.java` | Wyliczenie kolorów pojazdów |
| `ConsoleColors.java` | Narzędzia do formatowania konsoli |

## 💾 Przechowywanie Danych

Dane użytkowników są przechowywane w pliku tekstowym (`users_data.txt`) z wartościami oddzielonymi przecinkami:

```txt
username,password,balance,plateNumber,brand,model,color
```

**Zawarte informacje:**
- 👤 Nazwa użytkownika
- 🔑 Hasło
- 💰 Saldo
- 🚗 Informacje o pojeździe:
  - Numer rejestracyjny
  - Marka
  - Model
  - Kolor
---

<div align="center">
  
**Made with ❤️ by Java Developers**

</div>
