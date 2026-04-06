package appDomain;

import implementations.MyStack;
import implementations.MyQueue;
import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLParser {
    public static void main(String[] args) {
    	String fileName = args[0];
        File file = new File("./res/"+fileName);
        // Group 1: tag name. Group 2: attributes.
        Pattern tagPattern = Pattern.compile("<(/?[a-zA-Z0-9_:-]+)(.*?)>");

        MyStack<String> tagS = new MyStack<>(); 
        MyQueue<String> errorQ = new MyQueue<>(); 
        MyQueue<String> extrasQ = new MyQueue<>();
        
        boolean hasError = false;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                Matcher matcher = tagPattern.matcher(line);

                while (matcher.find()) {
                    String fullTag = matcher.group(0);
                    String tagName = matcher.group(1);

                    if (fullTag.startsWith("<?")) continue;

                    // Improved self-closing check
                    if (fullTag.endsWith("/>") || fullTag.endsWith("/ >")) continue;

                    if (tagName.startsWith("/")) {
                        String actualName = tagName.substring(1); 

                        if (!tagS.isEmpty() && actualName.equals(tagS.peek())) {
                            tagS.pop();
                        } 
                        else if (!errorQ.isEmpty() && actualName.equals(errorQ.peek())) {
                            errorQ.dequeue();
                        } 
                        else if (tagS.isEmpty()) {
                            errorQ.enqueue(actualName);
                            hasError = true;
                        } 
                        else {
                            if (tagS.contains(actualName)) {
                                while (!tagS.isEmpty() && !tagS.peek().equals(actualName)) {
                                    String E = tagS.pop();
                                    errorQ.enqueue(E);
                                    System.out.println("Error: Tag <" + E + "> never closed.");
                                    hasError = true;
                                }
                                tagS.pop(); 
                            } else {
                                extrasQ.enqueue(actualName);
                                hasError = true;
                            }
                        }
                    } 
                    else {
                        tagS.push(tagName);
                    }
                }
            }

            // EOF Processing
            while (!tagS.isEmpty()) {
                errorQ.enqueue(tagS.pop());
                hasError = true;
            }

            while (!errorQ.isEmpty() || !extrasQ.isEmpty()) {
                hasError = true; // Any leftovers mean an error occurred
                if (errorQ.isEmpty() || extrasQ.isEmpty()) {
                    if (!errorQ.isEmpty()) System.out.println("Error: Missing closing tag for: " + errorQ.dequeue());
                    if (!extrasQ.isEmpty()) System.out.println("Error: Extra closing tag found: " + extrasQ.dequeue());
                } else {
                    String eErr = errorQ.peek();
                    String eExt = extrasQ.peek();

                    if (!eErr.equals(eExt)) {
                        System.out.println("Error: Mismatch! Expected closer for " + errorQ.dequeue());
                    } else {
                        // Matching names in different queues
                        System.out.println("Error: Intercrossed tags detected for: " + errorQ.peek());
                        errorQ.dequeue();
                        extrasQ.dequeue();
                    }
                }
            }
            
            if (!hasError) {
                System.out.println("The XML document is constructed correctly");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}