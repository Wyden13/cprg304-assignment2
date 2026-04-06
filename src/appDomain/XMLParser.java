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
        
        // group 1: tag name 
        // group 2: attributes.
        Pattern tagPattern = Pattern.compile("<(/?[a-zA-Z0-9_:-]+)(.*?)>");

        MyStack<String> tagS = new MyStack<>(); 
        MyQueue<String> errorQ = new MyQueue<>(); 
        MyQueue<String> extrasQ = new MyQueue<>();
        
        boolean hasError = false;
        int lineCounter = 0;
        boolean rootClosed = false;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                lineCounter++;
                String line = scanner.nextLine().trim();
                Matcher matcher = tagPattern.matcher(line);

                while (matcher.find()) {
                    String fullTag = matcher.group(0);
                    String tagName = matcher.group(1);

                    // Skip processing instructions 
                    if (fullTag.startsWith("<?")) continue;

                    // ignore Self_Closing_Tag
                    if (fullTag.endsWith("/>") || fullTag.replace(" ", "").endsWith("/>")) continue;

                    // end_tag processing
                    if (tagName.startsWith("/")) {
                        String actualName = tagName.substring(1); 

                        // pack tag with its line number
                        String[] topParts = tagS.isEmpty() ? null : tagS.peek().split("\\|");

                        // if matches top of stack, pop stack and all is well
                        if (topParts != null && actualName.equals(topParts[2])) {
                            tagS.pop();
                            if (tagS.isEmpty()) rootClosed = true; // Track for root rule 
                        } 
                        // if matches head of errorQ, dequeue and ignore
                        else if (!errorQ.isEmpty() && actualName.equals(errorQ.peek())) {
                            errorQ.dequeue();
                        } 
                        // if stack is empty, add to errorQ
                        else if (tagS.isEmpty()) {
                            errorQ.enqueue(actualName);
                            System.out.println("Error at line: " + lineCounter + " </" + actualName + "> is not constructed correctly.");
                            hasError = true;
                        } 
                        else {
                            // search stack for matching Start_Tag
                            if (stackContains(tagS, actualName)) {
                                // pop each E from stack into errorQ until match, report as error
                                while (!tagS.isEmpty() && !tagS.peek().split("\\|")[2].equals(actualName)) {
                                    String[] errorParts = tagS.pop().split("\\|");
                                    errorQ.enqueue(errorParts[2]);
                                    System.out.println("Error at line: " + errorParts[0] + " " + errorParts[1] + " is not constructed correctly.");
                                    hasError = true;
                                }
                                tagS.pop(); // pop the matching tag itself
                            } else {
                                // else add E to extrasQ
                                extrasQ.enqueue(actualName);
                                System.out.println("Error at line: " + lineCounter + " </" + actualName + "> is not constructed correctly.");
                                hasError = true;
                            }
                        }
                    } 
                    else {
                        // If Start_Tag: Check root rule then Push on stack
                        if (tagS.isEmpty() && rootClosed) {
                            System.out.println("Error at line: " + lineCounter + " Multiple root tags detected.");
                            hasError = true;
                        }
                        // add the packed data
                        tagS.push(lineCounter + "|" + fullTag + "|" + tagName);
                    }
                }
            }

            // pop remaining stack into errorQ
            while (!tagS.isEmpty()) {
                String[] errorParts = tagS.pop().split("\\|");
                errorQ.enqueue(errorParts[2]);
                System.out.println("Error at line: " + errorParts[0] + " " + errorParts[1] + " is not constructed correctly.");
                hasError = true;
            }

            // Queue Comparison for intercrossed tags
            while (!errorQ.isEmpty() || !extrasQ.isEmpty()) {
                hasError = true;
                if (errorQ.isEmpty() || extrasQ.isEmpty()) {
                    if (!errorQ.isEmpty()) errorQ.dequeue();
                    if (!extrasQ.isEmpty()) extrasQ.dequeue();
                } else if (errorQ.peek().equals(extrasQ.peek())) {
                    // Match found in both queues suggests intercrossed structure
                    errorQ.dequeue();
                    extrasQ.dequeue();
                } else {
                    errorQ.dequeue();
                }
            }
            
            if (!hasError) {
                System.out.println("XML document is constructed correctly.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // helper to search stack for a tag name without a custom class
    private static boolean stackContains(MyStack<String> stack, String name) {
        utilities.Iterator<String> it = stack.iterator();
        while (it.hasNext()) {
            if (it.next().split("\\|")[2].equals(name)) return true;
        }
        return false;
    }
}