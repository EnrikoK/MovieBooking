#Filmide broneerimise süsteemi testtöö CGI suvepraktika jaoks

##Backend

Rakenduse backend on tehtud Spring Boot raamistikus, kasutades Java 21 versiooni. Backend jookseb pordil 8080 ja on ühendatud PostgreSQL serveriga mis jookseb pordil 5432.
Kasutajate registreerimiseks ja autentimiseks kasutasin JWT HttpOnly tokeneid. Rakendusel on 3 põhilist API route'i.
```
localhost:8080/api/screenings/
localhost:8080/api/auth/
localhost:8080/api/tickets/
```
API Endpointid:

###/api/screenings/


Tagastab kõik kinokavas olevad filmide linastused

[GET] /api/screenings/all-screenings


Tagastab kõik kinokavas olevad filmid, mille linastused on tulemas,
ehk nende kuupäev on suurem kui praegune TimeStamp.

[GET] /api/screenings/upcoming


Tagastab vastava id-ga filmilinastuse informatsiooni

[GET] /api/screening/{id}


Tagastab kasutajale vastavad filmilinastuste soovitused. Soovitused arvutatakse kasutades 
kasutaja vaatamisajalugu žanrite põhjal. Kasutaja vaadatud žanrite kogused järjestatakse 
vastaval antud žanrile ostetud piletite arvu põhjal.

[GET] /api/user-recomendations

###/api/auth

Kasutajaks registreerimise endpoint

[POST] /api/auth/register

JSON payload

```
{
	"username":"Kasutajanimi",
	"password":"Salasona"
}
```

Kasutaja sisselogimine. Tagastab HttpOnly JWT tokeni

[POST] /api/auth/login

```
{
	"username":"Kasutajanimi",
	"password":"Salasona"
}
```

Kasutaja autentimine. Toimub HttpOnly cookie-ga kus on JWT

[GET] /api/auth/authenticate


Kasutaja välja logimine. Võetakse kasutaja JWT token ja tagastatakse uus token, mille
kehtivusaeg on 0.

[GET] /api/auth/logout


###/api/tickets

Leitakse JWT põhjal kasutaja ja tagastatakse temale kuuluvad piletid.

[GET] /api/tickets/user-tickets


Uue pileti ostmine. JWT põhjal leitakse kasutaja ja omistatakse talle vastavale filmiseansile piletid.

[POST] /api/tickets/purchase-ticket. 


JSON payload: screeningID - vastava filmiseansi id, seats - int[][] kus iga iste on esindatud kujul [rida,koht]
```
{
	"screeningID":1,
	"seats":[[2,1]]
}

```

#Frontend

Rakenduse frontendi jaoks on kasutatud raamistiku Vue3 koos Vuex ja Vue-Routeriga. Frontend jookseb pordil 3333


#Docker

Lisasin projektile ka failid, et seda saaks Dockeri konteinerites jooksutada. Kuna ma kasutasin Dockerit selle projekti raames esimest korda, siis loodan, et mu dockerFile-id ikka töötavad.
Kui kõik töötab nagu mu lokaalses masinas siis peaks saama rakendust käivitada dockeris: liigu rakenduse juurkausta ja käivita
(Windowsi jaoks)
```
docker-compose up --buid
```




