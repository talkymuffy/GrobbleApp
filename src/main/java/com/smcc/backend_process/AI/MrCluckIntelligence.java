package com.smcc.backend_process.AI;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class MrCluckIntelligence {

    public static String processUserMessage(String input) {
        String text = input.trim().toLowerCase();

        // Greetings
        String[] greetings = {"hi", "hello", "hey", "greetings", "namaste", "hola"};
        for (String greet : greetings) {
            if (text.contains(greet)) {
                return "Hello there! How can I help you today?";
            }
        }


        // Farewells
        String[] farewells = {"bye", "goodbye", "see you", "later", "take care"};
        for (String bye : farewells) {
            if (text.contains(bye)) {
                return "Goodbye! Talk to you soon.";
            }
        }

        //Commands, Open And Close
        String[] commands={"close","open"};
        String[] keywords={"youtube", "google","facebook","instagram","twitter","x","spotify","link","desktop","pc","computer","app"};

        if(text.contains(commands[1])){
            if(text.contains(keywords[0])){
                try {
                    Desktop.getDesktop().browse(URI.create("https://www.youtube.com/"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return "Opening..";
            }
            else if(text.contains(keywords[1])){
                try {
                    Desktop.getDesktop().browse(URI.create("https://www.google.com/"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return "Opening..";
            }
            else if(text.contains(keywords[2])){
                try {
                    Desktop.getDesktop().browse(URI.create("https://www.facebook.com/"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return "Opening..";
            }
            else if(text.contains(keywords[3])){
                try {
                    Desktop.getDesktop().browse(URI.create("https://www.instagram.com/"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return "Opening..";
            }
            else if(text.contains(keywords[4])){
                try {
                    Desktop.getDesktop().browse(URI.create("https://www.x.com/"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return "Opening..";
            }
            else if(text.contains(keywords[5])){
                try {
                    Desktop.getDesktop().browse(URI.create("https://www.x.com/"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return "Opening..";
            }
            else if(text.contains(keywords[6])){
                try {
                    Desktop.getDesktop().browse(URI.create("https://www.spotify.com/"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return "Opening..";
            }
            else if(text.contains(keywords[7])){

            }
        }
        else if(text.contains(commands[0])){
           if(text.contains(keywords[8])||text.contains(keywords[9])||text.contains(keywords[10])){//Close Dekstop(Shut Down)

           }
           else if(text.contains(keywords[11])){
               System.exit(0);
               return "" ;
           }
        }

        // Basic questions
        if (text.contains("how are you")) {
            return "I'm doing great! Thanks for asking!";
        }
        if (text.contains("what is your name")) {
            return "You can call me Mr. Cluck—your friendly AI companion.";
        }
        if (text.contains("who made you")) {
            return "I was made by two brilliant humans named Saptansu and Kajol, of course!";
        }

        // Fallback
        return "I'm not sure how to respond to that yet, but I'm learning!";
    }

}
