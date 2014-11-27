/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package myhashtable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author Hui
 */
public class SpellCheck {
    
    /* read the buffer
    *@return lst string list contains all the words
    */
    public static List<String> readWords( BufferedReader in ) throws IOException
    {
            String oneLine;
            List<String> lst = new ArrayList<>( );

            while( ( oneLine = in.readLine( ) ) != null )
                    lst.add( oneLine );

            return lst;
    }
    
    private static <KeyType> void update( Map<KeyType,List<String>> m, KeyType key, String value )
    {
            List<String> lst = m.get( key );
            if( lst == null )
            {
                lst = new ArrayList<>( );
                m.put( key, lst );
            }

            lst.add( value );
    }
    
    public static void computeAdjacentWords( String word, List<String> words )
    {
        List<String> adjWords = new ArrayList<>( );
        Map<Integer,List<String>> wordsByLength = new TreeMap<>( );

          // Group the words by their length
        for( String w : words )
            update( wordsByLength, w.length( ), w );

        List<String> entry = wordsByLength.get(word.length());                        
        for(String s : entry) 
            if(oneCharOff(s,word))
                System.out.print(s+" ");        
        
    }
    
    private static boolean oneCharOff( String word1, String word2 ){
            if( word1.length( ) != word2.length( ) )
                return false;

            int diffs = 0;

            for( int i = 0; i < word1.length( ); i++ )
                if( word1.charAt( i ) != word2.charAt( i ) )
                    if( ++diffs > 1 )
                        return false;
            return diffs == 1;
    }    
    
    public static void main( String [ ] args ) throws IOException
    {           
        FileReader fin = new FileReader( "C:/dictionary.txt" );
        BufferedReader bin = new BufferedReader( fin );
        List<String> words = readWords( bin );
        MyHashTable<String> dic = new MyHashTable<>();
        for (String word : words) 
            dic.insert(word);

        Scanner in = new Scanner(new FileReader("C:/nosilverbullet.txt"));
        while(in.hasNext()) {
            String word = in.next();
            word = word.toLowerCase();
            for(int i = 0; i<word.length(); i++) {
                String temp = "";
                while(i<word.length()
                        &&" -,.;?".indexOf(word.charAt(i))==-1) {
                    temp = temp + word.charAt(i);
                    i++;
                }
                if(!temp.equals("")&&!dic.contains(temp)) {
                    System.out.println(temp + " is misspelled");
                    System.out.print("Alternatives: ");
                    computeAdjacentWords( temp, words );
                    System.out.println();
                }   
            }                
        } 
    }
}
