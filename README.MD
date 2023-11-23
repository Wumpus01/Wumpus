# Wumpus játék
<br>

## Játék menete
1. A játék a felhasználó nevének bekérésével kezdődik
2. Ezután jelenik meg a menü, ahol választhatunk az alábbi opciók közül:

    * (1) - Create map (még nem implementált)
    * (2) - Read map from file
    * (3) - Load game from database
    * (4) - Save game into database
    * (5) - Play game
    * (6) - Quit

A menüpontok közül az 1-6 billenyűk lenyomásával választhatunk. 

*Megjegyzés:* Megoldása során a fájlból való beolvasását választottam, így a térképszerkeztő nem került implemenálásra a feladat meghatározásának megfelelően.

## Egyes opciók leírása

#### Read map from file
Ezen opciót választva a felhasználónak meg kell adnia a beolvasni kívánt input fájlt, ami megfelel a feladatban szerepelő elvárásoknak.

#### Load game from database
Ezen opciót választva a felhasználónak meg kell adnia a beolvasni kívánt játék azonosítóját. Ha sikeres volt a beolvasás akkor Game successfully loaded! felirat jelenik meg.

#### Save game into database
Ezen opciót választva a felhasználónak lementheti a játékot adatbázisba, ami onnan visszatölthető

#### Play game
Elindítja a játékmenetet.

#### Quit
Kilép a wumpus játékból.

<br>

## Előfeltétel az adatbázis műveletekhez.
Amennyiben szeretnénk adatbázisból betölteni játékot, illetve oda menteni úgy futnia kell a H2-nek, és léteznie kell a felhasználói könyvtárban egy wumpusz nevű adatbázisban admin felhasználónév és jelszó mellett. Ellenkező esetben runtime error mellett fog indulni a program és nem lesz lehetőség a 3-4 menüpontok használatára


## Bedandó dolgozat
A tárgy teljesítéséhez szüksége *Tervezési minták egy OO programozási nyelvben. MVC, mint modell-nézet-vezérlő minta és néhány másik tervezési minta.* című dolgozat megtalálható jelen repositoryban Tervezési_minák_és_MVC.md név alatt.