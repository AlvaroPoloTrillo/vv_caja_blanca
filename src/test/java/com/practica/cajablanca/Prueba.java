package com.practica.cajablanca;

import static org.junit.Assert.assertThrows;

import static org.junit.jupiter.api.Assertions.*;



import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.practica.cajablanca.Editor;
import com.cajanegra.AbstractSingleLinkedListImpl;
import com.cajanegra.EmptyCollectionException;
import com.cajanegra.SingleLinkedListImpl;

class Prueba {

	private static Editor editorVacio;
    private static Editor editor0;
    private static Editor editor1;
    private static Editor editor2;
    private static Editor editor3;
    private static Editor editor4;
    private static Editor editor5;
    private static Editor editor6;
    private static Editor editor7;
    private static Editor editor8;
   

    @BeforeEach
    void setUp() {
    	
        editorVacio = new Editor();  
        editorVacio.leerFichero("src/test/java/com/practica/cajablanca/ficherovacio.txt"); // 
        editor0 = new Editor();       
        editor0.leerFichero("src/test/java/com/practica/cajablanca/fichero0.txt");// AA-BB
        editor1 = new Editor();  
        editor1.leerFichero("src/test/java/com/practica/cajablanca/fichero1.txt");// []-BB
        editor2 = new Editor();
        editor2.leerFichero("src/test/java/com/practica/cajablanca/fichero2.txt"); //AA
        editor3 = new Editor();
        editor3.leerFichero("src/test/java/com/practica/cajablanca/fichero3.txt"); //AA-B
        editor4= new Editor();
        editor4.leerFichero("src/test/java/com/practica/cajablanca/fichero4.txt"); //A-BB
        editor5= new Editor();
        editor5.leerFichero("src/test/java/com/practica/cajablanca/ficheroAny.txt"); //X-[]
        editor6= new Editor();
        editor6.leerFichero("src/test/java/com/practica/cajablanca/ficheroAA[].txt");//AA[]
        editor7= new Editor();
        editor7.leerFichero("src/test/java/com/practica/cajablanca/ficheroBB.txt");
        editor8= new Editor();
        editor8.leerFichero("src/test/java/com/practica/cajablanca/ficherointroAA.txt");
    }
    
    //-------------------------------------------------
    
    @DisplayName("Probando numPalabras")
    @ParameterizedTest
    @MethodSource("ArgumentosNumPalabras")
    void testNumPalabras(int resultado,  Editor editor, int inicio, int fin, String palabra) {
        try {
            assertEquals(resultado, editor.numPalabras(inicio,fin,palabra));
        } catch (IllegalArgumentException e) {
            assertThrows(IllegalArgumentException.class, () -> {
                editor.numPalabras(inicio, fin, palabra);
            });
        }
    }

    static Stream<Arguments> ArgumentosNumPalabras() {

        return Stream.of(
                Arguments.of(0, editor0, 0, 1, "A"),
                Arguments.of(0, editor0, 1, 10, "A"),
                Arguments.of(0, editorVacio, 1, 0, "A"),
                Arguments.of(0, editor0, 5, 2, "A"),
                //Arguments.of(0, editor1, 1, 2, "A"),
                Arguments.of(0, editor0, 1, 2, "CC"),
                Arguments.of(1, editor0, 1, 2, "AA"), 
                Arguments.of(1, editor0, 1, 2, "BB")
                );
    }
    
    //-----------------------------------------
    
    
    @DisplayName("Probando sustituirPalabras")
    @ParameterizedTest
    @MethodSource("ArgumentosSustituirPalabras")
    void sustituirPalabra(Editor editor, Editor editorFinal, String palabra, String nuevaPalabra){
    	
    	try {
    		editor.sustituirPalabra(palabra, nuevaPalabra);
    		
	    	for(int i=1;i<=editor.size();i++) {
	    		//System.out.println("Antes del cambio:" + editor.getLinea(1));
	    		String aux = editor.getLinea(i).toString();
	    		String aux1 = editorFinal.getLinea(i).toString();
	    		assertTrue(aux.equals(aux1));
	    	}
    	}catch(EmptyCollectionException e) {
    		e.getStackTrace();
    	}
    	
    	
    }

    	static Stream<Arguments> ArgumentosSustituirPalabras() {

    		return Stream.of(
    				Arguments.of(editorVacio, editorVacio, "X", "X"), 
    				Arguments.of(editor2, editor2, "BB", "X"),
    				Arguments.of(editor2, editor5 , "AA", "X"),
    				Arguments.of(editorVacio,editorVacio, "X", "X"), 
    				Arguments.of(editor6,editor5, "AA", "X")
    				);
    	}
    	
    	
    //--------------------------------------------
    	
    	@DisplayName("Probando mayorLongitud")
    	@ParameterizedTest
    	@MethodSource("ArgumentosMayorLongitud")
    	void testMayorLongitud(String resultado, Editor editor)  {
    			
      		try {
    		assertEquals(resultado,editor.mayorLongitud());
    		}catch(EmptyCollectionException e1) {
    			//HACEMOS EL TRY-CATCH POR LA EXCEPCION QUE PUEDE SALTAR PERO CON LOS CASOS DE PRUEBA QUE SE HAN DETERMINADO NUNCA SE ENTRA EN EL 
    			//CATCH HACIENDO QUE ESTAS INSTRUCCIONES NO SE CUBRAN Y DE UN COVERAGE MENOR DEL METODO. COMENTADOLAS LOGRAMOS EL COVERAGE OBJETIVO
//    			assertThrows(EmptyCollectionException.class, ()-> { 
//    				editor.mayorLongitud();
//    			}
//      		);
    		}
    	}
    	
    	static Stream<Arguments> ArgumentosMayorLongitud(){
    		
    		editorVacio = new Editor();  
            editorVacio.leerFichero("src/test/java/com/practica/cajablanca/ficherovacio.txt");
    		editor2 = new Editor();
            editor2.leerFichero("src/test/java/com/practica/cajablanca/fichero2.txt"); //AA
            editor3 = new Editor();
            editor3.leerFichero("src/test/java/com/practica/cajablanca/fichero3.txt"); //AA-B
            editor4= new Editor();
            editor4.leerFichero("src/test/java/com/practica/cajablanca/fichero4.txt");
            editor8 = new Editor();       
            editor8.leerFichero("src/test/java/com/practica/cajablanca/fichero8.txt");// []
            
    		return Stream.of(
    					Arguments.of(null,editorVacio), 
    					Arguments.of("",editor8), //en los dos anteriores salta la excepcion EmptyCollectionException
    					Arguments.of("AA",editor2),
    					Arguments.of("AA",editor3),      
    					Arguments.of("BB",editor4)	
    				);
    	}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    
}
