import java.util.Random;

/**
 * Taller 6 - Herencia y Polimorfismo
 * Sistema de combate de videojuegos (Programación 1)
 *
 * Todas las clases están en este único archivo para facilitar su descarga.
 * Solo la clase Main es pública (regla de Java: un archivo .java solo puede
 * tener una clase pública, y debe llamarse igual que el archivo).
 *
 * Para compilar y ejecutar:
 *   javac Main.java
 *   java Main
 */
public class Main {

    public static void main(String[] args) {

        // ----- Creación de personajes -----
        Guerrero guerrero1 = new Guerrero("G1", "Thrall", 1, 100, 15);
        Guerrero guerrero2 = new Guerrero("G2", "Garrosh", 1, 120, 20);

        Mago mago1 = new Mago("M1", "Jaina", 1, 80, 12, 60);
        Mago mago2 = new Mago("M2", "Medivh", 1, 90, 16, 40);

        Arquero arquero1 = new Arquero("A1", "Legolas", 1, 85, 10, 8);
        Arquero arquero2 = new Arquero("A2", "Sylvanas", 1, 95, 13, 12);

        System.out.println("Personajes creados:");
        System.out.println("- " + guerrero1.getNombre() + " (Guerrero) fuerza=" + guerrero1.getFuerza());
        System.out.println("- " + guerrero2.getNombre() + " (Guerrero) fuerza=" + guerrero2.getFuerza());
        System.out.println("- " + mago1.getNombre() + " (Mago) poderMagico=" + mago1.getPoderMagico()
                + " mana=" + mago1.getMana());
        System.out.println("- " + mago2.getNombre() + " (Mago) poderMagico=" + mago2.getPoderMagico()
                + " mana=" + mago2.getMana());
        System.out.println("- " + arquero1.getNombre() + " (Arquero) fuerza=" + arquero1.getFuerza()
                + " precision=" + arquero1.getPrecision());
        System.out.println("- " + arquero2.getNombre() + " (Arquero) fuerza=" + arquero2.getFuerza()
                + " precision=" + arquero2.getPrecision());
        System.out.println();

        // ----- Combates entre personajes de diferentes tipos -----
        Combate combate1 = new Combate(guerrero1, mago1);
        combate1.iniciar();

        Combate combate2 = new Combate(mago2, arquero1);
        combate2.iniciar();

        Combate combate3 = new Combate(arquero2, guerrero2);
        combate3.iniciar();

        Combate combate4 = new Combate(guerrero1, arquero2);
        combate4.iniciar();

        Combate combate5 = new Combate(mago1, guerrero2);
        combate5.iniciar();
    }
}


/**
 * Clase abstracta que representa un personaje del sistema de combate.
 * Contiene el estado y comportamiento común a todo tipo de personaje.
 * Las subclases (Guerrero, Mago, Arquero) definen su propia forma de atacar
 * mediante sobrescritura del método abstracto atacar() (polimorfismo).
 */
abstract class Personaje {

    private String id;
    private String nombre;
    private int nivel;
    private int vida;
    private int vidaMaxima;
    private int experiencia;
    private int experienciaParaSubir;

    public Personaje(String id, String nombre, int nivel, int vidaMaxima) {
        this.id = id;
        this.nombre = nombre;
        this.nivel = nivel;
        this.vidaMaxima = vidaMaxima;
        this.vida = vidaMaxima;
        this.experiencia = 0;
        this.experienciaParaSubir = 200; // XP requerida para pasar de nivel 1 a 2
    }

    /**
     * Cada subclase implementa su propia lógica de ataque.
     * Este método es la base del polimorfismo del sistema: el combate
     * llama siempre a atacar() sin saber de qué tipo concreto es el personaje.
     */
    public abstract int atacar();

    public void recibirDano(int dano) {
        this.vida -= dano;
        if (this.vida < 0) {
            this.vida = 0;
        }
    }

    public boolean estaVivo() {
        return this.vida > 0;
    }

    /**
     * Suma experiencia y sube de nivel las veces que corresponda.
     * La XP necesaria para el siguiente nivel aumenta 100 puntos cada vez.
     */
    public void ganarExperiencia(int xp) {
        this.experiencia += xp;
        while (this.experiencia >= this.experienciaParaSubir) {
            this.experiencia -= this.experienciaParaSubir;
            subirNivel();
            this.experienciaParaSubir += 100;
        }
    }

    private void subirNivel() {
        this.nivel++;
        this.vidaMaxima += 20;
        this.vida = this.vidaMaxima; // recupera vida completa al subir de nivel
        System.out.println(">> " + this.nombre + " sube a nivel " + this.nivel
                + ". Vida máxima ahora: " + this.vidaMaxima);
    }

    // ----- Getters (encapsulamiento) -----
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public int getVida() {
        return vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public int getExperienciaParaSubir() {
        return experienciaParaSubir;
    }
}


/**
 * Guerrero: personaje especializado en ataques físicos.
 * Daño = fuerza x nivel.
 * Habilidad especial: si su vida es <= 30% de su vida máxima,
 * obtiene 20% de bonificación en el daño causado (furia de batalla).
 */
class Guerrero extends Personaje {

    private int fuerza;

    public Guerrero(String id, String nombre, int nivel, int vidaMaxima, int fuerza) {
        super(id, nombre, nivel, vidaMaxima);
        this.fuerza = fuerza;
    }

