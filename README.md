# Magazin_Mobila

Acest proiect are ca scop realizarea unui magazin de unde iti poti achizitiona mobila.

Ca si functionalitati avem:

- login ( admin / user )
- register ( doar user )

Admin:

- adaugare produs in stoc
- stergere produs din stoc
- modificare produs existent
- vizualizare produse
- cautare produse dupa nume
- raspundere la tichete
- cautare tichete dupa numele utilizatorului / id
- afisare tichete nerezolvate / toate tichetele
- cautare user
- stergere user ( delete account )
- cautare user
- vizualizare comenzi
- vizulizare produse comanda
- actualizare status comanda ( in procesare, expediata, etc )


User:

- adaugarea unui produs in cosul de cumparaturi
- stergerea unui produs din cosul de cumparaturi
- crearea unui tichet
- cautare produse
- plasare comanda
- vizualizare comenzi plasate
- modificarea bucatilor unui produs din cos

Aplicatia este facuta cu exceptii de tipul try - catch, ca de exemplu:

- nu putem sa adaugam un produs cu un stoc mai mare decat este in magazin ( daca mai avem 3 scaune disponibile nu putem adauga 4 in cos )
- daca plasam comanda si intre timp a fost plasata alta comanda, iar stocul nu mai este disponibil, comanda nu este plasata, iar pe ecran ne va aparea ce produs nu mai este disponibil
- cantitatea adaugata trebuie sa fie un numar natural nenul intotdeauna
- nu putem adauga de mai multe ori in cos acelasi produs, dar putem modifica cantitatea
- etc

Proiectul este conectata la o baza de date in SQL Developer si are 6 table-uri: admincomenzimobila, comenzimobila, comenziplasatemobila, produsemobila, tichetemobila si usermobila.

UserMobila: ( datele unui user )

- username
- parola
- functie ( admin / user, admin poate fi adaugata doar din baza de date )
- telefon
- oras
- adresa
- prenume
- nume

ProduseMobila: ( stocul actual )

- numele produsului
- descrierea produsului
- cantitatea

ComenziMobila: ( produsele aflate in cosul fiecarul user )

- username
- numele produsului
- cantitatea

TicheteMobila: ( tichetele create de utilizatori, acestea avand un raspuns sau statusul "Raspuns in asteptare!" )

- numele utilizatorului
- subiecul tichetului
- mesaj
- raspuns ( raspunsul sau statusul "Raspuns in asteptare!" )
- id-ul tichetului ( generat autoamt )

AdminComenziMobila: ( produsele care au fost plasate deja intr-o comanda )

- id-ul comenzii ( generat automat )
- numele produsului
- cantitatea

ComenziPlasateMobila: ( comenzile care au fost plasate )

- id-ul comenzii ( pe baza id-ului se pot vizualiza produsele comandate )
- username-ul utilizatorului
- prenume
- nume
- adresa
- telefon
- statusul ( expeidata, procesata, etc )
- orasul
