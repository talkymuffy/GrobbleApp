package com.smcc;

import com.smcc.app.App;
import com.smcc.backend_process.AudioSystem;



/**
 *  Devs- Saptansu Ghosh and Kajol Dey
 * Languages Involved-Java,Gradle & Kotlin
 * This App Is a Simple Abstraction Of How AI is helpful in education.
 * We Did not go into any complicated logic like importing any basic AI models and stuff in a Python or C++ system(although Python is Preferable)
 * instead we made some self-made systems for the project (basics) as a foundation to demonstrate how useful it can be.
 */

 /**
 * Our Goal With This Project Was To Show How AI Is Useful In Education. Not Only By The Means Of Interacting With It, While It Is Still In Development
 * With A Few Here and There Stuff, Such As It Can Solve Some Specific Math Problems And Physics Problems, Open Apps Like Facebook, Twitter,
 * Instagram ,YouTube Etc. On Our Default Browser. Not Only We Get To Learn And Experience More Of Programming Via This Gradle-Kotlin-Java Integrated
 * System While Making This Educational AI, But Also The Ones Interacting Will Gain More Knowledge When They Try To
  * Gather Knowledge From AI, Which We Tried To Portray
 * In A Very Simple Way.
 */

 /**
  *For Any Physics Query In Extreme Detail use-'physics help'
  * For Any Specific Physics Query Within Data Quota use-'faq about <topic>' or 'what is <topic>'
  * For Sample Input Details Of Both Maths And Physics- 'physics help','math examples','physics examples','all physics formulas'
  *
  * For Maths Input-use 'Solve:<Qun>' [Only For Numericals And Sums]
  * For Physics Input use Phyiscs: <Qn>'[Only For Numericals And Sums]
  *
  * [STILL IN DEV------WILL TAKE TIME]
  *
  * For Registration Use username-yy password-xx as Default, or Go To Registration Page To Register your Custom Details.
  * AI Used-Copilot (For AI Images And The Definitions Of Items Used Here[With Moderator Permit](Like Physics Topic Items Definition, Maths Topic Items Definition) Only
  * -No Use Of AI In The Code Was Involved.)
  *
  * Date Of Submission-29 th June 2025
  *
  * Added-Sound Effects From Minecraft As Easter Eggs.
  *
  * */
 public class Main {
    public static void main(String[] args) {

        //Initialize The App(Starting)
           AudioSystem.playSoundStart();
           App.init();

        }
    }
