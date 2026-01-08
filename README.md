# Sammlung von oauth2 Beispielen

## oauth2 mit Spring Boot

### SpringSecurity
* Alle statischen Resourcen sollten frei zugänglich sein (siehe in `ApplicationConfiguration` #permitAll())
  Insbesondere alle Javascript, favicon.ico, index.html und sonstige Dateien, die für das initiale Starten
  der Web-Anwendung nötig sind.
* Alle REST Endpunkte sollten unterhalb eine URL liegen z.B. /api/** (Siehe auch den nächsten Punkt).
* `#oauth2Login(login -> login.defaultSuccessfulUrl("/", alwaysUse: true)`
  TODO: Ohne diese Angabe würden alle geschützten REST Abfragen, die ein Login erzwingen, im anschließendem
  Redirect in der JSON Response landen und diese dem Nutzer anzeigen.
  ![SPA mit Spring Boot und OAuth2](./spa-spring-boot-oauth2.svg)
  