    @Override
    public int atacar() {
        int danoBase = this.fuerza * getNivel();
        double porcentajeVida = (double) getVida() / getVidaMaxima();

        if (porcentajeVida <= 0.30) {
            int danoBonificado = (int) Math.round(danoBase * 1.20);
            System.out.println("   [Habilidad] " + getNombre()
                    + " activa su Furia de Batalla (vida baja): +20% de daño.");
            return danoBonificado;
        }
        return danoBase;
    }

    public int getFuerza() {
        return fuerza;
    }
}


/**
 * Mago: personaje especializado en ataques mágicos.
 * Ataque normal = poderMagico x nivel.
 * Ataque especial (consume 20 de maná) = poderMagico x nivel x 2.
 * Si no tiene maná suficiente, realiza un ataque normal.
 */
class Mago extends Personaje {

    private static final int COSTO_ATAQUE_ESPECIAL = 20;

    private int poderMagico;
    private int mana;
    private int manaMaximo;

    public Mago(String id, String nombre, int nivel, int vidaMaxima, int poderMagico, int manaMaximo) {
        super(id, nombre, nivel, vidaMaxima);
        this.poderMagico = poderMagico;
        this.manaMaximo = manaMaximo;
        this.mana = manaMaximo;
    }

    @Override
    public int atacar() {
        if (this.mana >= COSTO_ATAQUE_ESPECIAL) {
            this.mana -= COSTO_ATAQUE_ESPECIAL;
            int danoEspecial = this.poderMagico * getNivel() * 2;
            System.out.println("   [Habilidad] " + getNombre()
                    + " lanza un ataque especial (maná restante: " + this.mana + ").");
            return danoEspecial;
        }

        System.out.println("   " + getNombre() + " no tiene maná suficiente, realiza un ataque normal.");
        return this.poderMagico * getNivel();
    }

    public int getMana() {
        return mana;
    }

    public int getManaMaximo() {
        return manaMaximo;
    }

    public int getPoderMagico() {
        return poderMagico;
    }
}


/**
 * Arquero: personaje especializado en ataques a distancia.
 * Daño = (fuerza x nivel) + precision.
 * Tiene probabilidad de golpe crítico, que duplica el daño calculado.
 */
class Arquero extends Personaje {

    private static final double PROBABILIDAD_CRITICO = 0.25; // 25% de probabilidad
    private static final Random RANDOM = new Random();

    private int fuerza;
    private int precision;

    public Arquero(String id, String nombre, int nivel, int vidaMaxima, int fuerza, int precision) {
        super(id, nombre, nivel, vidaMaxima);
        this.fuerza = fuerza;
        this.precision = precision;
    }

    @Override
    public int atacar() {
        int danoBase = (this.fuerza * getNivel()) + this.precision;
        boolean esCritico = RANDOM.nextDouble() < PROBABILIDAD_CRITICO;

        if (esCritico) {
            System.out.println("   [Habilidad] " + getNombre() + " conecta un golpe crítico! Daño duplicado.");
            return danoBase * 2;
        }
        return danoBase;
    }

    public int getFuerza() {
        return fuerza;
    }

    public int getPrecision() {
        return precision;
    }
}


/**
 * Gestiona un combate por turnos entre dos personajes.
 * No necesita saber el tipo concreto de cada personaje: solo invoca
 * el método polimórfico atacar() heredado de Personaje.
 */
class Combate {

    private static final int XP_VICTORIA = 100;

    private Personaje personaje1;
    private Personaje personaje2;

    public Combate(Personaje personaje1, Personaje personaje2) {
        this.personaje1 = personaje1;
        this.personaje2 = personaje2;
    }

    public void iniciar() {
        System.out.println("=========================================================");
        System.out.println("COMBATE: " + descripcion(personaje1) + "  VS  " + descripcion(personaje2));
        System.out.println("=========================================================");

        Personaje atacante = personaje1;
        Personaje defensor = personaje2;
        int turno = 1;

        while (personaje1.estaVivo() && personaje2.estaVivo()) {
            System.out.println("\n-- Turno " + turno + ": ataca " + atacante.getNombre() + " --");

            int dano = atacante.atacar();
            defensor.recibirDano(dano);

            System.out.println("   " + atacante.getNombre() + " causa " + dano + " de daño a " + defensor.getNombre() + ".");
            System.out.println("   " + defensor.getNombre() + " -> vida: "
                    + defensor.getVida() + "/" + defensor.getVidaMaxima());

            if (!defensor.estaVivo()) {
                break;
            }

            // se intercambian los roles para el siguiente turno
            Personaje temp = atacante;
            atacante = defensor;
            defensor = temp;
            turno++;
        }

        Personaje ganador = personaje1.estaVivo() ? personaje1 : personaje2;
        Personaje perdedor = personaje1.estaVivo() ? personaje2 : personaje1;

        System.out.println("\n" + perdedor.getNombre() + " ha sido derrotado.");
        System.out.println("Ganador: " + ganador.getNombre() + " (+"
                + XP_VICTORIA + " puntos de experiencia)");

        int nivelAntes = ganador.getNivel();
        ganador.ganarExperiencia(XP_VICTORIA);
        if (ganador.getNivel() == nivelAntes) {
            System.out.println(ganador.getNombre() + " ahora tiene " + ganador.getExperiencia()
                    + "/" + ganador.getExperienciaParaSubir() + " XP para el siguiente nivel.");
        }
        System.out.println("=========================================================\n");
    }

    private String descripcion(Personaje p) {
        return p.getNombre() + " (" + p.getClass().getSimpleName() + ", nivel " + p.getNivel() + ")";
    }
}
