package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.*;


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
        // En instans av klassen DobbeltLenketListe()
        DobbeltLenketListe<T> liste = new DobbeltLenketListe<>();

        // Skjekker om indeksene fra og til er lovlige
        for (int i = fra; i < til; ++i){
            liste.leggInn(finnNode(i).verdi);
        }

        fratilKontroll(antall, fra, til);

        // Returnerer listen
        return liste;
    }

    // Oppgave 3 b)
    // Legger inn metoden fratilKontroll som en privat metode.
    private void fratilKontroll(int antall, int fra, int til) {
        /*
        Se for-løkke:
        Indeksene er lovlige hvis i starter fra fra-verdien og
        slutter til når i er mindre enn til-verdien.
         */
        // Hvis fra-verdien er mindre enn null, så er verdien negativ
        if (fra < 0){
            throw new IndexOutOfBoundsException("fra (" + fra + ") - Intervallet er negativt!");
        }

        // Hvis fra-verdien er større enn til-verdien
        if (fra > til){
            throw new IndexOutOfBoundsException("fra(" + fra + ") > til(" + til + ") - Intervallet er ikke lovlig!");
        }

        // Hvis til-verdien er større enn antall verdier, så skriv ut melding
        if (til > antall){
            throw new IndexOutOfBoundsException("til(" + til + ") > antall(" + antall + ")");
        }
    }
    // oppgave 2-1b

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        if (antall == 0){
            return true;
        }
        else{
            return false; }

    }


    @Override
    // Oppgave 2-b
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi," Det er ikke tillatt med null verdi");
        if(antall == 0){
            hode = new Node<>(verdi, null, null);
            hale = hode;

        }
        else {
            hale.neste = new Node <> (verdi, hale, null);
            hale = hale.neste;
        }
        antall++;
        return true;
    }



    @Override
    // oppgave 5
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi, " Det er ikke tillatt med null verdier ");

        indeksKontroll(indeks, true);

        if (indeks == 0 && antall == 0) {
            hode = new Node<> (verdi, null, null);
            hale = hode;
            hode.neste = null;
            hode.forrige = null;

        } else if (indeks == 0 && antall > 0) {

            hode = finnNode(0);

            Node<T> current = hode;
            Node<T> xNode = new Node<> (verdi, null, current);

            xNode.neste = current;
            current.forrige = xNode;
            hode = xNode;
        }
        else if (indeks == antall) {
            Node<T> xNode = new Node<>(verdi, hale, null);

            if (hale != null) {
                xNode.forrige = hale;
                hale.neste = xNode;
                hale = xNode;
            }
        }
        else {
            Node<T> prev = null;
            Node<T> current = hode;

            for (int i = 1; i < indeks; i++)
                current = current.neste;
            prev = current;
            current = current.neste;

            Node<T> xNode = new Node<>(verdi, prev, current);
            prev.neste = xNode;
            xNode.neste = current;
            current.forrige = xNode;
            xNode.forrige = prev;
        }
        antall++;
        endringer++;

        //throw new UnsupportedOperationException();
    }

    // Oppgave 4
    @Override
    public boolean inneholder(T verdi) {
        if(indeksTil(verdi) != -1){

            return true;
        }
        else return false;
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
        Objects.requireNonNull(nyverdi, "Null-verdier kan ikke legges inn!");
        indeksKontroll(indeks, false);
        Node<T> prev = finnNode(indeks);
        T prevVerdi = prev.verdi;
        prev.verdi = nyverdi;

        endringer++;

        return prevVerdi;
    }

    // Oppgave 6
    @Override
    public boolean fjern(T verdi) {
        // Skal fjerne verdi fra listen og så returnere true.
        if (verdi == null){
            return false;
        }
        Node<T> fjernNode = null;
        Node<T> node = hode;

        while(node != null){
            if (node.verdi.equals(verdi)){
                fjernNode = node;
                break;
            } else {
                node = node.neste;
            }
        }

        if (fjernNode == null){
            return false;
        }

        fjernNode.verdi = null;

        if (fjernNode == hode && fjernNode.neste != null){
            hode = fjernNode.neste;
        }

        if (fjernNode == hale && fjernNode.forrige != null){
            hale = fjernNode.forrige;
        }

        if (fjernNode.forrige != null){
            fjernNode.forrige.neste = fjernNode.neste;
        }

        if (fjernNode.neste != null){
            fjernNode.neste.forrige = fjernNode.forrige;
        }

        antall--;
        endringer++;
        return true;
    }

    // Oppgave 6
    @Override
    public T fjern(int indeks) {
        // Skal fjerne (og retunere) verdien på posisjon indeks (som først må sjekkes)

        // Sjekker indeksen ved å bruke metoden indekskontroll()
        indeksKontroll(indeks, false);

        // Hvis listen er tom
        if (antall == 0){
            throw new NoSuchElementException("Listen er tom!");
        }

        Node<T> fjernNode = finnNode(indeks);

        if (fjernNode == null){
            return null;
        }

        if (fjernNode == hode && fjernNode.neste != null){
            hode = fjernNode.neste;
        }

        if (fjernNode == hale && fjernNode.forrige != null){
            hale = fjernNode.forrige;
        }

        if (fjernNode.forrige != null){
            fjernNode.forrige.neste = fjernNode.neste;
        }

        if (fjernNode.neste != null){
            fjernNode.neste.forrige = fjernNode.forrige;
        }

        antall--;
        endringer++;

        // Returnerer verdien på posisjon indeks
        return fjernNode.verdi;
    }

    // Oppgave 7
    @Override
    public void nullstill() {
        Node<T> r = hode, s = hale;
        while (r != null){
            r = s.neste;
            s.neste = null;
            s.verdi = null;
            s = r;
        }
        hode = hale = null;
        antall = 0;
        endringer++;
    }

    // Oppgave 2
    @Override
    public String toString() {
        //Bygge en String foran
        StringBuilder sFor = new StringBuilder();
        if(tom())
            return " [] ";
        sFor .append('[');
        Node <T> p = hode;
        sFor .append(p.verdi);
        p = p.neste;
        while(p != null ) {
            sFor .append(',').append(' ').append(p.verdi);
            p = p. neste;
        }
        sFor.append(']');


        throw new UnsupportedOperationException();
    }

    public String omvendtString() {
        StringBuilder sBack = new StringBuilder();
        if (tom()){
            return"[]"; }
            sBack.append('[');
            Node<T> p = hale;
            sBack.append(p.verdi);
            p = p. forrige;
            while( p != null) {
                sBack.append(',').append(' ').append(p.verdi);
                p = p.forrige;
            }
            sBack.append(']');
            return sBack.toString();


       // throw new UnsupportedOperationException();
    }

    @Override
    // oppgave 8
    public Iterator<T> iterator() {
        return new DobbeltLenketListeIterator();

        //throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks, false);

        return
                new DobbeltLenketListeIterator(indeks);
        //throw new UnsupportedOperationException();
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
            denne = finnNode(indeks);
            fjernOK = false;
            iteratorendringer = endringer;
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            if(iteratorendringer != endringer) {
                throw new ConcurrentModificationException("  ");
                
            } else if (! hasNext()) {
                throw new NoSuchElementException(" Det finnes ingen verdier!");}
                fjernOK = true;

                T denneVerdi = denne.verdi ;

                denne = denne.neste ;

                return denneVerdi ;

        }

        // Oppgave 9
        @Override
        public void remove() {
            Node<T> p, q, r;
            if (!fjernOK){
                throw new IllegalStateException("Ikke lov å tilkalle denne metoden!");
            }

            if (endringer != iteratorendringer){
                throw new ConcurrentModificationException("De er like!");
            }
            fjernOK = false;

            if (antall == 1){   // 1.
                hode = hale = null;
            } else if (denne == null){     // 2.
                hale = hale.forrige;
                hale.neste = null;
            } else if (denne.forrige == hode){    // 3.
                hode = hode.forrige;
                hode.neste = null;
            } else {        // 4.
                p = denne.forrige;
                q = p.neste;
                r = p.forrige;
                q.forrige = r;
                r.neste = q;
            }

            antall--;
            endringer++;
            iteratorendringer++;
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        //throw new UnsupportedOperationException();
        for(int i= liste.antall(); i>0; i--){
            Iterator<T> itera = liste.iterator();
            T maxVardi = itera.next();
            int maxIndex = 0;

            for(int k =1; k<i; k++){
                T temp = itera.next();
                if(c.compare(temp,maxVardi) < 0){
                    maxVardi = temp;
                    maxIndex = k ;
                }
            }
          liste.leggInn(liste.fjern(maxIndex));
        }
    }

} // class DobbeltLenketListe


