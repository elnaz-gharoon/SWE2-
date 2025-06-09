# SWE2-
# Passwort-Management-System

Ein Passwort-Management-System, das es Benutzern ermöglicht, Accounts zu erstellen, anzuzeigen, zu aktualisieren, zu löschen und sichere Passwörter zu generieren und zu speichern.

## Funktionen

- **Account-Erstellung**: Erstellen Sie einen neuen Account mit einem Namen, Login und Passwort.
- **Account-Anzeige**: Zeigen Sie die Details eines bestimmten Accounts an.
- **Alle Accounts anzeigen**: Zeigen Sie eine Liste aller Accounts an.
- **Account-Aktualisierung**: Aktualisieren Sie die Details eines bestehenden Accounts.
- **Account-Löschung**: Löschen Sie einen bestehenden Account.
- **Passwort-Generierung**: Generieren Sie sichere Passwörter.
- **Passwort-Speicherung**: Speichern Sie Passwörter sicher.
- **Sicheres Passwort anzeigen**: Zeigen Sie ein sicheres, verschlüsseltes Passwort an.
- **Passwort ändern**: Ändern Sie das Passwort eines bestehenden Accounts.
- **Passwort anzeigen**: Zeigen Sie das Passwort eines bestehenden Accounts an.

## Dokumentation 

Alle notwendigen Unterlagen und Informationen finden Sie im [Wiki](https://github.com/elnaz-gharoon/SWE2-/wiki). sowie in der beigefügten docsDatei im Reposirory.

Clone this Wiki localy : https://github.com/elnaz-gharoon/SWE2-.wiki.git

## Installation

### Voraussetzungen

- Java 21 muss installiert und die Umgebungsvariable `JAVA_HOME` korrekt gesetzt sein
- Maven 3.8 oder höher installiert

1. **Klonen Sie das Repository:**
    ```sh
    git clone https://github.com/elnaz-gharoon/SWE2-.git
    cd SWE2-
    ```
3. **Erstellen und starten Sie das Projekt mit Maven:**
    ```sh
    mvn clean install
    mvn exec:java -Dexec.mainClass="https://github.com/elnaz-gharoon/SWE2-/blob/main/src/main/java/com.elnaz.Application.MainApplication.java"
    ```

## Verwendung

Nach dem Start des Programms können Sie aus dem Menü die gewünschten Optionen auswählen, um verschiedene Aktionen durchzuführen, wie z.B. Accounts zu erstellen, anzuzeigen, zu aktualisieren, zu löschen und Passwörter zu generieren und zu speichern.

### Beispiel

1. **Account-Erstellung:**
    - Wählen Sie Option 1 und geben Sie den Namen, Login und Passwort des neuen Accounts ein.
2. **Account-Anzeige:**
    - Wählen Sie Option 2 und geben Sie die Account-ID ein, um die Details anzuzeigen.
3. **Passwort-Generierung:**
    - Wählen Sie Option 6 und geben Sie die gewünschte Länge des Passworts ein.

## Tests

Um die Tests auszuführen, verwenden Sie den folgenden Befehl:
```sh
mvn test
```

## Datenbank:
Dieses Projekt verwendet eine eingebettete H2-Datenbank im Datei-Modus (./mydb). Sie wird automatisch beim Start erstellt und benötigt keine zusätzliche Konfiguration.

## Lizenz

nix.

## Beitragende

- [Elnaz Gharoon](https://github.com/elnaz-gharoon)
  
Dieses Projekt wurde primär von mir Elnaz Gharoon Dastjeroy mit Matrikelnummer [6164634] im Rahmen des Moduls Advanced Software Engineering entwickelt.  
Ich danke Ramin Armafar für die Unterstützung bei der Refaktorisierung einiger Code-Stellen im Sinne von Clean Code und Best Practices.  
Die Hauptfunktionalität, Struktur und Umsetzung stammen jedoch vollständig aus eigener Arbeit.


## Kontakt

Wenn Sie Fragen oder Anregungen haben, können Sie uns gerne kontaktieren:
- E-Mail: [Elnaz Gharoon](elnazgharoon2020@gmail.com)
