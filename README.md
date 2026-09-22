# taller6
taller #6 programacion1 Santiago Rodriguez Julian Felipe salazar
Taller 6 - Herencia y Polimorfismo
Descripción

Este proyecto es un sistema de combates de videojuegos hecho en Java.

Se trabajan los conceptos de:

Herencia

Polimorfismo

Encapsulamiento

Clases abstractas

El programa tiene tres tipos de personajes:

Guerrero

Mago

Arquero

Cada uno tiene una forma diferente de atacar.

Clases
Personaje

Es la clase principal y contiene los datos básicos de los personajes, como:

Nombre

Nivel

Vida

Experiencia

Guerrero

Tiene fuerza y hace daño dependiendo de su fuerza y nivel.

También tiene una habilidad que aumenta el daño cuando tiene poca vida.

Mago

Tiene poder mágico y maná.

Puede hacer un ataque especial que consume 20 de maná y hace el doble de daño.

Arquero

Tiene fuerza y precisión.

Tiene un 25% de probabilidad de hacer un golpe crítico que duplica el daño.

Combate

Se encarga de realizar los combates entre dos personajes.

Los personajes atacan por turnos hasta que uno pierde toda su vida.

El ganador recibe 100 puntos de experiencia.

Cómo ejecutar

Primero se debe compilar el programa:

javac Main.java


Después se ejecuta con:

java Main

Personajes

El programa crea estos personajes:

Thrall - Guerrero

Garrosh - Guerrero

Jaina - Mago

Medivh - Mago

Legolas - Arquero

Sylvanas - Arquero

También se realizan varios combates entre ellos.

Nota

Los resultados pueden cambiar en cada ejecución porque el Arquero tiene una probabilidad de realizar golpes críticos.
