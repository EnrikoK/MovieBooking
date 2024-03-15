# Filmidele piletite broneerimise süsteemi testtöö CGI suvepraktika jaoks

Veebirakendus, kus saab registreerida end kasutajaks, logida kasutajana sise ja siis broneerida valitud filmiseanssidele istekohti. Samuti saab profiili alt kuvada kasutaja ostetud pileteid. Rakendus soovitab kasutajale seansi vaates istekohad ja kui kasutajal on tekkinud filmide broneerimise ajalugu, siis soovitatakse talle vastava žanriga filme.



## Backend

Rakenduse backend on tehtud Spring Boot raamistikus, kasutades Java 21 versiooni. Backend jookseb pordil 8080 ja on ühendatud PostgreSQL serveriga mis jookseb pordil 5432.
Backendis integreerisin ka välise tasuta API (http://www.omdbapi.com/), mille kaudu leiab backend filmi pealkirja järgi selle imdb reitingu ja internetis oleva filmipostri ning lisab need
tagastatavale päringule.

Kasutajate registreerimiseks ja autentimiseks kasutasin JWT HttpOnly tokeneid. Rakendusel on 3 põhilist API route'i.
```
localhost:8080/api/screenings/
localhost:8080/api/auth/
localhost:8080/api/tickets/
```
### API Endpointid:

### /api/screenings/


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

### /api/auth

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


### /api/tickets

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

## Frontend

Rakenduse frontendi jaoks on kasutatud raamistiku Vue3 koos Vuex ja Vue-Routeriga. Frontend jookseb pordil 3333. Frontendis toimub ka kasutajatele vabade istekohtade soovitamine. Selleks võetakse arvesse piletite arv ja leitakse vastavalt järjestikused kohad, mis oleks istmete maatriksi keskpunktile kõige lähemal.


## Docker

Lisasin projektile ka failid, et seda saaks Dockeri konteinerites jooksutada. Kuna ma kasutasin Dockerit selle projekti raames esimest korda, siis loodan, et mu dockerFile-id ikka töötavad ja andmebaasi init.db skript, mis peaks andmebaasi lisama kirjed filmide ja seansside kohta, toimib õigesti.
Kui kõik töötab nagu mu lokaalses masinas siis peaks saama rakendust käivitada dockeris: liigu rakenduse juurkausta ja käivita
(Windowsi jaoks)
```
docker-compose up --buid
```
## Mis jäi tegemata / mida sooviks veel teha
Kuna ülikoolis õppetöö kõrvalt ei ole aeg piiramatu ressurss, siis tooksin välja mingid programmi osad, mille edasi nokitsemiseks mul hetkel aega ei jätkund:

1) Backendis MovieDetailsAPIRequestUtil klass teeb päringuid välise API pihta iga seansi ja filmi kohta uuest. Mõtekas oleks implementeerida mingit cache'i, kus hoida juba leitud filmide reitinguid ja postri url-i. Muidu koormab see lihtsalt välist API-t ja teeb filmiseanside tagastamise aeglaseks, kui sama filmi puhul on rohkem seansse.
2) Frontendis välja logimise funktsiooni puhul võib tekkida olukord, kus kui kuvatakse kasutaja profiili ja pileteid ning logitakse välja, siis lehe headeri seis ei muutu vastavalt või kui headeri seis muutub, siis ei toimu kasutaja uuele url-ile suunamine õigesti. Ilmselt on seal mingi probleem kasutaja sisselogitud oleku määramisel Vuex store'is.
3) Tegemata jäid ajanappuse tõttu ka Backend ja Frontend testid.
4) Frontend disain on võrdlemisi primitiivne. Otsustasin panna põhirõhu API funktsionaalsuse alla ja CSS-ga tegelemiseks nii palju aega ei jäänud.
5) Andmebaasi reeglid tahaksid võib-olla üle vaatamist ja osadele tulpadele tuleks lisada vajalikud piirangud.
6) Kasutajatele filmide soovitamisel peaks backendis tegema natukene muudatusi, et soovitused oleks ajaliselt korrektses järjekorras.

## Millist välist abi kasutasin ja kus
- andmed, mida andmebaasi selle loomisel init.db skriptiga sisestatakse on genereeritud ChatGPT abil. Kuna tegemist on testimiseks mõeldud anmebaasi seisuga, siis vast sellega suur probleemi ei ole.
- Spring Security on minu jaoks veel veidi uus ja selle puhul kasutasin erinevaid allikaid. Vaatasin mingeid teisi reposid ja nende implementatsioone, lugesin internetist ja kasutasin ChatGPT abi. Ühtegi konkreetset allikat siin välja ei oskagi tuua, sest osteselt ei kopeerinud koodi, vaid üritasin rohkem sellest loogikast aru saada ja enda implementatsiooni rakendada httpOnly küpsistega.







