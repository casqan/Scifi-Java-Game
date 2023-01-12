package net.casqan.scifigame.dungeon;

import net.casqan.scifigame.extensions.VertexInt;

record Corridor(VertexInt direction, boolean isExit, Node<Room> parent, Node<Room> child){}
