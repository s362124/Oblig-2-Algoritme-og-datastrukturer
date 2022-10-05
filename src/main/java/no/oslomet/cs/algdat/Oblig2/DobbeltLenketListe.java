package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        hode = null;
        hale = null;
        antall = 0;
        endringer = 0;
    }

    public DobbeltLenketListe(T[] a) {
        
        if (a == null){
        throw new NullPointerException("Tabellen a er null!!");
    }
        
    Node<T> forrige = null;
    Node<T> newNode = null;
    
    for (T verdi : a) {
        if(verdi ==  null) {
            continue;
        }
        antall++;
        newNode = new Node<>(verdi, forrige, null);
        
        if(forrige != null) {
            forrige.neste = newNode;
        }
        else {
            hode = newNode;
        }
        
        forrige = newNode;
        //endringer 
    }
    hale = newNode;
    
    }

    // Oppgave 3 a)
    private Node<T> finnNode(int indeks){
        // Må endre variabelnavnet!
        Node<T> current = null;
        // Hvis indeks er mindre enn antall/2
        if (indeks == 0 && antall == 1){
            current = hode;
            return current;
        }

        if (indeks == antall - 1){
            current = hale;
            return current;
        }

        if (indeks < (antall / 2)){
            // Letingen etter noden skal starte fra hode og gå mot høyre ved hjelp av neste-pekere.
            for (int i = 0; i < indeks; ++i){
                current = current.neste;
            }
        } else {
            // Letingen skal starte fra halen og gå mot venstre ved hjelp av forrige-pekere.
            current = hale;
            for (int j = 0; j < antall - 1; j--){
                current = current.forrige;
            }
        }

        return current;
    }


    // Oppgave 3 b)
    public Liste<T> subliste(int fra, int til) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int antall() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tom() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean leggInn(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new UnsupportedOperationException();
    }

    // Oppgave 3 a)
    @Override
    public T hent(int indeks) {
        // Bruker false som parameter i indekskontroll()
        indeksKontroll(indeks, false);

        return finnNode(indeks).verdi;
    }

    // Oppgave 4
    @Override
    public int indeksTil(T verdi) {
        int firstIndeks = -1;
        for(int i = 0; i < antall; i++){
            if(hent(i).equals(verdi)){
                firstIndeks = i;
                break;
            }
        }
        return firstIndeks;
    }

    // Oppgave 3 a)
    @Override
    public T oppdater(int indeks, T nyverdi) {
        // Jeg vet ikke om dette er riktig, så må få dette sjekket.
        if (indeks == 0){
            Objects.requireNonNull(nyverdi, "Null-verdier kan ikke legges inn!");
            // Variabelen øker for hver endring, dvs. for alle mutatorer.
            for (int i = 0; i < endringer; ++i){
                // return nyverdi;
            }
        }

        return nyverdi;
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException();
    }

    public String omvendtString() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe


