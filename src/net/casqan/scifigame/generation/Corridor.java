package net.casqan.scifigame.generation;

import net.casqan.scifigame.extensions.VertexInt;

record Corridor(VertexInt direction, boolean isExit, Node<Room> parent, Node<Room> child){}
