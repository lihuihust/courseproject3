/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myhashtable;

/**
 *
 * @author Hui
 */
public class MyHashTable<AnyType>{
    
    private static final int DEFAULT_TABLE_SIZE = 101;

    private AnyType[ ] array; // The array of elements
    private int occupied;                 // The number of occupied cells
    
    public MyHashTable() {
        this( DEFAULT_TABLE_SIZE );
    }     
    
    public MyHashTable(int size)
    {
        allocateArray(size);
        occupied = 0;
    }
    
    private void allocateArray( int arraySize ) {
        array = (AnyType[]) new Object[nextPrime(arraySize) ];
    }
    
        
    private int myhash( AnyType x ){
        
        int hashVal = x.hashCode( );

        hashVal %= array.length;
        if( hashVal < 0 )
            hashVal += array.length;

        return hashVal;
    }
    
    private int findpos(AnyType x) {
        int currentpos = myhash(x); 
        while(array[currentpos]!=null&&!array[currentpos].equals(x)) {
            currentpos += 1;
            if(currentpos >= array.length-1)
                currentpos -= array.length-1;
        }
        return currentpos;
    }
    
    public boolean contains (AnyType x) {
        int currentpos = findpos(x);
        return array[currentpos]!=null&&array[currentpos].equals(x);    
    }
    
    public boolean insert (AnyType x) {
        int currentpos = findpos(x);
        if (array[currentpos]!=null&&array[currentpos].equals(x))
            return false;
        array[currentpos] = x;
        occupied++;
        if (occupied>array.length/2)
            rehash();
        return true;
    }
    
    private void rehash() {
        AnyType[] oldArray = array;
        allocateArray(2*oldArray.length);
        occupied = 0;
        
        for( AnyType entry : oldArray )
            if(entry != null)
                insert(entry);
    }
    
    private static int nextPrime( int n ){
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }

    private static boolean isPrime( int n ){
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MyHashTable<String> H = new MyHashTable<>( );       
        
        final int NUMS = 200000;
        final int GAP  =   37;

        System.out.println( "Checking... (no more output means success)" );


        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS ) 
            H.insert( ""+i );
            
        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
            if(H.insert(""+i))
                System.out.println( "OOPS!!! " + i );
        
        for(int i = 2; i < NUMS; i+=2)
            if(!H.contains(""+i))
                System.out.println("Find fails " + i);

        System.out.println("the size of the table is: " + H.occupied);// TODO code application logic here
    }
    
}
