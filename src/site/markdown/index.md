 An extension, intended for use with Maven > 3.3.9, that set a property for use in the version.
 
 To use this, put a file named `version-policy.txt` in the project root, containing a line of the form:
 
 
     PROPERTY=VALUE
 
 
 VALUE can contain `${timestamp}`. For example:

 
     revision=${timestamp}
  
  Then declare this extension in `.mvn/extensions/xml` as follows:
 
 
     <?xml version="1.0" encoding="UTF-8"?>
     <extensions>
         <extension>
             <groupId>com.basistech</groupId>
             <artifactId>auto-version-maven-extension</artifactId>
             <version>0.0.1</version>
         </extension>
     </extensions>
  
 
 In the POM, use the property in the `<version/>` element:
 

     <version>0.0.${revision}</version>

   
