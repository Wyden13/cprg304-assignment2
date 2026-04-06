# Project Overview

The primary goal of this project is to create a utility that determines if an XML document is constructed correctly. It checks for properly matched opening and closing tags, enforces a single root tag, and identifies structural errors like intercrossed or unclosed tags.

A critical constraint of this assignment is the prohibition of java.util collection classes (like ArrayList or Stack) for core data management. Instead, the project implements these structures from scratch to demonstrate fundamental understanding of data structure mechanics.

# System Architecture & Data Structures

The project is built upon a hierarchy of custom-built linear data structures:

- MyArrayList<E>: A dynamic array implementation that manages its own capacity and provides indexed access to elements.

- MyDLL<E>: A doubly-linked list that uses MyDLLNode<E> to link elements in both directions, allowing efficient insertions and removals from both ends.

- MyStack<E>: A Last-In-First-Out (LIFO) structure used to track opening tags. It is internally backed by MyArrayList<E>.

- MyQueue<E>: A First-In-First-Out (FIFO) structure used to manage error reporting and processing queues. It is internally backed by MyDLL<E>

# Core Functionality: XMLParser.java

The XMLParser class contains the application logic for validating XML files.

## Parsing Algorithm

The parser follows a specific logic (often referred to as Kitty's XML Parser Algorithm):

- Tag Identification: Uses regular expressions to find tags while ignoring attributes and processing instructions (e.g., <?xml ... ?>).

- Tag Classification:

    - Self-Closing Tags: Ignore.

    -  Opening Tags: Pushed onto the MyStack.

    - Closing Tags: Matched against the top of the stack.

## Error Handling:

If a closing tag does not match the stack's top, the parser "searches" the stack, popping unmatched tags into an error queue until a match is found or the stack is empty.

It enforces that the document has one and only one root tag.

Final Comparison: At the end of the file (EOF), any remaining tags in the stack are moved to error queues to identify unclosed or intercrossed tags.

# Usage Instructions

## Compilation and Packaging

The project is designed to be exported as a Runnable JAR named Parser.jar.

## Running the Application

Execute the validator from the command line by providing the XML filename as an argument:

# Key Features

- Custom ADTs: Implements ListADT, StackADT, and QueueADT interfaces without using standard Java collections.

- Detailed Error Reporting: Identifies the exact line and type of structural failure (mismatch, missing closer, extra closer, or multiple roots).

- Memory Efficiency: Uses snapshot-based iterators to allow safe traversal of data structures.
