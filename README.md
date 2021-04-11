# Elevator-control-system-app
"**Elevator Control System**" to aplikacja będąca odwzorowaniem systemu kontroli wind w wersji mobilnej. Pozwala ona w łatwy sposób ustawić dane konfiguracyjne systemu, które posłużą do zainicjalizowania symulacji. Symulacja ta bazuje na losowo wygenerowanych danych dla każdej z wind. Aplikacja umożliwia wybór liczby pięter budynku od 5 do 50 i jest to własna inwencja, nie zmienia ona trudności generowania danych symulacji. Docelowy punkt, z którego obsługujemy system jest ekran panelu operacyjnego. Wyświetlone na nim zostają windy w schludnie zaprojektowanym recyclerView. Karta przedstawiająca stan windy oprócz numeru (ID) windy skupia się na wyświetleniu obecnego piętra, na którym znajduje się winda, stanu windy (winda naturalnie może poruszać się w górę, w dół, lub stać w miejscu) oraz kolejki żądanych pięter posortowanych w zależności od kierunku poruszania się windy (wygenerowanych przez symulację oraz tych, które narzuci użytkownik). Ekran systemu oprócz powyższych kart wyświetla informację na temat ilości pięter w budynku, dla którego skonfigurowana jest aplikacja, informację pomagające odczytać prawidłowo wskazywane dane oraz sekcję przywoływania windy (zmieniającego się po dotarciu windy na żądane piętro na sekcję wyboru pożądanego piętra. Całość interfejsu aplikacji domykają schludnie zaimplementowane animacje. Użycie w tego typu projekcie baz danych jest moim zdaniem doskonałym zobrazowaniem sytuacji, w której taki system był zaimplementowany do prawdziwych wind i zarządzania nimi. Nawet podczas "awarii" systemu, stany aktualnej sytuacji zostają zapisane i możliwy jest powrót do wcześniej rozpoczętej symulacji. Zastosowanie recyclerView umożliwia wyświetlenie każdej ilości kart opisujących stan windy w schludny sposób niezależnie jak dużo ich jest. Rozważałem dodanie Glide'a do obsługi zaimplementowanych grafik, ale są one na tyle małe, że niepotrzebnie skomplikowały by kod (choć zmniejszyły by rozmiar pliku instalacyjnego aplikacji). 

# Screenshots
![N|Solid](https://i.imgur.com/RcVSW1K.jpg) ![N|Solid](https://i.imgur.com/AG4x8Uf.jpg) ![N|Solid](https://i.imgur.com/JOeHxVO.jpg) ![N|Solid](https://i.imgur.com/1cq1FGO.jpg)

# Video presentation
[![N|Solid]()

Kliknij powyższy obrazek, aby obejrzeć prezentację wideo.

# Kanban board @Trello

[![N|Solid](https://i.imgur.com/5FwGeG2.png)](https://trello.com/b/VXElFonf/elevator-control-app)

Stworzyłem własną tablicę Kanban, używając Trello do zarządzania zadaniami i problemami. Kliknij powyższy obrazek, aby zobaczyć aktualną wersję.

# Main features

  - Inicjalizacja danych systemu dla max. 16 wind i max 50 pięter
  - Zapisywanie aktualnego stanu symulacji
  - Zamawianie windy z dowolnego piętra (Obsługa przywołań windy)
  - Uaktualnienie stanu wind
  - Wykonanie kroku symulacji
  - Sprawdzenie stanu systemu (na którym piętrze windy są i do którego zmierzają)

# Under the hood

  - Room DB
  - MVVM
  - Architectural Components
  - Fragments
  - RecyclerViews
  - Material Design
  - Gson (dla stworzenia TypeConverter)

### Information from the author
> Notice that this is a very beginning version of the app and it's still developing
> so you can see some strange behaviour or some desing issues. 
> If you find some bug, please contact with me, I will try to fixed it as soon as I can.
> Other helpful tips or ideas are warmly welocome, so just let me know :)
> Thank you for visiting my repo and sharing your opinions!
