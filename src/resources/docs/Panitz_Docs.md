# Dokumentation zum Projekt 

## Einleitung

Bei dem Spiel handelt es sich um ein sogenanntes "Rogue-Like", basierend auf dem Spiel "Rogue" von 1980.
Das Spiel ist ein Dungeon-Crawler, bei dem der Spieler durch einen Dungeon navigieren muss, 
u m denBoss zu finden und zu besiegen. ~~Der Spieler bekommt coins, die er in einem Shop gegen Items eintauschen kann
um seine eigenen Statistiken, wie z.B. Geschwindigkeit, Schaden, Leben, etc. zu verbessern.~~ (Das Kaufen von Upgrades
wurde vorerst entfernt.)
## Steuerung

Bewegung mit den Tasten: `W A S D`

Angreifen mit: `Leertaste`

~~Interagiern mit dem Händler: `E`~~

## Features
Das Spielt besitzt eine sich zufällig generierende Spielwelt, Charactere mit dynamischer Animation (Anpassung an die
Bewegungsrichtung), vollständig dynamisches UI, welches sich an den Bildschirm anpasst und ein Event-System. In
Erweiterung zur basis Library.

# Quellen
- Sprites: https://cainos.itch.io/pixel-art-top-down-basic
- Player: https://rgsdev.itch.io/animated-top-down-character-base-template-in-pixel-art-rgsdev
- Font: https://www.dafont.com/de/upheaval-pro.font
- Item sprites: https://game-icons.net/

# Code Dokumentation
Im nachgang befindet sich eine (mehr oder weniger vollständige) Dokumentation zum Code. Diese ist hauptsächlich
nur zum Nachschlagen bei der Entwicklung. Im anhang sind die Quellen zu finden.

## Game2D

## Core
### `Class Event<T>`
Ein event speichert eine Liste an Consumers, die als Parameter einen Typ T haben.
Diese können über die Methode `AddListener` hinzugefügt werden und werden 
dann bei Aufruf der Methode `Invoke` ausgeführt.

## Sprite

## InputManager
### `Class InputManager`
Der Inputmanager ist ein Singleton, der die Tastatureingaben des Spielers verwaltet. 
Er speichert für jede benötigte Taste ein Event für die Tastendruck- und Tastenloslöseaktionen.
Die Events werden in einem Dictionary gespeichert, wobei der Key der KeyCode der Taste ist.
Die Events werden bei einer Flanke der `SetPressed()` Methode aufgerufen, die dem InputManager sagt, 
welche Taste gerade gedrückt wurde. Dies muss getan werden, da Tasten auf einer Tastatur das Pressed,
Event beim Gedrückthalten mehrmals an den InputManager senden.

## Data Structures
### `Class Graph<T>`
Ein Graph besteht aus Knoten, die eine referenz auf
ihre Nachbarn haben. Die Nachbarn werden in einer Liste
gespeichert. Die Klasse Graph stellt Methoden zur Verfügung,
um Knoten hinzuzufügen, zu entfernen und zu verbinden.

### `Class Node<T>`
Die Node Klasse speichert ein Objekt vom Typ T, eine `Node<T>` Parent,
die sich im Graphen vor dem aktuellen Knoten befindet und eine Liste
von `Node<T>` Nachbarn. Die Klasse stellt Methoden zur Verfügung,
um die Nachbarn zu verändern und zu erhalten.

## Extensions
### `Class Pair<T,U>`
Bei der Klasse Pair handelt es sich um eine generische Klasse, die zwei Objekte vom Typ T und U speichert.

### `Class Physics`
Die Klasse Physics stellt Methoden zur Verfügung, um die Kollisionen zwischen Objekten zu berechnen.
Die Methoden sind statisch und können daher direkt aufgerufen werden.

## Dungeon
### `Class Dungeon`
Ein Dungeon erweitert die `class Graph<Room>`. Die Klasse implementiert
die Methode `generateDungeon()` um einen Dungeon zu generieren. Die
Methode `generateDungeon()` erzeugt einen Graphen mit zufällig generierten
Räumen. Der grundlegende Algorithmus für die Generation des Dungeons kommt von 
diesem Youtube Video: 

https://www.youtube.com/watch?v=qAf9axsyijY

Die Generation wurde jedoch um einige Features erweitert, so sind die Räume 
dynamisch, d.h. sie sind nicht vorher gespeichert, sondern werden bei der Runtime,
sobald sie benötigt werden generiert.
### Algorithmus
Der Algorithmus ist wie folgt aufgebaut:
1. Erstelle einen leeren Graphen
2. Erstelle einen Startknoten
3. Nehme eine Beliebige richtung und erstelle einen neuen Knoten, 
sollte dort nicht schon ein Knoten sein
4. Verbinde den neuen Knoten mit dem vorherigen
5. Wiederhole Schritt 3 und 4 bis die maximale Anzahl an Knoten in einem "Pfad" erreicht ist.
6. Nehme einen beliebigen Knoten und wiederhole Schritt 3 und 4 bis die maximale Anzahl an Knoten erreicht ist.
    Sollte der Knoten kein freies Feld mehr haben, gehe einen Schritt zurück und wähle einen anderen Knoten aus.
7. Wiederhole Schritt 6 bis die maximale Anzahl an Pfaden erreicht ist.

### `Class Room`
Raum-Typen:
- Start: Der Startpunkt des Dungeons
- Merchant: Ein Händler, der Items verkauft 
- Normal: Ein normaler Raum
- End: der Endpunkt eines Pfads, um letzten Raum zukommen, der noch nicht geöffnete Türen enthält.
- Boss: Der Boss-Raum

## Entities

### Enemies

### `Boss`
Der Boss ist ein Statisches Entity in Form eines Kristalls, vom Kristall aus werden in einem bestimmten
Abstand 5 Gegner gespawnt, die Bekämpft werden müssen, während der Kristall immer wider eine Schock-welle
aussendet, die dem Spieler Schaden zufügt, wenn er zu nah am Kristall ist.