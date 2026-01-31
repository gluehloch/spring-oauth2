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
  

## FAQ

* Frage: 
  Why do i need the CsrfCookieFilter? The AplicationConfiguration already defines in line 25 a `CookieCsrfToeknRepository?

  Short answer: you don't always need CsrfCookieFilter — CookieCsrfTokenRepository.withHttpOnlyFalse() will create the cookie in many setups — but the filter is a safety-net to guarantee the cookie exists/gets refreshed for the SPA on every relevant response.

  Why I added the filter (concise)

  CookieCsrfTokenRepository writes the cookie when the CSRF token is saved by the repository; in many Spring setups that happens automatically, so the cookie is present.
  However, initial SPA page loads, static-resource handling, some proxy/caching setups, or custom request flows can deliver the page before the repository saves/writes the cookie. The CsrfCookieFilter reads the CsrfToken request attribute and explicitly sets/refreshes the XSRF-TOKEN cookie (with HttpOnly=false) so JS can always read it.
  The filter also handles token refreshes and ensures the cookie path/flags are set exactly how the SPA  expects.
  When you can remove the filter

  If you verify locally that hitting / (or your SPA entry) always returns Set-Cookie: XSRF-TOKEN=... from CookieCsrfTokenRepository on the initial GET, and your SPA reliably reads it, then the filter is redundant and can be removed.
  If you serve index.html via Spring MVC behind the security filter chain and you see the cookie set consistently, CookieCsrfTokenRepository.withHttpOnlyFalse() alone is sufficient.
  Recommendation

  Keep the filter for robustness (especially during development or when the SPA might be cached/served differently). If you prefer minimal code, remove it and I can run the app and verify the cookie is present on the initial GET so you can be confident it’s safe to delete.