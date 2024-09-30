# Serialization Failed Questions:

1. What will the following code print when compiled and run?

````java
import java.io.Serializable;
class Booby{
    int i; public Booby(){ i = 10; System.out.print("Booby"); }
}
class Dooby extends Booby implements Serializable {
    int j; public Dooby(){ j = 20; System.out.print("Dooby"); }
}
class Tooby extends Dooby{
    int k; public Tooby(){ k = 30; System.out.print("Tooby"); }
}
public class TestClass {
  public static void main(String[] args) throws Exception{
    
    var t = new Tooby();
    t.i = 100;
    var oos  = new ObjectOutputStream(new FileOutputStream("c:\\temp\\test.ser"));
    oos.writeObject(t); oos.close();
    
    var ois = new ObjectInputStream(new FileInputStream("c:\\temp\\test.ser"));
    t = (Tooby) ois.readObject();ois.close();
    
    System.out.println(t.i+" "+t.j+" "+t.k);
  }
}
//Ans: Booby Dooby Tooby Booby 10 20 30

````

````text
Objects of a class that is not marked Serializable cannot be serialized. In this question, class Booby does not implement Serializable and so, its objects cannot be serialized. Class Dooby implements Serializable and since Tooby extends Dooby, it is Serializable as well.

Now, when you serialize an object of class Tooby, only the data members of Dooby and Tooby will be serialized. Data members of Booby will not be serialized. Thus, the value of i (which is 100) at the time of serialization will not be saved in the file.

When reading the object back (i.e. deserializing), the constructors of serializable classes are not called. Their data members are set directly from the values present in serialized data. Constructor for unserializable classes is called. Thus, in this case, constructors of Tooby and Dooby are not called but the constructor of Booby is called. Therefore, i is set in the constructor to 10 and j and k are set using the data from the file to 20 and 30 respectively.
````