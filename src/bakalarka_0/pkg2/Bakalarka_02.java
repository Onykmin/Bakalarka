
package bakalarka_0.pkg2;

public class Bakalarka_02 {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();//pocitani za jak dlouho se vygeneruje uzel
        new Generator();
        long stopTime = System.currentTimeMillis();//pocitani za jak dlouho se vygeneruje uzel
        long elapsedTime = stopTime - startTime; //pocitani za jak dlouho se vygeneruje uzel
        System.out.println("Cas vypoctu :"+elapsedTime);
        System.out.println("PIDAROS");
    }
    
}
